/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author fernanda
 */
@Embeddable
public class CondicoesContratuaisEquipamentoPK implements Serializable {

    @Column(name = "id_condicoes_contratuais")
    private int idCondicoesContratuais;

    @Column(name = "id_equipamento")
    private int idModeloEquipamento;

    public CondicoesContratuaisEquipamentoPK() {
    }

    public CondicoesContratuaisEquipamentoPK(int idCondicoesContratuais, int idModeloEquipamento) {
        this.idCondicoesContratuais = idCondicoesContratuais;
        this.idModeloEquipamento = idModeloEquipamento;
    }

    public int getIdCondicoesContratuais() {
        return idCondicoesContratuais;
    }

    public void setIdCondicoesContratuais(int idCondicoesContratuais) {
        this.idCondicoesContratuais = idCondicoesContratuais;
    }

    public int getIdModeloEquipamento() {
        return idModeloEquipamento;
    }

    public void setIdModeloEquipamento(int idModeloEquipamento) {
        this.idModeloEquipamento = idModeloEquipamento;
    }

}
