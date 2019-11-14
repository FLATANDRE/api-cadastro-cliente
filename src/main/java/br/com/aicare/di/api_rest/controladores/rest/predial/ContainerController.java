/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.predial;

import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.predial.Compartimento;
import br.com.aicare.di.api_rest.dominio.predial.Container;
import br.com.aicare.di.api_rest.dominio.predial.TipoContainer;
import br.com.aicare.di.api_rest.dto.predial.ContainerDTO;
import br.com.aicare.di.api_rest.dto.predial.ContainerSimpleResponseDTO;
import br.com.aicare.di.api_rest.repository.predial.CompartimentoRepository;
import br.com.aicare.di.api_rest.repository.predial.ContainerRepository;
import br.com.aicare.di.api_rest.repository.predial.TipoContainerRepository;
import br.com.aicare.di.api_rest.specifications.predial.ContainerSpecificationFactory;
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
@RequestMapping("/predial/container")
@Api(tags = "Container", description = "Container")
public class ContainerController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ContainerRepository containerRepository;

    @Autowired
    CompartimentoRepository compartimentoRepository;

    @Autowired
    TipoContainerRepository tipoContainerRepository;

    @ApiOperation(value = "Lista os container")
    @GetMapping()
    public Page<Container> listar(
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
            @ApiParam(required = true, name = "c", value = "Id do compartimento")
            @RequestParam(
                    value = "c",
                    required = true) int c
    ) {
        Specification<Container> specificationQ = Specification.where(null);

        if (q != null) {
            specificationQ = specificationQ.and(ContainerSpecificationFactory.busca(q));
        }
        specificationQ = specificationQ.and(ContainerSpecificationFactory.compartimento(c));

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return containerRepository.findAll(specificationQ, pageable);
    }

    @ApiOperation(value = "Lista os containers com informações reduzidas")
    @GetMapping("/all")
    public Collection<ContainerSimpleResponseDTO> listar(
            @ApiParam(required = true, name = "c", value = "Id do container")
            @RequestParam(value = "c",
                    required = true) int c
    ) {
        Collection<ContainerSimpleResponseDTO> all = containerRepository.all(c);
        return all;
    }

    @ApiOperation(value = "Busca um container pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity buscar(@PathVariable Integer id) {
        Optional<Container> container = containerRepository.findById(id);

        if (!container.isPresent()) {
            return ApiError.badRequest(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(container.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo container")
    @PostMapping()
    public ResponseEntity<Container> criar(@RequestBody ContainerDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            Container container = new Container();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            container.setNome(dto.getNome());

            container.setDescricao(dto.getDescricao());

            if (dto.getTipo() == null) {
                return ApiError.badRequest(Translator.toLocale("container_validacao_tipo"));
            }
            Optional<TipoContainer> o = tipoContainerRepository.findById(dto.getTipo());
            if (o.isPresent()) {
                container.setTipoContainer(o.get());
            } else {
                return ApiError.badRequest(Translator.toLocale("container_validacao_tipo_inesistente") + ": " + dto.getTipo());
            }

            if (dto.getCompartimento() == null) {
                return ApiError.badRequest(Translator.toLocale("container_validacao_compartimento"));
            }
            Optional<Compartimento> o2 = compartimentoRepository.findById(dto.getCompartimento());
            if (o2.isPresent()) {
                container.setCompartimento(o2.get());
            } else {
                return ApiError.badRequest(Translator.toLocale("container_validacao_compartimento_inesistente") + ": " + dto.getCompartimento());
            }

            Container novo = containerRepository.save(container);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um container", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um Container")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Container> atualizar(@PathVariable("id") int id, @RequestBody ContainerDTO dto) {
        try {
            Optional<Container> atual = containerRepository.findById(id);

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
                Optional<TipoContainer> o = tipoContainerRepository.findById(dto.getTipo());
                if (o.isPresent()) {
                    atual.get().setTipoContainer(o.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("container_validacao_tipo_inesistente") + ": " + dto.getTipo());
                }
            }

            if (dto.getCompartimento() != null) {
                Optional<Compartimento> o2 = compartimentoRepository.findById(dto.getCompartimento());
                if (o2.isPresent()) {
                    atual.get().setCompartimento(o2.get());
                } else {
                    return ApiError.badRequest(Translator.toLocale("container_validacao_compartimento_inesistente") + ": " + dto.getCompartimento());
                }
            }

            //Atualizo o objeto utilizando o repositório
            Container atualizado = containerRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um container", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
