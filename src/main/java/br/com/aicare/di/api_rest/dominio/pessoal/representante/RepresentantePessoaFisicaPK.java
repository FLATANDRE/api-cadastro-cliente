/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.representante;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author fernanda
 */
@Embeddable
public class RepresentantePessoaFisicaPK implements Serializable {

    @Column(name = "id_pessoa_fisica")
    private int idPessoaFisica;

    @Column(name = "id_representante")
    private int idRepresentante;

    public RepresentantePessoaFisicaPK() {
    }

    public RepresentantePessoaFisicaPK(int idPessoaFisica, int idRepresentante) {
        this.idPessoaFisica = idPessoaFisica;
        this.idRepresentante = idRepresentante;
    }

    public int getIdPessoaFisica() {
        return idPessoaFisica;
    }

    public void setIdPessoaFisica(int idPessoaFisica) {
        this.idPessoaFisica = idPessoaFisica;
    }

    public int getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(int idRepresentante) {
        this.idRepresentante = idRepresentante;
    }

}
