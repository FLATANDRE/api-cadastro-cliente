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
public class LocalPlanejadoPK implements Serializable {

    @Column(name = "id_condicoes_contratuais")
    private int idCondicoesContratuais;

    @Column(name = "id_localizacao_fisica")
    private int idLocalizacaoFisica;

    public LocalPlanejadoPK() {
    }

    public LocalPlanejadoPK(int idCondicoesContratuais, int idLocalizacaoFisica) {
        this.idCondicoesContratuais = idCondicoesContratuais;
        this.idLocalizacaoFisica = idLocalizacaoFisica;
    }

    public int getIdCondicoesContratuais() {
        return idCondicoesContratuais;
    }

    public void setIdCondicoesContratuais(int idCondicoesContratuais) {
        this.idCondicoesContratuais = idCondicoesContratuais;
    }

    public int getIdLocalizacaoFisica() {
        return idLocalizacaoFisica;
    }

    public void setIdLocalizacaoFisica(int idLocalizacaoFisica) {
        this.idLocalizacaoFisica = idLocalizacaoFisica;
    }

    
   
}
