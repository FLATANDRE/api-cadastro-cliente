/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.ordem_producao;

import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "ordem_producao_modelo_dispositivo")
public class OrdemProducaoModeloDispositivo implements Serializable {

    @EmbeddedId
    private OrdemProducaoModeloDispositivoPK id = new OrdemProducaoModeloDispositivoPK();

    @JoinColumn(name = "id_ordem_producao", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idOrdemProducao")
    private OrdemProducao ordemProducao;

    @JoinColumn(name = "id_modelo_dispositivo", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idModeloDispositivo")
    private ModeloDispositivo modeloDispositivo;

    @Column(name = "preco_unitario", columnDefinition = "float default 0")
    private float precoUnitario;

    @Column(name = "quantidade", columnDefinition = "int default 0")
    private int quantidade;

    @JsonIgnore
    public OrdemProducaoModeloDispositivoPK getId() {
        return id;
    }

    public void setId(OrdemProducaoModeloDispositivoPK id) {
        this.id = id;
    }

    @JsonIgnore
    public OrdemProducao getOrdemProducao() {
        return ordemProducao;
    }

    public void setOrdemProducao(OrdemProducao ordemProducao) {
        this.ordemProducao = ordemProducao;
    }

    public ModeloDispositivo getModeloDispositivo() {
        return modeloDispositivo;
    }

    public void setModeloDispositivo(ModeloDispositivo modeloDispositivo) {
        this.modeloDispositivo = modeloDispositivo;
    }

    public float getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(float precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.getOrdemProducao().getId();
        hash = 23 * hash + this.getModeloDispositivo().getId();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdemProducaoModeloDispositivo other = (OrdemProducaoModeloDispositivo) obj;
        if (this.getOrdemProducao().getId() != other.getOrdemProducao().getId()) {
            return false;
        }
        if (this.getModeloDispositivo().getId() != other.getModeloDispositivo().getId()) {
            return false;
        }
        return true;
    }

}
