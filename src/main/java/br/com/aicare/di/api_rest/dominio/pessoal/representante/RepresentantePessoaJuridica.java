/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.representante;

import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
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
@Table(name = "representante_pessoa_juridica")
public class RepresentantePessoaJuridica implements Serializable {

    @EmbeddedId
    private RepresentantePessoaJuridicaPK id = new RepresentantePessoaJuridicaPK();

    @JoinColumn(name = "id_representante", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idRepresentante")
    private Representante representante;

    @JoinColumn(name = "id_pessoa_juridica", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPessoaJuridica")
    private PessoaJuridica pessoaJuridica;

    public RepresentantePessoaJuridicaPK getId() {
        return id;
    }

    public void setId(RepresentantePessoaJuridicaPK id) {
        this.id = id;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

}
