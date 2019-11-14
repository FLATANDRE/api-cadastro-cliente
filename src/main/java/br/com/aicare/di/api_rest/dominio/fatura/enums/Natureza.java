/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.enums;

/**
 *
 * @author fernanda
 */
public enum Natureza {
    VENDA("Venda"),
    ALUGUEL("Aluguel"),
    DEVOLUCAO("Devolução"),
    COMODATO("Comodato");

    private String nome;

    private Natureza(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
