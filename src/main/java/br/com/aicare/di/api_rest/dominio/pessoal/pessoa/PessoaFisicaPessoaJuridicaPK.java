/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.pessoa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author fernanda
 */
@Embeddable
public class PessoaFisicaPessoaJuridicaPK implements Serializable {

    @Column(name = "id_pessoa_fisica")
    private int idPessoaFisica;

    @Column(name = "id_pessoa_juridica")
    private int idPessoaJuridica;

    public PessoaFisicaPessoaJuridicaPK() {
    }

    public PessoaFisicaPessoaJuridicaPK(int idPessoaFisica, int idPessoaJuridica) {
        this.idPessoaFisica = idPessoaFisica;
        this.idPessoaJuridica = idPessoaJuridica;
    }

    public int getIdPessoaFisica() {
        return idPessoaFisica;
    }

    public void setIdPessoaFisica(int idPessoaFisica) {
        this.idPessoaFisica = idPessoaFisica;
    }

    public int getIdPessoaJuridica() {
        return idPessoaJuridica;
    }

    public void setIdPessoaJuridica(int idPessoaJuridica) {
        this.idPessoaJuridica = idPessoaJuridica;
    }


}
