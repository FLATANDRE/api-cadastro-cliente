/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.ordem_producao;

import java.util.List;

/**
 *
 * @author Paulo Collares
 */
public class OrdemProducaoDTO {

    private String numero;
    private String data;
    private String descricao;
    private Integer fornecedor;
    private List<ItemOrdemProducaoDTO> itens;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Integer fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<ItemOrdemProducaoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemOrdemProducaoDTO> itens) {
        this.itens = itens;
    }

}
