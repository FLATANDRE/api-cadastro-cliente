/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.manutencao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.configuracoes.seguranca.TokenProvider;
import br.com.aicare.di.api_rest.dominio.autenticacao.Usuario;
import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import br.com.aicare.di.api_rest.dominio.equipamento.Manutencao;
import br.com.aicare.di.api_rest.dominio.equipamento.TipoManutencao;
import br.com.aicare.di.api_rest.dto.manutencao.ManutencaoDTO;
import br.com.aicare.di.api_rest.repository.autenticacao.UsuarioRepository;
import br.com.aicare.di.api_rest.repository.equipamento.EquipamentoRepository;
import br.com.aicare.di.api_rest.repository.manutencao.ManutencaoRepository;
import br.com.aicare.di.api_rest.repository.manutencao.TipoManutencaoRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaFisicaRepository;
import br.com.aicare.di.api_rest.specifications.manutencao.ManutencaoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_GMN", "ROLE_GCM"})
@RequestMapping("/equipamentos/manutencoes")
@Api(tags = "Manutenções", description = "Manutenções")
public class ManutencaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    ManutencaoRepository manutencaoRepository;

    @Autowired
    TipoManutencaoRepository tipoManutencaoRepository;

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @ApiOperation(value = "Lista as Manutenções de um equipamento")
    @GetMapping()
    public Page<Manutencao> listar(
            @ApiParam(required = false, name = "page", value = "Número da página")
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @ApiParam(required = false, name = "size", value = "Tamanho da página")
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size,
            @ApiParam(required = false, name = "sort", value = "Ordenação [coluna],[asc|desc]")
            @RequestParam(
                    value = "sort",
                    required = false) String sort,
            @ApiParam(required = false, name = "q", value = "Busca")
            @RequestParam(
                    value = "q",
                    required = false) String q
    ) {

        Specification<Manutencao> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(ManutencaoSpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return manutencaoRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Busca uma Manutencao")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Manutencao> listar(@PathVariable int id) {
        Optional<Manutencao> manutencao = manutencaoRepository.findById(id);

        if (!manutencao.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(manutencao.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria uma nova mantenção")
    @PostMapping()
    public ResponseEntity<Manutencao> criar(@RequestBody ManutencaoDTO dto, UriComponentsBuilder ucBuilder, @RequestHeader(name = "Authorization") String token) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            Manutencao manutencao = new Manutencao();

            if (token == null) {
                return ApiError.badRequest(Translator.toLocale("validacao_manutencao_usuario_invalido"));
            }

            String username = tokenProvider.getUserFromToken(token);

            if (username == null) {
                return ApiError.badRequest(Translator.toLocale("validacao_manutencao_usuario_invalido"));
            }

            Optional<Usuario> usuario = usuarioRepository.findByLogin(username);

            if (!usuario.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_manutencao_usuario_invalido"));
            }

            manutencao.setResponsavel(usuario.get().getPessoa());

            if (dto.getEquipamento() == null) {
                return ApiError.badRequest(Translator.toLocale("validacao_manutencao_equipamento_invalido"));
            }

            Optional<Equipamento> equipamento = equipamentoRepository.findById(dto.getEquipamento());
            if (!equipamento.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_manutencao_equipamento_invalido"));
            }
            manutencao.setEquipamento(equipamento.get());

            if (dto.getTipo() == null) {
                return ApiError.badRequest(Translator.toLocale("validacao_manutencao_tipo_invalido"));
            }

            Optional<TipoManutencao> tipo = tipoManutencaoRepository.findById(dto.getTipo());
            if (!tipo.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_manutencao_tipo_invalido"));
            }
            manutencao.setTipoManutencao(tipo.get());

            manutencao.setDataManutencao(new Date());
            manutencao.setObservacao(dto.getObservacao());

            //Salvo o objeto utilizando o repositório
            Manutencao novo = manutencaoRepository.save(manutencao);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar uma manutenção", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza uma manutenção")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Manutencao> atualizar(@PathVariable("id") int id, @RequestBody ManutencaoDTO dto) {
        try {
            Optional<Manutencao> atual = manutencaoRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getEquipamento() != null) {
                Optional<Equipamento> equipamento = equipamentoRepository.findById(dto.getEquipamento());
                if (!equipamento.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_manutencao_equipamento_invalido"));
                }
                atual.get().setEquipamento(equipamento.get());
            }

            if (dto.getTipo() != null) {
                Optional<TipoManutencao> tipo = tipoManutencaoRepository.findById(dto.getTipo());
                if (!tipo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_manutencao_tipo_invalido"));
                }
                atual.get().setTipoManutencao(tipo.get());
            }

            if (dto.getObservacao() != null) {
                atual.get().setObservacao(dto.getObservacao());
            }

            //Atualizo o objeto utilizando o repositório
            Manutencao atualizado = manutencaoRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar uma manutenção", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
