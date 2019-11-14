/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.predial;

/**
 *
 * @author Paulo Collares
 */
public class ContainerDTO {

    private String nome;
    private String descricao;
    private Integer compartimento;
    private Integer tipo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCompartimento() {
        return compartimento;
    }

    public void setCompartimento(Integer compartimento) {
        this.compartimento = compartimento;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

}
