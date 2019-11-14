/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.equipamento;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.aicare.di.api_rest.dominio.equipamento.ModeloEquipamento;
import br.com.aicare.di.api_rest.dominio.equipamento.bomba.Bomba;
import br.com.aicare.di.api_rest.dominio.equipamento.bomba.TipoBomba;
import br.com.aicare.di.api_rest.dto.equipamento.bomba_infusao.BombaDTO;
import br.com.aicare.di.api_rest.repository.equipamento.BombaRepository;
import br.com.aicare.di.api_rest.repository.equipamento.ModeloEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.equipamento.TipoBombaRepository;
import br.com.aicare.di.api_rest.specifications.equipamento.BombaSpecificationFactory;
import br.com.aicare.di.api_rest.uteis.ApiError;
import br.com.aicare.di.api_rest.uteis.PageableFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM"})
@RequestMapping("/equipamentos/bombas")
@Api(tags = "Bomba", description = "Equipamento bomba")
public class BombaController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BombaRepository bombaRepository;

    @Autowired
    TipoBombaRepository tipoBombaRepository;

    @Autowired
    ModeloEquipamentoRepository modeloRepository;

    @ApiOperation(value = "Lista as Bombas")
    @GetMapping()
    public Page<Bomba> listar(
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

        LOGGER.info("Pesquisando lista de equipamento Bomba");

        Specification<Bomba> specification = Specification.where(null);

        if (q != null) {
            specification = specification.and(BombaSpecificationFactory.busca(q));
        }

        Pageable pageable = new PageableFactory(page, size, sort).getPageable();
        return bombaRepository.findAll(specification, pageable);
    }

    @ApiOperation(value = "Busca uma Bomba pelo serial number")
    @GetMapping(value = "/serial/{serial}")
    public ResponseEntity<Bomba> listar(@PathVariable String serial) {
        Optional<Bomba> dispositivo = bombaRepository.findBySerialNumber(serial);

        if (!dispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(dispositivo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca uma bomba pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Bomba> listar(@PathVariable Integer id) {
        Optional<Bomba> dispositivo = bombaRepository.findById(id);

        if (!dispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
        }

        return new ResponseEntity<>(dispositivo.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Cria um novo Equipamento Bomba")
    @PostMapping()
    public ResponseEntity<Bomba> criar(@RequestBody BombaDTO dto, UriComponentsBuilder ucBuilder) {
        try {
            LOGGER.info("Inserindo um novo equipamento bomba.");
            //Crio um objeto da entidade preenchendo com os valores do DTO e validando
            Bomba bomba = new Bomba();

            if (dto.isOperativo() == null) {
                bomba.setOperativo(true);
            } else {
                bomba.setOperativo(dto.isOperativo());
            }

            if (dto.getSerialNumber() == null || dto.getSerialNumber().isEmpty()) {
                return ApiError.notFound(Translator.toLocale("serial_number_nao_informado"));
            }
            if (bombaRepository.serialNumberExistente(dto.getSerialNumber())) {
                return ApiError.notFound(Translator.toLocale("serial_number_ja_cadastrado"));
            }
            bomba.setSerialNumber(dto.getSerialNumber());

            if (dto.getTipoBomba() == null) {
                return ApiError.badRequest(Translator.toLocale("validacao_tipo_bomba"));
            }
            Optional<TipoBomba> tipoBomba = tipoBombaRepository.findById(dto.getTipoBomba());
            if (!tipoBomba.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_tipo_bomba"));
            }
            bomba.setTipoBomba(tipoBomba.get());

            if (dto.getModelo() == null) {
                dto.setModelo(1);
            }
            Optional<ModeloEquipamento> modelo = modeloRepository.findById(dto.getModelo());
            if (!modelo.isPresent()) {
                return ApiError.badRequest(Translator.toLocale("validacao_modelo"));
            }
            bomba.setModelo(modelo.get());

            bomba.setVersaoSoftware(dto.getVersaoSoftware());
            bomba.setVersaoHardware(dto.getVersaoHardware());

            //Salvo o objeto utilizando o repositÃ³rio           
            Bomba novo = bombaRepository.save(bomba);

            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null) {
                return ApiError.badRequest(Translator.toLocale("erro_criacao"));
            }

            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao criar um equipamento bomba", e);
            return ApiError.internalServerError(Translator.toLocale("erro_criacao"));
        }
    }

    @ApiOperation(value = "Atualiza um Equipamento Bomba")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Bomba> atualizar(@PathVariable("id") int id, @RequestBody BombaDTO dto) {
        try {
            LOGGER.info("Atualizando objeto ID = " + id);
            Optional<Bomba> equipamentoBombaAtual = bombaRepository.findById(id);

            if (!equipamentoBombaAtual.isPresent()) {
                return ApiError.notFound(Translator.toLocale("registro_nao_encontrado"));
            }

            if (dto.isOperativo() != null) {
                equipamentoBombaAtual.get().setOperativo(dto.isOperativo());
            }

            if (dto.getTipoBomba() != null) {
                Optional<TipoBomba> tipoBomba = tipoBombaRepository.findById(dto.getTipoBomba());
                if (!tipoBomba.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_tipo_bomba"));
                }
                equipamentoBombaAtual.get().setTipoBomba(tipoBomba.get());
            }

            if (dto.getModelo() != null) {
                Optional<ModeloEquipamento> modelo = modeloRepository.findById(dto.getModelo());
                if (!modelo.isPresent()) {
                    return ApiError.badRequest(Translator.toLocale("validacao_modelo"));
                }
                equipamentoBombaAtual.get().setModelo(modelo.get());
            }

            if (dto.getVersaoSoftware() != null) {
                equipamentoBombaAtual.get().setVersaoSoftware(dto.getVersaoSoftware());
            }

            if (dto.getVersaoHardware() != null) {
                equipamentoBombaAtual.get().setVersaoHardware(dto.getVersaoHardware());
            }

            //Atualizo o objeto utilizando o repositÃ³rio
            Bomba atualizado = bombaRepository.save(equipamentoBombaAtual.get());

            //Se ocorreu algum erro, retorno esse erro para a API
            if (atualizado == null) {
                return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
            }

            //Se foi criado com sucesso, retorno o objeto atualizado
            return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar um equipamento bomba", e);
            return ApiError.internalServerError(Translator.toLocale("erro_atualizacao"));
        }
    }
}
