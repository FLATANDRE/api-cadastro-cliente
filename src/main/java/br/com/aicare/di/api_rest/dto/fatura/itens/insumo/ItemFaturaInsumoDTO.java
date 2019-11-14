/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.fatura.itens.insumo;

import br.com.aicare.di.api_rest.dto.contrato.TipoInsumoDTO;

public class ItemFaturaInsumoDTO {

    private TipoInsumoDTO tipoInsumo;
    private float precoUnitario;
    private int quantidade;

    public TipoInsumoDTO getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(TipoInsumoDTO tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
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