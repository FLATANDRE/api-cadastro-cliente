/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo;

import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivo;
import br.com.aicare.di.api_rest.uteis.MAC;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "dispositivo")
public class Dispositivo implements Serializable {

    @Id
    @SequenceGenerator(name = "dispositivo_seq", sequenceName = "dispositivo_seq", allocationSize = 1)
    @GeneratedValue(generator = "dispositivo_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "mac")
    private String mac;

    @Column(name = "versao_firmware", nullable = true)
    private String versaoFirmware;

    @Column(name = "versao_hardware", nullable = true)
    private String versaoHardware;

    @Column(name = "name_space")
    private String nameSpace;

    @Column(name = "operativo")
    private boolean operativo;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estado_atual", nullable = false)
    private EstadoAtual estadoAtual;

    @ManyToOne
    @JoinColumn(name = "tipo_dispositivo")
    private TipoDispositivo tipoDispositivo;

    @ManyToOne
    @JoinColumn(name = "modelo")
    private ModeloDispositivo modelo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dispositivo")
    private Set<AssociacaoDispositivo> associacoes;

    public Dispositivo() {
    }

    @ApiModelProperty(notes = "Identificador Único do dispositivo", required = true, position = -1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "MAC Address do dispositivo", required = false)
    public String getMac() {
        return mac;
    }

    @Transient
    public String getMacFormatado() {
        return new MAC(getMac()).toString();
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @ApiModelProperty(notes = "Versão Firmware do dispositivo", required = false)
    public String getVersaoFirmware() {
        return versaoFirmware;
    }

    public void setVersaoFirmware(String versaoFirmware) {
        this.versaoFirmware = versaoFirmware;
    }

    public String getVersaoHardware() {
        return versaoHardware;
    }

    public void setVersaoHardware(String versaoHardware) {
        this.versaoHardware = versaoHardware;
    }

    /**
     * É removido o relacionamento de modelo com dispositivos, para evitar
     * a referencia ciclica. O que causa erros na desserializacao.
     * 
     * Apresenta informacoes simplificadas do modelo.
     */
    public ModeloDispositivo getModelo() {
        ModeloDispositivo modeloSimplificado = new ModeloDispositivo();
        modeloSimplificado.setId(modelo.getId());
        modeloSimplificado.setNome(modelo.getNome());
        modeloSimplificado.setDesconhecido(modelo.isDesconhecido());
        modeloSimplificado.setFabricante(modelo.getFabricante());
        modeloSimplificado.setIntervaloManutencaoObrigatoria(modelo.getIntervaloManutencaoObrigatoria());
        modeloSimplificado.setTensaoMaximaBateria(modelo.getTensaoMaximaBateria());
        modeloSimplificado.setTensaoMinimaBateria(modelo.getTensaoMinimaBateria());
                
        return modeloSimplificado;
    }

    public void setModelo(ModeloDispositivo modelo) {
        this.modelo = modelo;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public boolean isOperativo() {
        return operativo;
    }

    public void setOperativo(boolean operativo) {
        this.operativo = operativo;
    }

    public EstadoAtual getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(EstadoAtual estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    @JsonProperty("tipo")
    public TipoDispositivo getTipoDispositivo() {
        return tipoDispositivo;
    }

    public void setTipoDispositivo(TipoDispositivo tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }

    public Set<AssociacaoDispositivo> getAssociacoes() {
        return associacoes;
    }

    @JsonIgnore
    public void setAssociacoes(Set<AssociacaoDispositivo> associacoes) {
        this.associacoes = associacoes;
    }

    @Transient
    public double getPorcentagemBateria() {

        if (getModelo() == null) {
            return -1;
        }
        if (getEstadoAtual() == null) {
            return -1;
        }

        if (getModelo().getTensaoMaximaBateria() <= 0
                || getModelo().getTensaoMinimaBateria() <= 0
                || getModelo().getTensaoMaximaBateria() <= getModelo().getTensaoMinimaBateria()) {
            return -1;
        }

        double bateria = ((double) getEstadoAtual().getBateria()) / 1000;

        if (bateria > getModelo().getTensaoMaximaBateria()) {
            return 100;
        }

        if (bateria < getModelo().getTensaoMinimaBateria()) {
            return 0;
        }

        double valorMaximo = getModelo().getTensaoMaximaBateria() - getModelo().getTensaoMinimaBateria();
        double variacaoTensao = bateria - getModelo().getTensaoMinimaBateria();

        Double porcentagem = BigDecimal.valueOf((variacaoTensao / valorMaximo) * 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return porcentagem;
    }

}
