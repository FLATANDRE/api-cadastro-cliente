/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.presets.enums;

/**
 *
 * @author fernanda
 */
public enum TipoMovimentoSlot {
    MOVIMENTO("Movimento"),
    DOUBLETAP("DoubleTap"),
    TRIPLETAP("TripleTap");

    private final String nome;

    private TipoMovimentoSlot(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
