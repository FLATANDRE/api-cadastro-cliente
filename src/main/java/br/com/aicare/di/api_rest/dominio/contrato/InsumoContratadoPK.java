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
public class InsumoContratadoPK implements Serializable {

    @Column(name = "id_condicoes_contratuais")
    private int idCondicoesContratuais;

    @Column(name = "id_tipo_insumo")
    private int idTipoInsumo;

    public InsumoContratadoPK() {
    }

    public InsumoContratadoPK(int idCondicoesContratuais, int idTipoInsumo) {
        this.idCondicoesContratuais = idCondicoesContratuais;
        this.idTipoInsumo = idTipoInsumo;
    }

    public int getIdCondicoesContratuais() {
        return idCondicoesContratuais;
    }

    public void setIdCondicoesContratuais(int idCondicoesContratuais) {
        this.idCondicoesContratuais = idCondicoesContratuais;
    }

    public int getIdTipoInsumo() {
        return idTipoInsumo;
    }

    public void setIdTipoInsumo(int idTipoInsumo) {
        this.idTipoInsumo = idTipoInsumo;
    }

}
