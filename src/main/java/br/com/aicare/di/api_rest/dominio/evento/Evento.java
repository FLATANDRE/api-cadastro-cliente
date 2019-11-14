/*
 * AICare EHR - Artificial Intelligence Care (Electronic Health Record)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.evento;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.uteis.Data;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    @Id
    @SequenceGenerator(name = "evento_seq", sequenceName = "evento_seq", allocationSize = 1)
    @GeneratedValue(generator = "evento_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "data_hora")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHora;

    @Column(name = "dado", nullable = true)
    private String dado;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dispositivo", nullable = false)
    private Dispositivo dispositivo;

    public Evento() {
    }

    @ApiModelProperty(notes = "Identificador Único do evento", required = true, position = -1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "Data do evento", required = true)
    public Date getDataHora() {
        return dataHora;
    }

    @Transient
    public String getDataHoraFormatada() {
        if (dataHora != null) {
            Data data = new Data(dataHora);
            return data.toString();
        }
        return null;
    }

    @Transient
    public String getDataHoraTempo() {
        if (dataHora != null) {
            Data data = new Data(dataHora);
            return data.tempoAtras();
        }
        return null;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getDado() {
        return dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }

    public String getTipo() {
        return tipo;
    }

    @Transient
    public String getDescricaoTipo() {
        return Translator.toLocale("tipo_evento_" + getTipo().toLowerCase());
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

}
