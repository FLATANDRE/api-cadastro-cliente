/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.enums;

/**
 *
 * @author FERNANDA
 */
public enum Cor {
    
    BRANCA("Branca"),
    PRETA("Preta"),
    AMARELA("Amarela"),
    PARDA("Parda"),
    INDIGENA("Indigena");

    private final String nome;

    private Cor(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
