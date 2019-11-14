/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.dispositivo;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.TipoDispositivo;
import br.com.aicare.di.api_rest.dto.dispositivo.TipoDispositivoDTO;
import br.com.aicare.di.api_rest.repository.dispositivo.TipoDispositivoRepository;
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

/**
 *
 * @author Paulo Collares
 */
@RestController
@Secured({"ROLE_ADA"})
@CrossOrigin
@RequestMapping("/dispositivos/tipos")
@Api(tags = "Tipo Dispositivos", description = "Tipo de Dispositivos")
public class TipoDispositivoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TipoDispositivoRepository tipoDispositivoRepository;

    @ApiOperation(value = "Lista os tipos de Dispositivos")
    @GetMapping()
    public Page<TipoDispositivo> listar(
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
        return tipoDispositivoRepository.findAll(pageable);
    }

    @ApiOperation(value = "Busca um tipo de dispositivo pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TipoDispositivo> listar(@PathVariable Integer id) {
        Optional<TipoDispositivo> tipoDispositivo = tipoDispositivoRepository.findById(id);

        if (!tipoDispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(tipoDispositivo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo tipo dispositivo")
    @PostMapping()
    public ResponseEntity<TipoDispositivo> criar(@RequestBody TipoDispositivoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            TipoDispositivo tipoDispositivo = new TipoDispositivo();

            if (dto.getNome() == null || dto.getNome().isEmpty()) {
                return ApiError.badRequest(Translator.toLocale("validacao_nome"));
            }
            tipoDispositivo.setNome(dto.getNome());

            if (dto.getCodigo() == null || !dto.getCodigo().matches("[A-Za-z]{2}") && !dto.getCodigo().matches("[A-Za-z]{4}")) {
                return ApiError.badRequest(Translator.toLocale("validacao_codigo_invalido"));
            }
            if (tipoDispositivoRepository.codigoExistente(dto.getCodigo())) {
                return ApiError.badRequest(Translator.toLocale("validacao_codigo_existente"));
            }
            tipoDispositivo.setCodigo(dto.getCodigo().toLowerCase());

            if (dto.getAssociavelDispositivo() != null) {
                tipoDispositivo.setAssociavelEquipamento(dto.getAssociavelDispositivo());
            }

            if (dto.getAssociavelLocalizacao() != null) {
                tipoDispositivo.setAssociavelLocalizacao(dto.getAssociavelLocalizacao());
            }

            TipoDispositivo novo = tipoDispositivoRepository.save(tipoDispositivo);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um tipo de dispositivo", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um tipo dispositivo")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TipoDispositivo> atualizar(@PathVariable("id") int id, @RequestBody TipoDispositivoDTO dto) {
        try {
            Optional<TipoDispositivo> atual = tipoDispositivoRepository.findById(id);

            if (!atual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.getNome() != null) {
                if (dto.getNome().isEmpty()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_nome"));
                }
                atual.get().setNome(dto.getNome());
            }

            if (dto.getCodigo() != null) {
                if (!dto.getCodigo().matches("[A-Za-z]{2}") && !dto.getCodigo().matches("[A-Za-z]{4}")) {
                    return ApiError.badRequest(Translator.toLocale("validacao_codigo_invalido"));
                }
                if (!atual.get().getCodigo().equalsIgnoreCase(dto.getCodigo()) && tipoDispositivoRepository.codigoExistente(dto.getCodigo())) {
                    return ApiError.badRequest(Translator.toLocale("validacao_codigo_existente"));
                }
                atual.get().setCodigo(dto.getCodigo().toLowerCase());
            }

            if (dto.getAssociavelDispositivo() != null) {
                atual.get().setAssociavelEquipamento(dto.getAssociavelDispositivo());
            }

            if (dto.getAssociavelLocalizacao() != null) {
                atual.get().setAssociavelLocalizacao(dto.getAssociavelLocalizacao());
            }

            //Atualizo o objeto utilizando o repositório
            TipoDispositivo atualizado = tipoDispositivoRepository.save(atual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um tipo de dispositivo", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
