/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento.bomba;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author fernanda
 */
@Embeddable
public class BombaRackBombaPK implements Serializable {

    @Column(name = "id_bomba")
    private int idBomba;

    @Column(name = "id_rack_bomba")
    private int idRackBomba;

    public BombaRackBombaPK() {
    }

    public BombaRackBombaPK(int idBomba, int idRackBomba) {
        this.idBomba = idBomba;
        this.idRackBomba = idRackBomba;
    }

    public int getIdBomba() {
        return idBomba;
    }

    public void setIdBomba(int idBomba) {
        this.idBomba = idBomba;
    }

    public int getIdRackBomba() {
        return idRackBomba;
    }

    public void setIdRackBomba(int idRackBomba) {
        this.idRackBomba = idRackBomba;
    }

   
}
