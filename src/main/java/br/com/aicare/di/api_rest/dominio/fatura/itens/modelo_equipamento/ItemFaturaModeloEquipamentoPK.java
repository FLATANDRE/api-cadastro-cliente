/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_equipamento;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Paulo Collares
 */
@Embeddable
public class ItemFaturaModeloEquipamentoPK implements Serializable {

    @Column(name = "id_fatura")
    private int idFatura;

    @Column(name = "id_modelo_equipamento")
    private int idModeloEquipamento;

    public ItemFaturaModeloEquipamentoPK() {
    }

    public ItemFaturaModeloEquipamentoPK(int idFatura, int idModeloEquipamento) {
        this.idFatura = idFatura;
        this.idModeloEquipamento = idModeloEquipamento;
    }

    public int getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }

    public int getIdModeloEquipamento() {
        return idModeloEquipamento;
    }

    public void setIdModeloEquipamento(int idModeloEquipamento) {
        this.idModeloEquipamento = idModeloEquipamento;
    }

}
