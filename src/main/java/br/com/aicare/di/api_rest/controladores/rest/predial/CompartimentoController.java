/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.predial;

import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.predial.Compartimento;
import br.com.aicare.di.api_rest.dominio.predial.LocalizacaoFisica;
import br.com.aicare.di.api_rest.dominio.predial.TipoCompartimento;
import br.com.aicare.di.api_rest.dto.predial.CompartimentoDTO;
import br.com.aicare.di.api_rest.dto.predial.CompartimentoSimpleResponseDTO;
import br.com.aicare.di.api_rest.repository.predial.CompartimentoRepository;
import br.com.aicare.di.api_rest.repository.predial.LocalizacaoFisicaRepository;
import br.com.aicare.di.api_rest.repository.predial.TipoCompartimentoRepository;
import br.com.aicare.di.api_rest.specifications.predial.CompartimentoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Paulo
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM", "ROLE_ADH"})
@RequestMapping("/predial/compartimento")
@Api(tags = "Compartimentos", description = "Compartimentos")
public class CompartimentoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CompartimentoRepository compartimentoRepository;

    @Autowired
    LocalizacaoFisicaRepository localizacaoFisicaRepository;

    @Autowired
    TipoCompartimentoRepository tipoCompartimentoRepository;

    @ApiOperation(value = "Lista os compartimentos")
    @GetMapping()
    public Page<Compartimento> listar(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size,
            @RequestParam(
                    value = "sort",
                    required = false) String sort,
            @RequestParam(
                    value = "q",
                    required = false) String q,
            @ApiParam(required = true, name = "l", value = "Id da localização física")
            @RequestParam(
                    value = "l",
                    required = true) int l
    ) {
        Specification<Compartimento> specificationQ = Specification.where(null);

        if (q != null) {
            specificationQ = specificationQ.and(CompartimentoSpecificationFactory.busca(q));
        }
        specificationQ = specificationQ.and(CompartimentoSpecificationFactory.localizacaoFisica(l));

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return compartimentoRepository.findAll(specificationQ, pageable);
    }

    @ApiOperation(value = "Lista os compartimentos com informações reduzidas")
    @GetMapping("/all")
    public Collection<CompartimentoSimpleResponseDTO> listar(
            @ApiParam(required = true, name = "l", value = "Id da localização física")
            @RequestParam(value = "l",
                    required = true) int l
    ) {
        Collection<CompartimentoSimpleResponseDTO> all = compartimentoRepository.all(l);
        return all;
    }

    @ApiOperation(value = "Busca um compartimento pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity buscar(@PathVariable Integer id) {
        Optional<Compartimento> compartimento = compartimentoRepository.findById(id);

        if (!compartimento.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(compartimento.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo Compartimento")
    @PostMapping()
    public ResponseEntity<Compartimento> criar(@RequestBody CompartimentoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            Compartimento compartimento = new Compartimento();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            compartimento.setNome(dto.getNome());

            compartimento.setDescricao(dto.getDescricao());

            if (dto.getTipo() == null) {
                return ApiError.badRequest(Translator.toLocale("compartimento_validacao_tipo"));
            }
            Optional<TipoCompartimento> o = tipoCompartimentoRepository.findById(dto.getTipo());
            if (o.isPresent()) {
                compartimento.setTipoCompartimento(o.get());
            } else {
                return ApiError.badRequest(Translator.toLocale("compartimento_validacao_tipo_inesistente") + ": " + dto.getTipo());
            }

            if (dto.getLocalizacaoFisica() == null) {
                return ApiError.badRequest(Translator.toLocale("compartimento_validacao_localizacao_fisica"));
            }
            Optional<LocalizacaoFisica> o2 = localizacaoFisicaRepository.findById(dto.getLocalizacaoFisica());
            if (o2.isPresent()) {
                compartimento.setLocalizacaoFisica(o2.get());
            } else {
                return ApiError.badRequest(Translator.toLocale("compartimento_validacao_localizacao_fisica_inesistente") + ": " + dto.getLocalizacaoFisica());
            }

            Compartimento novo = compartimentoRepository.save(compartimento);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um Compartimento", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um Compartimento")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Compartimento> atualizar(@PathVariable("id") int id, @RequestBody CompartimentoDTO dto) {
        try {
            Optional<Compartimento> atual = compartimentoRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getNome() != null) {
                if (dto.getNome().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_nome"));
                }
                atual.get().setNome(dto.getNome());
            }

            if (dto.getDescricao() != null) {
                atual.get().setDescricao(dto.getDescricao());
            }

            if (dto.getTipo() != null) {
                Optional<TipoCompartimento> o = tipoCompartimentoRepository.findById(dto.getTipo());
                if (o.isPresent()) {
                    atual.get().setTipoCompartimento(o.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("compartimento_validacao_tipo_inesistente") + ": " + dto.getTipo());
                }
            }

            if (dto.getLocalizacaoFisica() != null) {
                Optional<LocalizacaoFisica> o2 = localizacaoFisicaRepository.findById(dto.getLocalizacaoFisica());
                if (o2.isPresent()) {
                    atual.get().setLocalizacaoFisica(o2.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("compartimento_validacao_localizacao_fisica_inesistente") + ": " + dto.getLocalizacaoFisica());
                }
            }

            //Atualizo o objeto utilizando o repositório
            Compartimento atualizado = compartimentoRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um Compartimento", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
