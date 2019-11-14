/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento.bomba;

import br.com.aicare.di.api_rest.dominio.equipamento.bomba.Bomba;
import br.com.aicare.di.api_rest.dominio.equipamento.bomba.RackBomba;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "bomba_rack_bomba")
public class BombaRackBomba implements Serializable {

    @EmbeddedId
    private BombaRackBombaPK id = new BombaRackBombaPK();

    @JoinColumn(name = "id_bomba", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idBomba")
    private Bomba bomba;

    @JoinColumn(name = "id_rack_bomba", referencedColumnName = "id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idRastreadorBomba")
    private RackBomba rackBomba;

    public BombaRackBomba() {
    }

    public BombaRackBomba(Bomba bomba, RackBomba rackBomba) {
        this.bomba = bomba;
        this.rackBomba = rackBomba;
    }

    public BombaRackBombaPK getId() {
        return id;
    }

    public void setId(BombaRackBombaPK id) {
        this.id = id;
    }

    public Bomba getBomba() {
        return bomba;
    }

    public void setBomba(Bomba bomba) {
        this.bomba = bomba;
    }

    public RackBomba getRackBomba() {
        return rackBomba;
    }

    public void setRackBomba(RackBomba rackBomba) {
        this.rackBomba = rackBomba;
    }
    
    

}
