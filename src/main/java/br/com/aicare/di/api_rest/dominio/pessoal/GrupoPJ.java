/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal;

import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "grupo_pj")
public class GrupoPJ implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "grupo_pj_lider", joinColumns = {
        @JoinColumn(name = "id_grupo_pj")}, inverseJoinColumns = {
        @JoinColumn(name = "id_pessoa_juridica")})
    private Set<PessoaJuridica> lider;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "grupo_pj_participantes", joinColumns = {
        @JoinColumn(name = "id_grupo_pj")}, inverseJoinColumns = {
        @JoinColumn(name = "id_pessoa_juridica")})
    private Set<PessoaJuridica> participantes;

    public GrupoPJ() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Set<PessoaJuridica> getLider() {
        return lider;
    }

    public void setLider(Set<PessoaJuridica> lider) {
        this.lider = lider;
    }

    public Set<PessoaJuridica> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<PessoaJuridica> participantes) {
        this.participantes = participantes;
    }

}
