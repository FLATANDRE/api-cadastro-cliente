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
public class CondicoesContratuaisDispositivoPK implements Serializable {

    @Column(name = "id_condicoes_contratuais")
    private int idCondicoesContratuais;

    @Column(name = "id_dispositivo")
    private int idModeloDispositivo;

    public CondicoesContratuaisDispositivoPK() {
    }

    public CondicoesContratuaisDispositivoPK(int idCondicoesContratuais, int idModeloDispositivo) {
        this.idCondicoesContratuais = idCondicoesContratuais;
        this.idModeloDispositivo = idModeloDispositivo;
    }

    public int getIdCondicoesContratuais() {
        return idCondicoesContratuais;
    }

    public void setIdCondicoesContratuais(int idCondicoesContratuais) {
        this.idCondicoesContratuais = idCondicoesContratuais;
    }

    public int getIdModeloDispositivo() {
        return idModeloDispositivo;
    }

    public void setIdModeloDispositivo(int idModeloDispositivo) {
        this.idModeloDispositivo = idModeloDispositivo;
    }

}
