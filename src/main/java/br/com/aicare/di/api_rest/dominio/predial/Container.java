/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.predial;

import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoContainer;
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
@Table(name = "container")
public class Container implements Serializable {

    @Id
    @SequenceGenerator(name = "container_seq", sequenceName = "container_seq", allocationSize = 1)
    @GeneratedValue(generator = "container_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "tipo_container")
    private TipoContainer tipoContainer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "container")
    private Set<AssociacaoDispositivoContainer> dispositivosAssociados;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_compartimento", nullable = false)
    private Compartimento compartimento;

    public Container() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoContainer getTipoContainer() {
        return tipoContainer;
    }

    public void setTipoContainer(TipoContainer tipoContainer) {
        this.tipoContainer = tipoContainer;
    }

    @JsonIgnore
    public Set<AssociacaoDispositivoContainer> getDispositivosAssociados() {
        return dispositivosAssociados;
    }

    public void setDispositivosAssociados(Set<AssociacaoDispositivoContainer> dispositivosAssociados) {
        this.dispositivosAssociados = dispositivosAssociados;
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

    public Compartimento getCompartimento() {
        return compartimento;
    }

    public void setCompartimento(Compartimento compartimento) {
        this.compartimento = compartimento;
    }

}
