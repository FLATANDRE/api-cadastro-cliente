/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.dispositivo;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuais;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisDispositivo;
import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivo;
import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoCompartimento;
import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoContainer;
import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoContrato;
import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoEquipamento;
import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import br.com.aicare.di.api_rest.dominio.predial.Compartimento;
import br.com.aicare.di.api_rest.dominio.predial.Container;
import br.com.aicare.di.api_rest.dto.dispositivo.DispositivoDTO;
import br.com.aicare.di.api_rest.repository.associacao.AssociacaoDispositivoCompartimentoRepository;
import br.com.aicare.di.api_rest.repository.associacao.AssociacaoDispositivoContainerRepository;
import br.com.aicare.di.api_rest.repository.associacao.AssociacaoDispositivoEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.associacao.AssociacaoDispositivoRepository;
import br.com.aicare.di.api_rest.repository.contrato.ContratoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.DispositivoRepository;
import br.com.aicare.di.api_rest.repository.equipamento.EquipamentoRepository;
import br.com.aicare.di.api_rest.repository.predial.CompartimentoRepository;
import br.com.aicare.di.api_rest.repository.predial.ContainerRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Paulo Collares
 */
@RestController
@CrossOrigin
@Secured({"ROLE_ADA"})
@RequestMapping("/dispositivos/associacao")
@Api(tags = "Associacao de Dispositivos", description = "Associacao de Dispositivo")
public class AssociacaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    CompartimentoRepository compartimentoRepository;

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    ContainerRepository containerRepository;

    @Autowired
    AssociacaoDispositivoRepository associacaoDispositivoRepository;

    @Autowired
    AssociacaoDispositivoEquipamentoRepository associacaoDispositivoEquipamentoRepository;

    @Autowired
    AssociacaoDispositivoCompartimentoRepository associacaoDispositivoCompartimentoRepository;

    @Autowired
    AssociacaoDispositivoContainerRepository associacaoDispositivoContainerRepository;

    @ApiOperation(value = "Obtém a associação ativa de um dispositivo")
    @GetMapping("/{mac}")
    public ResponseEntity<AssociacaoDispositivo> associacaoAtiva(
            @PathVariable String mac
    ) {

        Optional<Dispositivo> dispositivo = dispositivoRepository.findByMac(mac);

        if (!dispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("dispositivo_nao_encontrado"));
        }

        List<AssociacaoDispositivo> associacoesDispositivo = associacaoDispositivoRepository.obtemAssociacaoAtiva(dispositivo.get().getId());

        if (associacoesDispositivo != null && !associacoesDispositivo.isEmpty()) {
            return ok(associacoesDispositivo.get(0));
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Obtém um dispositivo associado a um equipamento")
    @GetMapping("/equipamento/{id}")
    public ResponseEntity<Dispositivo> dispositivoAssociadoEquipamento(
            @PathVariable int id
    ) {

        List<AssociacaoDispositivoEquipamento> associacoesDispositivo
                = associacaoDispositivoEquipamentoRepository.obtemAssociacaoAtivaEquipamento(id);

        if (associacoesDispositivo != null && !associacoesDispositivo.isEmpty()) {

            Optional<Dispositivo> dispositivo
                    = dispositivoRepository.findById(associacoesDispositivo.get(0).getDispositivo().getId());

            if (dispositivo.isPresent()) {
                return new ResponseEntity<>(dispositivo.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Associa um Dispositivo a um equipamento")
    @PutMapping("{mac}/equipamento/{serial}")
    @Transactional
    public ResponseEntity<AssociacaoDispositivoEquipamento> associarEquipamento(
            @PathVariable String mac,
            @PathVariable String serial
    ) {
        try {

            Optional<Dispositivo> dispositivo = dispositivoRepository.findByMac(mac);

            if (!dispositivo.isPresent()) {
                return ApiError.notFound(Translator.toLocale("dispositivo_nao_encontrado"));
            }

            if (associacaoDispositivoRepository.isAssociacaoAtiva(dispositivo.get().getId())) {
                List<AssociacaoDispositivo> associacoesDispositivo = associacaoDispositivoRepository.obtemAssociacaoAtiva(dispositivo.get().getId());
                return ApiError.badRequest(Translator.toLocale("erro_associacao_dispositivo_ja_associado") + " " + associacoesDispositivo.get(0).getTipoAssociacaoNome() + " - " + associacoesDispositivo.get(0).getDescricaoAssociacao());
            }

            if (!dispositivo.get().getTipoDispositivo().isAssociavelEquipamento()) {
                return ApiError.badRequest(Translator.toLocale("erro_associacao_tipo_nao_associavel_equipamento"));
            }

            Optional<Equipamento> equipamento = equipamentoRepository.findBySerialNumber(serial);

            if (!equipamento.isPresent()) {
                return ApiError.notFound(Translator.toLocale("erro_associacao_equipamento_nao_encontrado"));
            }

            AssociacaoDispositivoEquipamento associacaoDispositivoEquipamento = new AssociacaoDispositivoEquipamento();
            associacaoDispositivoEquipamento.setDispositivo(dispositivo.get());
            associacaoDispositivoEquipamento.setEquipamento(equipamento.get());
            associacaoDispositivoEquipamento.setAssociacao(new Date());

            AssociacaoDispositivoEquipamento novo = associacaoDispositivoEquipamentoRepository.save(associacaoDispositivoEquipamento);

            return ok(novo);

        } catch (Exception e) {
            LOGGER.error("Erro ao associar um dispositivo", e);
            return ApiError.internalServerError(Translator.toLocale("erro_associacao"));
        }
    }

    @ApiOperation(value = "Associa um Dispositivo a um compartimento")
    @PutMapping("{mac}/compartimento/{id}")
    @Transactional
    public ResponseEntity<AssociacaoDispositivoCompartimento> associarCompartimento(
            @PathVariable String mac,
            @PathVariable int id
    ) {
        try {

            Optional<Dispositivo> dispositivo = dispositivoRepository.findByMac(mac);

            if (!dispositivo.isPresent()) {
                return ApiError.notFound(Translator.toLocale("dispositivo_nao_encontrado"));
            }

            if (associacaoDispositivoRepository.isAssociacaoAtiva(dispositivo.get().getId())) {
                return ApiError.notFound(Translator.toLocale("erro_associacao_dispositivo_ja_associado"));
            }

            if (!dispositivo.get().getTipoDispositivo().isAssociavelLocalizacao()) {
                return ApiError.internalServerError(Translator.toLocale("erro_associacao_tipo_nao_associavel_compartimento"));
            }

            Optional<Compartimento> compartimento = compartimentoRepository.findById(id);

            if (!compartimento.isPresent()) {
                return ApiError.notFound(Translator.toLocale("erro_associacao_compartimento_nao_encontrado"));
            }

            AssociacaoDispositivoCompartimento associacaoDispositivoCompartimento = new AssociacaoDispositivoCompartimento();
            associacaoDispositivoCompartimento.setDispositivo(dispositivo.get());
            associacaoDispositivoCompartimento.setCompartimento(compartimento.get());
            associacaoDispositivoCompartimento.setAssociacao(new Date());

            AssociacaoDispositivoCompartimento novo = associacaoDispositivoCompartimentoRepository.save(associacaoDispositivoCompartimento);

            return ok(novo);

        } catch (Exception e) {
            LOGGER.error("Erro ao associar um compartimento", e);
            return ApiError.internalServerError(Translator.toLocale("erro_associacao"));
        }
    }

    @ApiOperation(value = "Associa um Dispositivo a um container")
    @PutMapping("{mac}/container/{id}")
    @Transactional
    public ResponseEntity<AssociacaoDispositivoContainer> associarContainer(
            @PathVariable String mac,
            @PathVariable int id
    ) {
        try {

            Optional<Dispositivo> dispositivo = dispositivoRepository.findByMac(mac);

            if (!dispositivo.isPresent()) {
                return ApiError.notFound(Translator.toLocale("dispositivo_nao_encontrado"));
            }

            if (associacaoDispositivoRepository.isAssociacaoAtiva(dispositivo.get().getId())) {
                return ApiError.notFound(Translator.toLocale("erro_associacao_dispositivo_ja_associado"));
            }

            if (!dispositivo.get().getTipoDispositivo().isAssociavelLocalizacao()) {
                return ApiError.internalServerError(Translator.toLocale("erro_associacao_tipo_nao_associavel_container"));
            }

            Optional<Container> container = containerRepository.findById(id);

            if (!container.isPresent()) {
                return ApiError.notFound(Translator.toLocale("erro_associacao_container_nao_encontrado"));
            }

            AssociacaoDispositivoContainer associacaoDispositivoContainer = new AssociacaoDispositivoContainer();
            associacaoDispositivoContainer.setDispositivo(dispositivo.get());
            associacaoDispositivoContainer.setContainer(container.get());
            associacaoDispositivoContainer.setAssociacao(new Date());

            AssociacaoDispositivoContainer novo = associacaoDispositivoContainerRepository.save(associacaoDispositivoContainer);

            return ok(novo);

        } catch (Exception e) {
            LOGGER.error("Erro ao associar um container", e);
            return ApiError.internalServerError(Translator.toLocale("erro_associacao"));
        }
    }


    @ApiOperation(value = "Remove todas as associações de um dispositivo")
    @DeleteMapping(value = "/{mac}")
    @Transactional
    public ResponseEntity remover(
            @PathVariable("mac") String mac
    ) {

        if (mac == null || mac.isEmpty()) {
            return ApiError.badRequest(Translator.toLocale("mac_invalido"));
        }

        Optional<Dispositivo> dispositivo = dispositivoRepository.findByMac(mac);

        if (!dispositivo.isPresent()) {
            return ApiError.notFound(Translator.toLocale("dispositivo_nao_encontrado"));
        }

        List<AssociacaoDispositivo> associacaoDispositivo = associacaoDispositivoRepository.obtemAssociacaoAtiva(dispositivo.get().getId());
        for (AssociacaoDispositivo associacao : associacaoDispositivo) {
            associacao.setDesassociacao(new Date());
            associacaoDispositivoRepository.save(associacao);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
