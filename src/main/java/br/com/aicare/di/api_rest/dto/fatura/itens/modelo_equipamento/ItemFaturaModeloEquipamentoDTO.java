/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.fatura.itens.modelo_equipamento;

import br.com.aicare.di.api_rest.dto.equipamento.ModeloEquipamentoDTO;

public class ItemFaturaModeloEquipamentoDTO{

    private ModeloEquipamentoDTO modeloEquipamento;
    private float precoUnitario;
    private int quantidade;

    public ModeloEquipamentoDTO getModeloEquipamento() {
        return modeloEquipamento;
    }

    public void setModeloEquipamento(ModeloEquipamentoDTO modeloEquipamento) {
        this.modeloEquipamento = modeloEquipamento;
    }

    public float getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    
}