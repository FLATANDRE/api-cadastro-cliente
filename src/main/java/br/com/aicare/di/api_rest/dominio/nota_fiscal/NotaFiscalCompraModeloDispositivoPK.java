/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.nota_fiscal;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Paulo Collares
 */
@Embeddable
public class NotaFiscalCompraModeloDispositivoPK implements Serializable {

    @Column(name = "id_nota_fiscal")
    private int idNotaFiscalCompra;

    @Column(name = "id_modelo_dispositivo")
    private int idModeloDispositivo;

    public NotaFiscalCompraModeloDispositivoPK() {
    }

    public NotaFiscalCompraModeloDispositivoPK(int idNotaFiscalCompra, int idModeloDispositivo) {
        this.idNotaFiscalCompra = idNotaFiscalCompra;
        this.idModeloDispositivo = idModeloDispositivo;
    }

    public int getIdNotaFiscalCompra() {
        return idNotaFiscalCompra;
    }

    public void setIdNotaFiscalCompra(int idNotaFiscalCompra) {
        this.idNotaFiscalCompra = idNotaFiscalCompra;
    }

    public int getIdModeloDispositivo() {
        return idModeloDispositivo;
    }

    public void setIdModeloDispositivo(int idModeloDispositivo) {
        this.idModeloDispositivo = idModeloDispositivo;
    }

}
