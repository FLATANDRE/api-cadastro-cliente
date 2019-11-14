/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.enums;

/**
 *
 * @author FERNANDA
 */
public enum Genero {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    TRANS_FEMININO("Trans Feminino"),
    TRANS_MASCULINO("Trans Masculino"),
    HERMAFRODITA("Hermafrodita"),
    NAO_DECLARADO("Não Declarado");

    private final String nome;

    private Genero(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
