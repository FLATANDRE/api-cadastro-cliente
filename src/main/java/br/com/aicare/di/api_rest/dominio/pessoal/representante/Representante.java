/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.representante;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "representante")
public class Representante implements Serializable {

    @Id
    @SequenceGenerator(name = "representante_seq", sequenceName = "representante_seq", allocationSize = 1)
    @GeneratedValue(generator = "representante_seq", strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoaFisica", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepresentantePessoaFisica> pessoasFisicas;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepresentantePessoaJuridica> pessoasJuridicas;

    //representante toma conta pj
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoaJuridica", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RepresentantePessoaJuridica> responsaveisJuridicos;

    public Representante() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<RepresentantePessoaFisica> getPessoasFisicas() {
        return pessoasFisicas;
    }

    public void setPessoasFisicas(Set<RepresentantePessoaFisica> pessoasFisicas) {
        this.pessoasFisicas = pessoasFisicas;
    }

    public Set<RepresentantePessoaJuridica> getPessoasJuridicas() {
        return pessoasJuridicas;
    }

    public void setPessoasJuridicas(Set<RepresentantePessoaJuridica> pessoasJuridicas) {
        this.pessoasJuridicas = pessoasJuridicas;
    }

    public Set<RepresentantePessoaJuridica> getResponsaveisJuridicos() {
        return responsaveisJuridicos;
    }

    public void setResponsaveisJuridicos(Set<RepresentantePessoaJuridica> responsaveisJuridicos) {
        this.responsaveisJuridicos = responsaveisJuridicos;
    }

}
