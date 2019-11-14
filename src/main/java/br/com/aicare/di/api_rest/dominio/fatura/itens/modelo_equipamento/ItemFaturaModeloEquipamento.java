/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_equipamento;

import br.com.aicare.di.api_rest.dominio.equipamento.ModeloEquipamento;
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
@Table(name = "item_fatura_modelo_equipamento")
public class ItemFaturaModeloEquipamento implements Serializable {

    @EmbeddedId
    private ItemFaturaModeloEquipamentoPK id = new ItemFaturaModeloEquipamentoPK();

    @JoinColumn(name = "id_fatura", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFatura")
    private Fatura fatura;

    @JoinColumn(name = "id_modelo_equipamento", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idModeloEquipamento")
    private ModeloEquipamento modeloEquipamento;

    @Column(name = "preco_unitario", columnDefinition = "float default 0")
    private float precoUnitario;

    @Column(name = "quantidade", columnDefinition = "int default 0")
    private int quantidade;

    @JsonIgnore
    public ItemFaturaModeloEquipamentoPK getId() {
        return id;
    }

    public void setId(ItemFaturaModeloEquipamentoPK id) {
        this.id = id;
    }

    public ModeloEquipamento getModeloEquipamento() {
        return modeloEquipamento;
    }

    public void setModeloEquipamento(ModeloEquipamento modeloEquipamento) {
        this.modeloEquipamento = modeloEquipamento;
    }

    @JsonIgnore
    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
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
        hash = 23 * hash + this.getModeloEquipamento().getId();
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
        final ItemFaturaModeloEquipamento other = (ItemFaturaModeloEquipamento) obj;
        if (this.getFatura().getId() != other.getFatura().getId()) {
            return false;
        }
        if (this.getModeloEquipamento().getId() != other.getModeloEquipamento().getId()) {
            return false;
        }
        return true;
    }

}
