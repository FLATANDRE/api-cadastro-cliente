/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_dispositivo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Paulo Collares
 */
@Embeddable
public class ItemFaturaModeloDispositivoPK implements Serializable {

    @Column(name = "id_fatura")
    private int idFatura;

    @Column(name = "id_modelo_dispositivo")
    private int idModeloDispositivo;

    public ItemFaturaModeloDispositivoPK() {
    }

    public ItemFaturaModeloDispositivoPK(int idFatura, int idModeloDispositivo) {
        this.idFatura = idFatura;
        this.idModeloDispositivo = idModeloDispositivo;
    }

    public int getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }

    public int getIdModeloDispositivo() {
        return idModeloDispositivo;
    }

    public void setIdModeloDispositivo(int idModeloDispositivo) {
        this.idModeloDispositivo = idModeloDispositivo;
    }

}
