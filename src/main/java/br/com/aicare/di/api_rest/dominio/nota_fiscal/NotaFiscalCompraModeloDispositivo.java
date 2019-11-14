/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.nota_fiscal;

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
@Table(name = "nota_fiscal_compra_modelo_dispositivo")
public class NotaFiscalCompraModeloDispositivo implements Serializable {

    @EmbeddedId
    private NotaFiscalCompraModeloDispositivoPK id = new NotaFiscalCompraModeloDispositivoPK();

    @JoinColumn(name = "id_nota_fiscal", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idNotaFiscalCompra")
    private NotaFiscalCompra notaFiscal;

    @JoinColumn(name = "id_modelo_dispositivo", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idModeloDispositivo")
    private ModeloDispositivo modeloDispositivo;

    @Column(name = "preco_unitario", columnDefinition = "float default 0")
    private float precoUnitario;

    @Column(name = "quantidade", columnDefinition = "int default 0")
    private int quantidade;

    @JsonIgnore
    public NotaFiscalCompraModeloDispositivoPK getId() {
        return id;
    }

    public void setId(NotaFiscalCompraModeloDispositivoPK id) {
        this.id = id;
    }

    @JsonIgnore
    public NotaFiscalCompra getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscalCompra notaFiscal) {
        this.notaFiscal = notaFiscal;
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
        hash = 23 * hash + this.getNotaFiscal().getId();
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
        final NotaFiscalCompraModeloDispositivo other = (NotaFiscalCompraModeloDispositivo) obj;
        if (this.getNotaFiscal().getId() != other.getNotaFiscal().getId()) {
            return false;
        }
        if (this.getModeloDispositivo().getId() != other.getModeloDispositivo().getId()) {
            return false;
        }
        return true;
    }

}
