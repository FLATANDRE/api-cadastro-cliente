/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.dispositivo;

/**
 *
 * @author Paulo Collares
 */
public class TipoDispositivoDTO {

    private String nome;
    private String codigo;
    private Boolean associavelDispositivo;
    private Boolean associavelLocalizacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getAssociavelDispositivo() {
        return associavelDispositivo;
    }

    public void setAssociavelDispositivo(Boolean associavelDispositivo) {
        this.associavelDispositivo = associavelDispositivo;
    }

    public Boolean getAssociavelLocalizacao() {
        return associavelLocalizacao;
    }

    public void setAssociavelLocalizacao(Boolean associavelLocalizacao) {
        this.associavelLocalizacao = associavelLocalizacao;
    }

}
