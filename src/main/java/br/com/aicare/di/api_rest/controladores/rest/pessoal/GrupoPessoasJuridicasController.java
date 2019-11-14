/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.pessoal;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.pessoal.GrupoPJ;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import br.com.aicare.di.api_rest.dto.pessoal.GrupoPJDTO;
import br.com.aicare.di.api_rest.repository.pessoal.GrupoPessoaJuridicaRepository;
import br.com.aicare.di.api_rest.repository.pessoal.PessoaJuridicaRepository;
import br.com.aicare.di.api_rest.specifications.pessoal.GrupoPJSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashSet;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_ADH"})
@RequestMapping("/pessoal/grupo-pessoas-juridicas")
@Api(tags = "Grupo de pessoas Jurídicas", description = "Grupo de pessoas Jurídicas")
public class GrupoPessoasJuridicasController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GrupoPessoaJuridicaRepository grupoPessoaJuridicaRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @ApiOperation(value = "Lista os Grupo de pessoas Jurídicas")
    @GetMapping()
    public Page<GrupoPJ> listar(
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

        Specification<GrupoPJ> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(GrupoPJSpecificationFactory.busca(q));
        }
        
        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return grupoPessoaJuridicaRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Busca um Grupo de pessoas Jurídicas pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<GrupoPJ> listar(@PathVariable Integer id) {
        Optional<GrupoPJ> grupoPJ = grupoPessoaJuridicaRepository.findById(id);

        if (!grupoPJ.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(grupoPJ.get(), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "Cria um novo Grupo de pessoas Jurídicas")
    @PostMapping()
    public ResponseEntity<GrupoPJ> criar(@RequestBody GrupoPJDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            GrupoPJ grupoPJ = new GrupoPJ();

            if (StringUtils.isBlank(dto.getNome())) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            grupoPJ.setNomeFantasia(dto.getNome());

            if (dto.getParticipantes() == null || dto.getParticipantes().size() < 2) {
                return ApiError.badRequest(Translator.toLocale("validacao_grupopj_quantidade_participantes"));
            }
            grupoPJ.setParticipantes(new HashSet<>());
            for (int i : dto.getParticipantes()) {
                Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(i);
                if (o.isPresent()) {
                    grupoPJ.getParticipantes().add(o.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("validacao_grupopj_participante_inesistente") + ": " + i);
                }
            }
            if (grupoPJ.getParticipantes().size() < 2) {
                return ApiError.badRequest(Translator.toLocale("validacao_grupopj_quantidade_participantes"));
            }

            if (dto.getLider() != null) {
                if (!dto.getParticipantes().contains(dto.getLider())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_grupopj_lider_nao_participantes"));
                }
                grupoPJ.setLider(new HashSet<>());
                Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(dto.getLider());
                if (o.isPresent()) {
                    grupoPJ.getLider().add(o.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("validacao_grupopj_lider_inesistente") + ": " + dto.getLider());
                }
            }

            GrupoPJ novo = grupoPessoaJuridicaRepository.save(grupoPJ);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um dispositivos", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @Transactional
    @ApiOperation(value = "Atualiza um Grupo de pessoas Jurídicas")
    @PutMapping(value = "/{id}")
    public ResponseEntity<GrupoPJ> atualizar(@PathVariable("id") int id, @RequestBody GrupoPJDTO dto) {
        try {
            Optional<GrupoPJ> atual = grupoPessoaJuridicaRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getNome() != null) {
                if (StringUtils.isBlank(dto.getNome())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_nome"));
                }
                atual.get().setNomeFantasia(dto.getNome());
            }

            if (dto.getParticipantes() != null) {
                atual.get().getParticipantes().clear();
                for (int i : dto.getParticipantes()) {
                    Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(i);
                    if (o.isPresent()) {
                        atual.get().getParticipantes().add(o.get());
                    } else {
                        return ApiError.badRequest(Translator.toLocale("validacao_grupopj_participante_inesistente") + ": " + i);
                    }
                }
                if (atual.get().getParticipantes().size() < 2) {
                    return ApiError.badRequest(Translator.toLocale("validacao_grupopj_quantidade_participantes"));
                }
            }

            if (dto.getLider() != null) {
                boolean encontrouLiderNaListaParticipantes = false;
                for (PessoaJuridica pj : atual.get().getParticipantes()) {
                    if (pj.getId() == dto.getLider()) {
                        encontrouLiderNaListaParticipantes = true;
                        break;
                    }
                }
                if (!encontrouLiderNaListaParticipantes) {
                    return ApiError.badRequest(Translator.toLocale("validacao_grupopj_lider_nao_participantes"));
                }
                Optional<PessoaJuridica> o = pessoaJuridicaRepository.findById(dto.getLider());
                if (o.isPresent()) {
                    if (atual.get().getLider() == null) {
                         atual.get().setLider(new HashSet<>());
                    } else {
                        atual.get().getLider().clear();
                    }
                    atual.get().getLider().add(o.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("validacao_grupopj_lider_inesistente") + ": " + dto.getLider());
                }
            }

            //Atualizo o objeto utilizando o repositório
            GrupoPJ atualizado = grupoPessoaJuridicaRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um grupo de pessoa juridica", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
