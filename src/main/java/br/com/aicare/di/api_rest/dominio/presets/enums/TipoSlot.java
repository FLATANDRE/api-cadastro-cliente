/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.presets.enums;

/**
 *
 * @author fernanda
 */
public enum TipoSlot {
    UID("UID"),
    TLM("TLM"),
    ACC("ACC");

    private final String nome;

    private TipoSlot(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
