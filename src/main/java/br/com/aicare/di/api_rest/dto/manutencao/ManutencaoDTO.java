/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.manutencao;

/**
 *
 * @author Paulo Collares
 */
public class ManutencaoDTO {

    private Integer equipamento;
    private String observacao;
    private Integer tipo;

    public Integer getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Integer equipamento) {
        this.equipamento = equipamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

}
