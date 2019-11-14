/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo;

import br.com.aicare.di.api_rest.uteis.Data;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "dispositivo_estado_atual")
public class EstadoAtual implements Serializable {

    @Id
    @SequenceGenerator(name = "dispositivo_estado_atual_seq", sequenceName = "dispositivo_estado_atual_seq", allocationSize = 1)
    @GeneratedValue(generator = "dispositivo_estado_atual_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "temperatura", nullable = true, columnDefinition = "float default 0")
    private float temperatura;

    @Column(name = "eixo_z", nullable = true, columnDefinition = "float default 0")
    private float eixoZ;

    @Column(name = "eixo_x", nullable = true, columnDefinition = "float default 0")
    private float eixoX;

    @Column(name = "eixo_y", nullable = true, columnDefinition = "float default 0")
    private float eixoY;

    @Column(name = "worktime", nullable = true, columnDefinition = "bigint default 0")
    private long workTime;

    @Column(name = "bateria", nullable = true, columnDefinition = "int default 0")
    private int bateria;

    @Column(name = "visto_em", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date vistoEm;

    public EstadoAtual() {
    }

    @ApiModelProperty(notes = "Identificador Ãšnico do dispositivo", required = true, position = -1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "Temperatura emitida pelo dispositivo", required = false)
    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    @ApiModelProperty(notes = "Eixo X do giroscópio emitido do dispositivo", required = false)
    public float getEixoX() {
        return eixoX;
    }

    public void setEixoX(float deltaX) {
        this.eixoX = deltaX;
    }

    @ApiModelProperty(notes = "Eixo Y do giroscópio emitido do dispositivo", required = false)
    public float getEixoY() {
        return eixoY;
    }

    public void setEixoY(float deltaY) {
        this.eixoY = deltaY;
    }

    @ApiModelProperty(notes = "Eixo Z do giroscópio emitido do dispositivo", required = false)
    public float getEixoZ() {
        return eixoZ;
    }

    public void setEixoZ(float deltaZ) {
        this.eixoZ = deltaZ;
    }

    @ApiModelProperty(notes = "Tempo em segundos emitido do dispositivo", required = false)
    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    public Date getVistoEm() {
        return vistoEm;
    }

    @Transient
    public String getVistoEmFormatado() {
        if (vistoEm != null) {
            Data data = new Data(vistoEm);
            return data.toString();
        }
        return null;
    }

    @Transient
    public String getVistoEmTempo() {
        if (vistoEm != null) {
            Data data = new Data(vistoEm);
            return data.tempoAtras();
        }
        return null;
    }

    public void setVistoEm(Date vistoEm) {
        this.vistoEm = vistoEm;
    }

    public int getBateria() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

}
