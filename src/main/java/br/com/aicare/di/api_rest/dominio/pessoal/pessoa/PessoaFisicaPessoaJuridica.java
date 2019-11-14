/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.pessoa;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "pessoa_fisica_pessoa_juridica")
public class PessoaFisicaPessoaJuridica  implements Serializable {

    @EmbeddedId
    private PessoaFisicaPessoaJuridicaPK id = new PessoaFisicaPessoaJuridicaPK();

    @JoinColumn(name = "id_pessoa_fisica", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPessoaFisica")
    private PessoaFisica pessoaFisica;

    @JoinColumn(name = "id_pessoa_juridica", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPessoaJuridica")
    private PessoaJuridica pessoaJuridica;

    public PessoaFisicaPessoaJuridica() {
    }

    public PessoaFisicaPessoaJuridica(PessoaFisica pessoaFisica, PessoaJuridica pessoaJuridica) {
        this.pessoaFisica = pessoaFisica;
        this.pessoaJuridica = pessoaJuridica;
    }

    public PessoaFisicaPessoaJuridicaPK getId() {
        return id;
    }

    public void setId(PessoaFisicaPessoaJuridicaPK id) {
        this.id = id;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }


}
