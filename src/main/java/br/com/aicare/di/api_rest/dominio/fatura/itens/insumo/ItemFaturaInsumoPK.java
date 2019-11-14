/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.itens.insumo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class ItemFaturaInsumoPK {

    @Column(name = "id_fatura")
    private int idFatura;

    @Column(name = "id_insumo")
    private int idInsumo;

    public int getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }
    
}