/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.dispositivo;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.dominio.dispositivo.EstadoAtual;
import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.dispositivo.TipoDispositivo;
import br.com.aicare.di.api_rest.dto.contrato.ContratoSimpleResponseDTO;
import br.com.aicare.di.api_rest.dto.dispositivo.DispositivoDTO;
import br.com.aicare.di.api_rest.repository.dispositivo.DispositivoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.EstadoAtualRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.ModeloDispositivoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.TipoDispositivoRepository;
import br.com.aicare.di.api_rest.specifications.dispositivo.DispositivoSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.MAC;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_PEV"})
@RequestMapping("/dispositivos")
@Api(tags = "Dispositivos", description = "Dispositivo")
public class DispositivoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    EstadoAtualRepository estadoAtualRepository;

    @Autowired
    TipoDispositivoRepository tipoDispositivoRepository;

    @Autowired
    ModeloDispositivoRepository modeloRepository;

    @ApiOperation(value = "Lista os Dispositivos")
    @GetMapping()
    public Page<Dispositivo> listar(
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
                    required = false) String q,
            @ApiParam(required = false, name = "o", value = "Operativos ou não [1|0]")
            @RequestParam(
                    value = "o",
                    required = false) String o,
            @ApiParam(required = false, name = "t", value = "Código do tipo de dispositivo")
            @RequestParam(
                    value = "t",
                    required = false) String t
    ) {

        Specification<Dispositivo> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(DispositivoSpecificationFactory.busca(q));
        }

        if (o != null) {
            specification = specification.and(DispositivoSpecificationFactory.operativo(o.equals("1")));
        }

        if (t != null) {
            specification = specification.and(DispositivoSpecificationFactory.tipo(t));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return dispositivoRepository.findAll(specification, pageable);
    }
    
    @ApiOperation(value = "Busca um dispositivo pelo mac")
    @GetMapping(value = "/mac/{mac}")
    public ResponseEntity<Dispositivo> listar(@PathVariable String mac) {
        Optional<Dispositivo> dispositivo = dispositivoRepository.findByMac(mac);

        if (!dispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(dispositivo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca um Dispositivo pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Dispositivo> listar(@PathVariable Integer id) {
        Optional<Dispositivo> dispositivo = dispositivoRepository.findById(id);

        if (!dispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(dispositivo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo Dispositivo")
    @PostMapping()
    public ResponseEntity<Dispositivo> criar(@RequestBody DispositivoDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            Dispositivo dispositivo = new Dispositivo();

            if (dto.isOperativo() == null) {
                dispositivo.setOperativo(true);
            } else {
                dispositivo.setOperativo(dto.isOperativo());
            }

            MAC mac = new MAC(dto.getMac());
            if (!mac.isValido()) {
                return ApiError.badRequest(Translator.toLocale("mac_invalido"));
            }
            if (dispositivoRepository.macExistente(mac.getMac().toLowerCase())) {
                return ApiError.badRequest(Translator.toLocale("mac_ja_cadastrado") + ": " + dto.getMac());
            }
            dispositivo.setMac(dto.getMac().toUpperCase());

            dispositivo.setNameSpace(dto.getNameSpace());
            dispositivo.setVersaoFirmware(dto.getVersaoFirmware());
            dispositivo.setVersaoHardware(dto.getVersaoHardware());

            if (dto.getIdTipo() != null) {
                Optional<TipoDispositivo> tipoDispositivo = tipoDispositivoRepository.findById(dto.getIdTipo());
                if (!tipoDispositivo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_tipo_dispositivo"));
                }
                dispositivo.setTipoDispositivo(tipoDispositivo.get());
            } else {
                String codigo = "uk";
                if (dto.getTipo() != null) {
                    codigo = dto.getTipo();
                }
                Optional<TipoDispositivo> tipoDispositivo = tipoDispositivoRepository.findByCodigo(codigo);
                if (!tipoDispositivo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_tipo_dispositivo"));
                }
                dispositivo.setTipoDispositivo(tipoDispositivo.get());
            }

            if (dto.getModelo() == null) {
                dto.setModelo(1);
            }
            Optional<ModeloDispositivo> modelo = modeloRepository.findById(dto.getModelo());
            if (!modelo.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_modelo"));
            }
            dispositivo.setModelo(modelo.get());

            //Salvo o objeto utilizando o repositório
            EstadoAtual novoEstadoAtual = estadoAtualRepository.save(new EstadoAtual());
            dispositivo.setEstadoAtual(novoEstadoAtual);
            Dispositivo novo = dispositivoRepository.save(dispositivo);

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

    @ApiOperation(value = "Atualiza um Dispositivo")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Dispositivo> atualizar(@PathVariable("id") int id, @RequestBody DispositivoDTO dto) {
        try {
            Optional<Dispositivo> dispositivoAtual = dispositivoRepository.findById(id);

            if (!dispositivoAtual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.isOperativo() != null) {
                dispositivoAtual.get().setOperativo(dto.isOperativo());
            }

            if (dto.getNameSpace() != null) {
                dispositivoAtual.get().setNameSpace(dto.getNameSpace());
            }

            if (dto.getVersaoFirmware() != null) {
                dispositivoAtual.get().setVersaoFirmware(dto.getVersaoFirmware());
            }

            if (dto.getVersaoHardware() != null) {
                dispositivoAtual.get().setVersaoHardware(dto.getVersaoHardware());
            }

            if (dto.getModelo() != null) {
                Optional<ModeloDispositivo> modelo = modeloRepository.findById(dto.getModelo());
                if (!modelo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_modelo"));
                }
                dispositivoAtual.get().setModelo(modelo.get());
            }

            if (dto.getIdTipo() != null) {
                Optional<TipoDispositivo> tipoDispositivo = tipoDispositivoRepository.findById(dto.getIdTipo());
                if (!tipoDispositivo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_tipo_dispositivo"));
                }
                dispositivoAtual.get().setTipoDispositivo(tipoDispositivo.get());
            } else if (dto.getTipo() != null) {
                Optional<TipoDispositivo> tipoDispositivo = tipoDispositivoRepository.findByCodigo(dto.getTipo());
                if (!tipoDispositivo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_tipo_dispositivo"));
                }
                dispositivoAtual.get().setTipoDispositivo(tipoDispositivo.get());
            }

            //Atualizo o objeto utilizando o repositório
            Dispositivo atualizado = dispositivoRepository.save(dispositivoAtual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um dispositivo", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }

}
