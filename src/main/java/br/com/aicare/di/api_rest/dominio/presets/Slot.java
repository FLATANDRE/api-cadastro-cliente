/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.presets;

import br.com.aicare.di.api_rest.dominio.presets.enums.TipoMovimentoSlot;
import br.com.aicare.di.api_rest.dominio.presets.enums.TipoSlot;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "slot")
public class Slot implements Serializable {

    @Id
    @SequenceGenerator(name = "slot_seq", sequenceName = "slot_seq", allocationSize = 1)
    @GeneratedValue(generator = "slot_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "habilitado")
    private boolean habilitado;

    @Column(name = "intervalo")
    private float intervalo;

    @Column(name = "RSSI_0m")
    private int RSSI_0m;

    @Column(name = "txPower")
    private String txPower;

    @Column(name = "trigger_habilitado")
    private boolean triggerHabilitado;

    @Column(name = "nome")
    private int adversiteAction;

    @Column(name = "quantidade")
    private boolean alwaysHabilitado;

    @Column(name = "intervalo_always")
    private float intervaloAlways;

    @Column(name = "tx_power_always")
    private int txPowerAlways;

    @Column(name = "tipo_slot")
    @Enumerated(EnumType.STRING)
    private TipoSlot tipoSlot;

    @Column(name = "tipo_movimento")
    @Enumerated(EnumType.STRING)
    private TipoMovimentoSlot tipoMovimento;

    public Slot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public float getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(float intervalo) {
        this.intervalo = intervalo;
    }
    
    public int getRSSI_0m() {
        return RSSI_0m;
    }

    public void setRSSI_0m(int RSSI_0m) {
        this.RSSI_0m = RSSI_0m;
    }

    public String getTxPower() {
        return txPower;
    }

    public void setTxPower(String txPower) {
        this.txPower = txPower;
    }

    public boolean isTriggerHabilitado() {
        return triggerHabilitado;
    }

    public void setTriggerHabilitado(boolean triggerHabilitado) {
        this.triggerHabilitado = triggerHabilitado;
    }

    public int getAdversiteAction() {
        return adversiteAction;
    }

    public void setAdversiteAction(int adversiteAction) {
        this.adversiteAction = adversiteAction;
    }

    public boolean isAlwaysHabilitado() {
        return alwaysHabilitado;
    }

    public void setAlwaysHabilitado(boolean alwaysHabilitado) {
        this.alwaysHabilitado = alwaysHabilitado;
    }

    public float getIntervaloAlways() {
        return intervaloAlways;
    }

    public void setIntervaloAlways(float intervaloAlways) {
        this.intervaloAlways = intervaloAlways;
    }

    public int getTxPowerAlways() {
        return txPowerAlways;
    }

    public void setTxPowerAlways(int txPowerAlways) {
        this.txPowerAlways = txPowerAlways;
    }

    public TipoSlot getTipoSlot() {
        return tipoSlot;
    }

    public void setTipoSlot(TipoSlot tipoSlot) {
        this.tipoSlot = tipoSlot;
    }

    public TipoMovimentoSlot getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimentoSlot tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

  

}
