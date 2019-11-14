/*
 * AICare EHR - Artificial Intelligence Care (Electronic Health Record)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.evento;

import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.uteis.Data;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Duration;
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
@Table(name = "interacao")
public class Interacao implements Serializable {

    @Id
    @SequenceGenerator(name = "interacao_seq", sequenceName = "interacao_seq", allocationSize = 1)
    @GeneratedValue(generator = "interacao_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "inicio")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inicio;

    @Column(name = "fim")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dispositivo1", nullable = false)
    private Dispositivo dispositivo1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dispositivo2", nullable = false)
    private Dispositivo dispositivo2;

    public Interacao() {
    }

    @ApiModelProperty(notes = "Identificador Único da interação", required = true, position = -1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "Inicio da interação", required = true)
    public Date getInicio() {
        return inicio;
    }

    @Transient
    public String getInicioFormatado() {
        if (inicio != null) {
            Data data = new Data(inicio);
            return data.toString();
        }
        return null;
    }

    @Transient
    public String getInicioTempo() {
        if (inicio != null) {
            Data data = new Data(inicio);
            return data.tempoAtras();
        }
        return null;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    @ApiModelProperty(notes = "Fim da interção", required = true)
    public Date getFim() {
        return fim;
    }

    @Transient
    public String getFimFormatado() {
        if (fim != null) {
            Data data = new Data(fim);
            return data.toString();
        }
        return null;
    }

    @Transient
    public String getFimTempo() {
        if (fim != null) {
            Data data = new Data(fim);
            return data.tempoAtras();
        }
        return null;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    @Transient
    public Date getDuracao() {
        if (fim == null) {
            return null;
        }
        long t = fim.getTime() - inicio.getTime();
        return new Date(t);
    }
    
    @Transient
    public String getDuracaoFormatada() {
        Date duracao = getDuracao();
        if (duracao!= null) {
            Data data = new Data(duracao);
            return data.duracao();
        }
        return null;
    }

    public Dispositivo getDispositivo1() {
        return dispositivo1;
    }

    public void setDispositivo1(Dispositivo dispositivo1) {
        this.dispositivo1 = dispositivo1;
    }

    public Dispositivo getDispositivo2() {
        return dispositivo2;
    }

    public void setDispositivo2(Dispositivo dispositivo2) {
        this.dispositivo2 = dispositivo2;
    }

}
