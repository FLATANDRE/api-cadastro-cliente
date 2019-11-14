/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.uteis;

/**
 * Validador de número de telefones válidos no Brasil. Nenhum DDD iniciado por 0
 * é aceito, e nenhum número de telefone pode iniciar com 0 ou 1. Exemplos
 * válidos: +55 (11) 98888-8888 / 9999-9999 / 21 98888-8888 / 5511988888888
 *
 * @author Paulo Collares
 */
public class NumeroTelefone {

    private final String telefone;

    public NumeroTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isValido() {
        if (telefone == null) {
            return false;
        }

        if (telefone.matches("\\d{8,}")) {
            return true;
        }

        return false;
    }

    public String getTelefone() {
        return telefone;
    }

    public String toString() {
        return getTelefone();
    }

}
