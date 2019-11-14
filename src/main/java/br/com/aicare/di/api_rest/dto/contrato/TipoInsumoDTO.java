/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.contrato;

/**
 *
 * @author Paulo Collares
 */
public class TipoInsumoDTO {

    private int id;
    private String nome;
    private String codigo;
    private String descricao;
    private Double precoPadrao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoPadrao() {
        return precoPadrao;
    }

    public void setPrecoPadrao(Double precoPadrao) {
        this.precoPadrao = precoPadrao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
