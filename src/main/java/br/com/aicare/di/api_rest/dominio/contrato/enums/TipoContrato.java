/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato.enums;

/**
 *
 * @author fernanda
 */
public enum TipoContrato {
    VENDA("Venda"),
    ALUGUEL("Aluguel"),
    COMODATO("Comodato"),
    MANUTENCAO("Manutenção");

    private final String nome;

    private TipoContrato(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
