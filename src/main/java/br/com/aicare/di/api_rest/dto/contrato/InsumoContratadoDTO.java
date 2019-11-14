/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.contrato;


public class InsumoContratadoDTO {
    private TipoInsumoDTO tipoInsumo;

    private int quantidadeMinima;
    
    private String descricao;

    private float precoContratado;

    public TipoInsumoDTO getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(TipoInsumoDTO tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPrecoContratado() {
        return precoContratado;
    }

    public void setPrecoContratado(float precoContratado) {
        this.precoContratado = precoContratado;
    }


}