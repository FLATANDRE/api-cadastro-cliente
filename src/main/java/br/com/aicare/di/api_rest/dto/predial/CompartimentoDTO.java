/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.predial;

/**
 *
 * @author Paulo Collares
 */
public class CompartimentoDTO {

    private String nome;
    private String descricao;
    private Integer localizacaoFisica;
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

    public Integer getLocalizacaoFisica() {
        return localizacaoFisica;
    }

    public void setLocalizacaoFisica(Integer localizacaoFisica) {
        this.localizacaoFisica = localizacaoFisica;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

}
