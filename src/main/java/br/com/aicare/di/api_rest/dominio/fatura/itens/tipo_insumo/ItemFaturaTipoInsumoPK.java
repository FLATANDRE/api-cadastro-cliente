/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.itens.tipo_insumo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Paulo Collares
 */
@Embeddable
public class ItemFaturaTipoInsumoPK implements Serializable {

    @Column(name = "id_fatura")
    private int idFatura;

    @Column(name = "id_tipo_insumo")
    private int idTipoInsumo;

    public ItemFaturaTipoInsumoPK() {
    }

    public ItemFaturaTipoInsumoPK(int idFatura, int idTipoInsumo) {
        this.idFatura = idFatura;
        this.idTipoInsumo = idTipoInsumo;
    }

    public int getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }

    public int getIdTipoInsumo() {
        return idTipoInsumo;
    }

    public void setIdTipoInsumo(int idTipoInsumo) {
        this.idTipoInsumo = idTipoInsumo;
    }

}
