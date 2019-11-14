/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.autenticacao;

/**
 *
 * @author Paulo Collares
 */
public class ApiKeyDTO {

    private String nome;
    private String dataVencimento;
    private Boolean habilitado;
    private Boolean gerarNovaKey;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Boolean getGerarNovaKey() {
        return gerarNovaKey;
    }

    public void setGerarNovaKey(Boolean gerarNovaKey) {
        this.gerarNovaKey = gerarNovaKey;
    }

}
