/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.controladores.rest.contrato.alocacao;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuais;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisDispositivo;
import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuaisEquipamento;
import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.contrato.alocacao.AlocacaoContrato;
import br.com.aicare.di.api_rest.dominio.contrato.alocacao.AlocacaoContratoDispositivo;
import br.com.aicare.di.api_rest.dominio.contrato.alocacao.AlocacaoContratoEquipamento;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import br.com.aicare.di.api_rest.dto.dispositivo.DispositivoDTO;
import br.com.aicare.di.api_rest.dto.equipamento.EquipamentoDTO;
import br.com.aicare.di.api_rest.repository.contrato.AlocacaoContratoDispositivoRepository;
import br.com.aicare.di.api_rest.repository.contrato.AlocacaoContratoEquipamentoRepository;
import br.com.aicare.di.api_rest.repository.contrato.ContratoRepository;
import br.com.aicare.di.api_rest.repository.dispositivo.DispositivoRepository;
import br.com.aicare.di.api_rest.repository.equipamento.EquipamentoRepository;
import br.com.aicare.di.api_rest.uteis.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Secured({"ROLE_ADA", "ROLE_ADM"})
@RequestMapping("/contratos")
@Api(tags = "Alocação dispositivo equipamento contrato", description = "Alocação dispositivo/equipamento com contrato")
public class AlocacaoController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());    

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    DispositivoRepository dispositivoRepository;

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    AlocacaoContratoDispositivoRepository alocacaoContratoDispositivoRepository;

    @Autowired
    AlocacaoContratoEquipamentoRepository alocacaoContratoEquipamentoRepository;


    @ApiOperation(value = "Busca dispositivos alocados para um contrato pelo id")
    @GetMapping(value = "/alocacoes/contrato/{id}/dispositivos")
    public Collection<AlocacaoContratoDispositivo> buscarDispositivosAlocados(@PathVariable Integer id) {
        Collection<AlocacaoContratoDispositivo> dispositivosAlocados = alocacaoContratoDispositivoRepository.dispositivosAlocados(id);
                
        return dispositivosAlocados;
    }

    @ApiOperation(value = "Busca equipamentos alocados para um contrato pelo id")
    @GetMapping(value = "/alocacoes/contrato/{id}/equipamentos")
    public Collection<AlocacaoContratoEquipamento> buscarEquipamentosAlocados(@PathVariable Integer id) {
        Collection<AlocacaoContratoEquipamento> equipamentosAlocados = alocacaoContratoEquipamentoRepository.equipamentosAlocados(id);
                
        return equipamentosAlocados;
    }

    @ApiOperation(value = "Busca todos dispositivos alocados")
    @GetMapping(value = "/alocacoes/dispositivos")
    public Collection<AlocacaoContratoDispositivo> allDispositivosAlocados() {
        Collection<AlocacaoContratoDispositivo> dispositivosAlocados = alocacaoContratoDispositivoRepository.all();
        
        return dispositivosAlocados;
    }

    @ApiOperation(value = "Busca todos equipamentos alocados")
    @GetMapping(value = "/alocacoes/equipamentos")
    public Collection<AlocacaoContratoEquipamento> allEquipamentosAlocados() {
        Collection<AlocacaoContratoEquipamento> equipamentosAlocados = alocacaoContratoEquipamentoRepository.all();
        
        return equipamentosAlocados;
    }


    @ApiOperation(value = "Associa um Dispositivo a um contrato")
    @PostMapping("/alocacao/contrato/{id}/dispositivos")
    @Transactional
    public ResponseEntity<Contrato> associarDispositivoContrato(
            @PathVariable int id,            
            @RequestBody List<DispositivoDTO> dto
    ) {
        try {

            Optional<Contrato> contrato = contratoRepository.findById(id);
            int quantidadePlanejada = 0;
            int idModelo = dto.size() > 0 ? dto.get(0).getModelo() : 0;    

            if (!contrato.isPresent()) {
                return ApiError.notFound(Translator.toLocale("contrato_nao_encontrado"));
            }

            if (dto.size() > 0) {
                CondicoesContratuais lastCondicao = new CondicoesContratuais();
                if (contrato.get().getCondicoesContratuais().size() > 0) {
                    for (CondicoesContratuais condicao : contrato.get().getCondicoesContratuais()) {
                        if (condicao.getModelosDispositivosContratados() != null && condicao.getModelosDispositivosContratados().size() > 0) {
                            lastCondicao = condicao;
                        }
                    }
                }
                            
                for (CondicoesContratuaisDispositivo condicaoDispositivo : lastCondicao.getModelosDispositivosContratados()) {
                    if (condicaoDispositivo.getModeloDispositivo().getId() == idModelo) {
                        quantidadePlanejada = condicaoDispositivo.getQuantidadeContratada();
                    }
                } 
            }
          
            Set<AlocacaoContrato> alocacoes = new HashSet<>();
            for (DispositivoDTO dispositivoDTO : dto) {
                AlocacaoContratoDispositivo alocacao = new AlocacaoContratoDispositivo();
                Optional<Dispositivo> dispositivo = dispositivoRepository.findById(dispositivoDTO.getId());
                
                if (dispositivo.isPresent()) {
                    alocacao.setDispositivo(dispositivo.get());
                    alocacao.setContrato(contrato.get());
                    alocacao.setAssociacao(new Date());
                }
                alocacoes.add(alocacao);
            }

            if (quantidadePlanejada < alocacoes.size()) {
                return ApiError.notFound(Translator.toLocale("quantidade_superior_planejada"));
            }

            Contrato contratoInsert = contrato.get();
            if (contratoInsert.getAlocacoes() != null && contratoInsert.getAlocacoes().size() > 0) {

                for (AlocacaoContrato alocacao : contratoInsert.getAlocacoes()) {
                    AlocacaoContratoDispositivo alocacaoDispositivo = null;

                    if (alocacao.getTipoAssociacao() == "dispositivo") {
                        alocacaoDispositivo = (AlocacaoContratoDispositivo) alocacao;
                        if (dto.size() > 0  &&  alocacaoDispositivo.getDispositivo().getModelo().getId() != dto.get(0).getModelo()) {
                            continue;
                        }
                    } else {
                        continue;
                    }

                    
                    if (alocacoes.size() <= 0 && alocacao.getTipoAssociacao() == "dispositivo") {
                        if (alocacao.getDesassociacao() == null) {
                            alocacaoDispositivo.setDesassociacao(new Date());
                            alocacaoContratoDispositivoRepository.save(alocacaoDispositivo);
                        }

                    } else if (alocacoes.size() >= 0 && alocacao.getTipoAssociacao() == "dispositivo") {      
                        
                        boolean found = false;
                        AlocacaoContratoDispositivo itemToRemove = null;
                        for (AlocacaoContrato alocacaoContrato : alocacoes) {
                            AlocacaoContratoDispositivo alocacaoDispositivoVerifica = (AlocacaoContratoDispositivo) alocacaoContrato;
                            if (alocacaoDispositivoVerifica.getDispositivo().getId() == alocacaoDispositivo.getDispositivo().getId()) {
                                found = true;
                                itemToRemove = alocacaoDispositivoVerifica;
                            }
                        }
                        
                        if (found && alocacaoDispositivo.getDesassociacao() == null) {
                            alocacoes.remove(itemToRemove);
                            //alocacaoDispositivo.setDesassociacao(new Date());
                            //alocacaoContratoDispositivoRepository.save(alocacaoDispositivo);
                        } else if (!found && alocacaoDispositivo.getDesassociacao() == null) {
                            alocacaoDispositivo.setDesassociacao(new Date());
                            alocacaoContratoDispositivoRepository.save(alocacaoDispositivo);
                        }

                    }
                }

                //contratoInsert.getDispositivosAssociados().clear();
                contratoInsert.getAlocacoes().addAll(alocacoes);

            } else {
                contratoInsert.setAlocacoes(alocacoes);
            }

            Contrato novo = contratoRepository.save(contratoInsert);
            
            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null)  return ApiError.badRequest("Ocorreu algum erro na inserção desse objeto");
            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
            
        } catch (Exception e) {
            LOGGER.error("Erro ao associar dispositivos ao contrato ", e);
            return ApiError.internalServerError(Translator.toLocale("erro_associacao"));
        }
    }


    @ApiOperation(value = "Associa um Equipamento a um contrato")
    @PostMapping("/alocacao/contrato/{id}/equipamentos")
    @Transactional
    public ResponseEntity<Contrato> associarEquipamentoContrato(
            @PathVariable int id,            
            @RequestBody List<EquipamentoDTO> dto
    ) {
        try {

            Optional<Contrato> contrato = contratoRepository.findById(id);
            int quantidadePlanejada = 0;
            int idModelo = dto.size() > 0 ? dto.get(0).getModelo() : 0;    

            if (!contrato.isPresent()) {
                return ApiError.notFound(Translator.toLocale("contrato_nao_encontrado"));
            }

            if (dto.size() > 0) {
                CondicoesContratuais lastCondicao = new CondicoesContratuais();
                if (contrato.get().getCondicoesContratuais().size() > 0) {
                    for (CondicoesContratuais condicao : contrato.get().getCondicoesContratuais()) {
                        if (condicao.getModelosEquipamentosContratados() != null && condicao.getModelosEquipamentosContratados().size() > 0) {
                            lastCondicao = condicao;
                        }
                    }
                }
                            
                for (CondicoesContratuaisEquipamento condicaoEquipamento : lastCondicao.getModelosEquipamentosContratados()) {
                    if (condicaoEquipamento.getModeloEquipamento().getId() == idModelo) {
                        quantidadePlanejada = condicaoEquipamento.getQuantidadeContratada();
                    }
                } 
            }
          
            Set<AlocacaoContrato> alocacoes = new HashSet<>();
            for (EquipamentoDTO equipamentoDTO : dto) {
                AlocacaoContratoEquipamento alocacao = new AlocacaoContratoEquipamento();
                Optional<Equipamento> equipamento = equipamentoRepository.findById(equipamentoDTO.getId());
                
                if (equipamento.isPresent()) {
                    alocacao.setEquipamento(equipamento.get());
                    alocacao.setContrato(contrato.get());
                    alocacao.setAssociacao(new Date());
                }
                alocacoes.add(alocacao);
            }

            if (quantidadePlanejada < alocacoes.size()) {
                return ApiError.notFound(Translator.toLocale("quantidade_superior_planejada"));
            }

            Contrato contratoInsert = contrato.get();
            if (contratoInsert.getAlocacoes() != null && contratoInsert.getAlocacoes().size() > 0) {

                for (AlocacaoContrato alocacao : contratoInsert.getAlocacoes()) {
                    AlocacaoContratoEquipamento alocacaoEquipamento = null;

                    if (alocacao.getTipoAssociacao() == "equipamento") {
                        alocacaoEquipamento = (AlocacaoContratoEquipamento) alocacao;
                        if (dto.size() > 0 && alocacaoEquipamento.getEquipamento().getModelo().getId() != dto.get(0).getModelo()) {
                            continue;
                        }
                    } else {
                        continue;
                    }

                    if (alocacoes.size() <= 0 && alocacao.getTipoAssociacao() == "equipamento") {
                        if (alocacao.getDesassociacao() == null) {
                            alocacaoEquipamento.setDesassociacao(new Date());
                            alocacaoContratoEquipamentoRepository.save(alocacaoEquipamento);
                        }

                    } else if (alocacoes.size() >= 0 && alocacao.getTipoAssociacao() == "equipamento") {      
                        
                        boolean found = false;
                        AlocacaoContratoEquipamento itemToRemove = null;
                        for (AlocacaoContrato alocacaoContrato : alocacoes) {
                            AlocacaoContratoEquipamento alocacaoEquipamentoVerifica = (AlocacaoContratoEquipamento) alocacaoContrato;
                            if (alocacaoEquipamentoVerifica.getEquipamento().getId() == alocacaoEquipamento.getEquipamento().getId()) {
                                found = true;
                                itemToRemove = alocacaoEquipamentoVerifica;
                            }
                        }
                        
                        if (found && alocacaoEquipamento.getDesassociacao() == null) {
                            alocacoes.remove(itemToRemove);
                        } else if (!found && alocacaoEquipamento.getDesassociacao() == null) {
                            alocacaoEquipamento.setDesassociacao(new Date());
                            alocacaoContratoEquipamentoRepository.save(alocacaoEquipamento);
                        }

                    }
                }

                contratoInsert.getAlocacoes().addAll(alocacoes);

            } else {
                contratoInsert.setAlocacoes(alocacoes);
            }

            Contrato novo = contratoRepository.save(contratoInsert);
            
            //Se ocorreu algum erro, retorno esse erro para a API
            if (novo == null)  return ApiError.badRequest("Ocorreu algum erro na inserção desse objeto");
            //Se foi criado com sucesso, retorno o objeto criado
            return new ResponseEntity<>(novo, HttpStatus.CREATED);
            
        } catch (Exception e) {
            LOGGER.error("Erro ao associar equipamentos ao contrato ", e);
            return ApiError.internalServerError(Translator.toLocale("erro_associacao"));
        }
    }
}