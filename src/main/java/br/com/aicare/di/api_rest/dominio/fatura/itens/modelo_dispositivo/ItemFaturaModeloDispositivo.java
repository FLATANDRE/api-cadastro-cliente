/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.ModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.fatura.Fatura;
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
@Table(name = "item_fatura_modelo_dispositivo")
public class ItemFaturaModeloDispositivo implements Serializable {

    @EmbeddedId
    private ItemFaturaModeloDispositivoPK id = new ItemFaturaModeloDispositivoPK();

    @JoinColumn(name = "id_fatura", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFatura")
    private Fatura fatura;

    @JoinColumn(name = "id_modelo_dispositivo", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idModeloDispositivo")
    private ModeloDispositivo modeloDispositivo;

    @Column(name = "preco_unitario", columnDefinition = "float default 0")
    private float precoUnitario;

    @Column(name = "quantidade", columnDefinition = "int default 0")
    private int quantidade;

    @JsonIgnore
    public ItemFaturaModeloDispositivoPK getId() {
        return id;
    }

    public void setId(ItemFaturaModeloDispositivoPK id) {
        this.id = id;
    }

    @JsonIgnore
    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
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
        hash = 23 * hash + this.getFatura().getId();
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
        final ItemFaturaModeloDispositivo other = (ItemFaturaModeloDispositivo) obj;
        if (this.getFatura().getId() != other.getFatura().getId()) {
            return false;
        }
        if (this.getModeloDispositivo().getId() != other.getModeloDispositivo().getId()) {
            return false;
        }
        return true;
    }

}
