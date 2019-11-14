/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.representante;

import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaFisica;
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
@Table(name = "representante_pessoa_fisica")
public class RepresentantePessoaFisica implements Serializable {

    @EmbeddedId
    private RepresentantePessoaFisicaPK id = new RepresentantePessoaFisicaPK();

    @JoinColumn(name = "id_pessoa_fisica", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPessoaFisica")
    private PessoaFisica pessoaFisica;

    @JoinColumn(name = "id_representante", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idRepresentante")
    private Representante representante;

    public RepresentantePessoaFisica() {
    }

    public RepresentantePessoaFisica(PessoaFisica pessoaFisica, Representante representante) {
        this.pessoaFisica = pessoaFisica;
        this.representante = representante;
    }

    public RepresentantePessoaFisicaPK getId() {
        return id;
    }

    public void setId(RepresentantePessoaFisicaPK id) {
        this.id = id;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

}
