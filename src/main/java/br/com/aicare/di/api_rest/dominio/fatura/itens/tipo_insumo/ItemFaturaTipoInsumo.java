/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.itens.tipo_insumo;

import br.com.aicare.di.api_rest.dominio.insumo.TipoInsumo;
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
@Table(name = "item_fatura_tipo_insumo")
public class ItemFaturaTipoInsumo implements Serializable {

    @EmbeddedId
    private ItemFaturaTipoInsumoPK id = new ItemFaturaTipoInsumoPK();

    @JoinColumn(name = "id_fatura", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idFatura")
    private Fatura fatura;

    @JoinColumn(name = "id_tipo_insumo", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idTipoInsumo")
    private TipoInsumo tipoInsumo;

    @Column(name = "preco_unitario", columnDefinition = "float default 0")
    private float precoUnitario;

    @Column(name = "quantidade", columnDefinition = "int default 0")
    private int quantidade;

    @JsonIgnore
    public ItemFaturaTipoInsumoPK getId() {
        return id;
    }

    public void setId(ItemFaturaTipoInsumoPK id) {
        this.id = id;
    }

    @JsonIgnore
    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public TipoInsumo getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
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
        hash = 23 * hash + this.getTipoInsumo().getId();
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
        final ItemFaturaTipoInsumo other = (ItemFaturaTipoInsumo) obj;
        if (this.getFatura().getId() != other.getFatura().getId()) {
            return false;
        }
        if (this.getTipoInsumo().getId() != other.getTipoInsumo().getId()) {
            return false;
        }
        return true;
    }

}
