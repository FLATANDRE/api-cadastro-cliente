/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.fatura.itens.modelo_dispositivo;

import br.com.aicare.di.api_rest.dto.dispositivo.ModeloDispositivoDTO;

public class ItemFaturaModeloDispositivoDTO {
    
    private ModeloDispositivoDTO modeloDispositivo;
    private float precoUnitario;
    private int quantidade;

    public ModeloDispositivoDTO getModeloDispositivo() {
        return modeloDispositivo;
    }

    public void setModeloDispositivo(ModeloDispositivoDTO modeloDispositivo) {
        this.modeloDispositivo = modeloDispositivo;
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