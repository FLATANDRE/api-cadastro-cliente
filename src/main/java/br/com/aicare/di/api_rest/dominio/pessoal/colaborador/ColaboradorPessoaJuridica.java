/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.colaborador;

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
@Table(name = "colaborador_pessoa_juridica")
public class ColaboradorPessoaJuridica implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ColaboradorPessoaJuridicaPK id = new ColaboradorPessoaJuridicaPK();

    @JoinColumn(name = "id_colaborador", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idColaborador")
    private Colaborador colaborador;

    @JoinColumn(name = "id_pessoa_juridica", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPessoaJuridica")
    private PessoaJuridica pessoaJuridica;

    public ColaboradorPessoaJuridica() {
    }

    public ColaboradorPessoaJuridica(Colaborador colaborador, PessoaJuridica pessoaJuridica) {
        this.colaborador = colaborador;
        this.pessoaJuridica = pessoaJuridica;
    }

    public ColaboradorPessoaJuridicaPK getId() {
        return id;
    }

    public void setId(ColaboradorPessoaJuridicaPK id) {
        this.id = id;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    
    
}
