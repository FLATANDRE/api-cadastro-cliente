/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.ordem_producao;

/**
 *
 * @author Paulo Collares
 */
public class ItemOrdemProducaoDTO {

    private Integer modeloDispositivo;
    private Float precoUnitario;
    private Integer quantidade;

    public Integer getModeloDispositivo() {
        return modeloDispositivo;
    }

    public void setModeloDispositivo(Integer modeloDispositivo) {
        this.modeloDispositivo = modeloDispositivo;
    }

    public Float getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
