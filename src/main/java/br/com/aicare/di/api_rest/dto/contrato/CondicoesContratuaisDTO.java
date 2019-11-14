/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.contrato;

import java.util.Set;

public class CondicoesContratuaisDTO {

    private String id;
    private String dataAssinatura;
    private String dataEncerramento;

    private Set<LocalizacaoObjetoDTO> possiveisLocais;
    private Set<CondicoesContratuaisDispositivoDTO> modelosDispositivosContratados;
    private Set<CondicoesContratuaisEquipamentoDTO> modelosEquipamentosContratados;
    private Set<InsumoContratadoDTO> insumosCobertos;
       
    
    public String getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(String dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Set<CondicoesContratuaisDispositivoDTO> getModelosDispositivosContratados() {
        return modelosDispositivosContratados;
    }

    public void setModelosDispositivosContratados(
            Set<CondicoesContratuaisDispositivoDTO> modelosDispositivosContratados) {
        this.modelosDispositivosContratados = modelosDispositivosContratados;
    }

    public Set<CondicoesContratuaisEquipamentoDTO> getModelosEquipamentosContratados() {
        return modelosEquipamentosContratados;
    }

    public void setModelosEquipamentosContratados(
            Set<CondicoesContratuaisEquipamentoDTO> modelosEquipamentosContratados) {
        this.modelosEquipamentosContratados = modelosEquipamentosContratados;
    }

    public Set<InsumoContratadoDTO> getInsumosCobertos() {
        return insumosCobertos;
    }

    public void setInsumosCobertos(Set<InsumoContratadoDTO> insumosCobertos) {
        this.insumosCobertos = insumosCobertos;
    }

    public Set<LocalizacaoObjetoDTO> getPossiveisLocais() {
        return possiveisLocais;
    }

    public void setPossiveisLocais(Set<LocalizacaoObjetoDTO> possiveisLocais) {
        this.possiveisLocais = possiveisLocais;
    }

    public int getSomaQuantidadeContratada() {
        int soma = 0;
        if ( this.getModelosEquipamentosContratados() != null ) {
            soma += this.getModelosEquipamentosContratados()
                       .stream()
                       .reduce(0, (subtotal, item) -> subtotal + item.getQuantidadeContratada(), Integer::sum);
        }

        if ( this.getModelosDispositivosContratados() != null ) {
            soma += this.getModelosDispositivosContratados()
                       .stream()
                       .reduce(0, (subtotal, item) -> subtotal + item.getQuantidadeContratada(), Integer::sum);
        }
        return soma;
    }

    public String getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(String dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }
    
}
	