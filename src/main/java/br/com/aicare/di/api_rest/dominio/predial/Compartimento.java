/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.predial;

import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoCompartimento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author FERNANDA
 */
@Entity
@Table(name = "compartimento")
public class Compartimento implements Serializable {

    @Id
    @SequenceGenerator(name = "compartimento_seq", sequenceName = "compartimento_seq", allocationSize = 1)
    @GeneratedValue(generator = "compartimento_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_localizacao_fisica", nullable = false)
    private LocalizacaoFisica localizacaoFisica;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compartimento")
    private Set<AssociacaoDispositivoCompartimento> dispositivosAssociados;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compartimento", referencedColumnName = "id")
    private Set<Container> conteiners;

    @ManyToOne
    @JoinColumn(name = "tipo_compartimento")
    private TipoCompartimento tipoCompartimento;

    public Compartimento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalizacaoFisica getLocalizacaoFisica() {
        return localizacaoFisica;
    }

    public void setLocalizacaoFisica(LocalizacaoFisica localizacaoFisica) {
        this.localizacaoFisica = localizacaoFisica;
    }

    @JsonIgnore
    public Set<Container> getConteiners() {
        return conteiners;
    }

    public void setConteiners(Set<Container> conteiners) {
        this.conteiners = conteiners;
    }

    @JsonIgnore
    public Set<AssociacaoDispositivoCompartimento> getDispositivosAssociados() {
        return dispositivosAssociados;
    }

    public void setDispositivosAssociados(Set<AssociacaoDispositivoCompartimento> dispositivosAssociados) {
        this.dispositivosAssociados = dispositivosAssociados;
    }

    public TipoCompartimento getTipoCompartimento() {
        return tipoCompartimento;
    }

    public void setTipoCompartimento(TipoCompartimento tipoCompartimento) {
        this.tipoCompartimento = tipoCompartimento;
    }

}
