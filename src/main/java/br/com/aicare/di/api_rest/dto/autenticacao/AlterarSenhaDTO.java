/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.autenticacao;

/**
 *
 * @author Paulo Collares
 */
public class AlterarSenhaDTO {

    private String senhaAtual;
    private String senhaNova;
    private String senhaNova2;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getSenhaNova2() {
        return senhaNova2;
    }

    public void setSenhaNova2(String senhaNova2) {
        this.senhaNova2 = senhaNova2;
    }

}
