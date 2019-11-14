/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.predial;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.predial.TipoContainer;
import br.com.aicare.di.api_rest.dto.predial.TipoContainerDTO;
import br.com.aicare.di.api_rest.repository.predial.TipoContainerRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@Secured({"ROLE_ADA"})
@CrossOrigin
@RequestMapping("/predial/containers/tipos")
@Api(tags = "Tipos de container", description = "Tipos de container")
public class TipoContainerController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TipoContainerRepository tipoContainerRepository;

    @ApiOperation(value = "Lista os tipos de containers")
    @GetMapping()
    public Page<TipoContainer> listar(
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
                    required = false) String sort
    ) {

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return tipoContainerRepository.findAll(pageable);
    }

    @ApiOperation(value = "Busca um tipo container pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoContainer> listar(@PathVariable Integer id) {
        Optional<TipoContainer> tipoContainer = tipoContainerRepository.findById(id);

        if (!tipoContainer.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(tipoContainer.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo tipo container")
    @PostMapping()
    public ResponseEntity<TipoContainer> criar(@RequestBody TipoContainerDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            TipoContainer tipoContainer = new TipoContainer();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            tipoContainer.setNome(dto.getNome());

            TipoContainer novo = tipoContainerRepository.save(tipoContainer);

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

    @ApiOperation(value = "Atualiza um tipo de container")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TipoContainer> atualizar(@PathVariable("id") int id, @RequestBody TipoContainerDTO dto) {
        try {
            Optional<TipoContainer> atual = tipoContainerRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getNome() != null) {
                if (dto.getNome().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_nome"));
                }
                atual.get().setNome(dto.getNome());
            }

            //Atualizo o objeto utilizando o repositório
            TipoContainer atualizado = tipoContainerRepository.save(atual.get());

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
