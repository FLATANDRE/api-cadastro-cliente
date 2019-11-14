/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.contrato;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.insumo.TipoInsumo;
import br.com.aicare.di.api_rest.dto.contrato.TipoInsumoDTO;
import br.com.aicare.di.api_rest.dto.contrato.TipoInsumoSimpleResponseDTO;
import br.com.aicare.di.api_rest.repository.contrato.TipoInsumoRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Collection;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
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

/**
 *
 * @author Paulo Collares
 */
@RestController
@Secured({"ROLE_ADA"})
@CrossOrigin
@RequestMapping("/insumos/tipos")
@Api(tags = "Tipo de insumos", description = "Tipo de insumos")
public class TipoInsumoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TipoInsumoRepository tipoInsumoRepository;

    @ApiOperation(value = "Lista os tipos de bomba de insumos")
    @GetMapping()
    public Page<TipoInsumo> listar(
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
        return tipoInsumoRepository.findAll(pageable);
    }

    @ApiOperation(value = "Lista os tipos de bomba de insumos informações reduzidas")
    @GetMapping("/all")
    public Collection<TipoInsumoSimpleResponseDTO> listar() {
        Collection<TipoInsumoSimpleResponseDTO> all = tipoInsumoRepository.all();
        return all;
    }

    @ApiOperation(value = "Busca um tipo de bomba pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoInsumo> listar(@PathVariable Integer id) {
        Optional<TipoInsumo> tipoInsumo = tipoInsumoRepository.findById(id);

        if (!tipoInsumo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(tipoInsumo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo tipo insumos")
    @PostMapping()
    public ResponseEntity<TipoInsumo> criar(@RequestBody TipoInsumoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            TipoInsumo tipoInsumo = new TipoInsumo();

            if (StringUtils.isBlank(dto.getNome())) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            tipoInsumo.setNome(dto.getNome());

            if (StringUtils.isBlank(dto.getCodigo())) {
                return ApiError.badRequest(Translator.toLocale("validacao_codigo"));
            }
            tipoInsumo.setCodigo(dto.getCodigo());

            if (dto.getPrecoPadrao() == null || dto.getPrecoPadrao().isNaN()) {
                return ApiError.badRequest(Translator.toLocale("validacao_preco_padrao"));
            }
            tipoInsumo.setPrecoPadrao(dto.getPrecoPadrao());

            tipoInsumo.setDescricao(dto.getDescricao());

            TipoInsumo novo = tipoInsumoRepository.save(tipoInsumo);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um tipo de insumos", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um tipo insumos")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TipoInsumo> atualizar(@PathVariable("id") int id, @RequestBody TipoInsumoDTO dto) {
        try {
            Optional<TipoInsumo> atual = tipoInsumoRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getNome() != null) {
                if (StringUtils.isBlank(dto.getNome())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_nome"));
                }
                atual.get().setNome(dto.getNome());
            }

            if (dto.getCodigo() != null) {
                if (StringUtils.isBlank(dto.getCodigo())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_codigo"));
                }
                atual.get().setCodigo(dto.getCodigo());
            }

            if (dto.getPrecoPadrao() != null) {
                if (dto.getPrecoPadrao().isNaN()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_preco_padrao"));
                }
                atual.get().setPrecoPadrao(dto.getPrecoPadrao());
            }

            if (dto.getDescricao() != null) {
                atual.get().setDescricao(dto.getDescricao());
            }

            //Atualizo o objeto utilizando o repositório
            TipoInsumo atualizado = tipoInsumoRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um tipo de insumos", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
