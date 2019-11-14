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
public class RepresentantePessoaJuridicaPK implements Serializable {

    @Column(name = "id_representante")
    private int idRepresentante;

    @Column(name = "id_pessoa_juridica")
    private int idPessoaJuridica;

    public RepresentantePessoaJuridicaPK() {
    }

    public RepresentantePessoaJuridicaPK(int idRepresentante, int idPessoaJuridica) {
        this.idRepresentante = idRepresentante;
        this.idPessoaJuridica = idPessoaJuridica;
    }

    public int getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(int idRepresentante) {
        this.idRepresentante = idRepresentante;
    }

    public int getIdPessoaJuridica() {
        return idPessoaJuridica;
    }

    public void setIdPessoaJuridica(int idPessoaJuridica) {
        this.idPessoaJuridica = idPessoaJuridica;
    }

    
}
