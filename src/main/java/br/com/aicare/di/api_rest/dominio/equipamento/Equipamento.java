/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento;

import br.com.aicare.di.api_rest.dominio.dispositivo.associacao.AssociacaoDispositivoEquipamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "equipamento")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Equipamento implements Serializable {

    @Id
    @SequenceGenerator(name = "equipamento_seq", sequenceName = "equipamento_seq", allocationSize = 1)
    @GeneratedValue(generator = "equipamento_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "modelo")
    private ModeloEquipamento modelo;

    @Column(name = "operativo")
    private boolean operativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipamento")
    private Set<AssociacaoDispositivoEquipamento> dispositivosAssociados;

    public Equipamento() {
    }

    @ApiModelProperty(notes = "Identificador Único do equipamento", required = true, position = -1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "Serial number do equipamento", required = true)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ModeloEquipamento getModelo() {
        ModeloEquipamento modeloSimplificado = new ModeloEquipamento();
        modeloSimplificado.setFabricante(modelo.getFabricante());
        modeloSimplificado.setIntervaloManutencaoObrigatoria(modelo.getIntervaloManutencaoObrigatoria());
        modeloSimplificado.setNome(modelo.getNome());
        modeloSimplificado.setId(modelo.getId());
        
        return modeloSimplificado;
    }

    public void setModelo(ModeloEquipamento modelo) {
        this.modelo = modelo;
    }

    public boolean isOperativo() {
        return operativo;
    }

    public void setOperativo(boolean operativo) {
        this.operativo = operativo;
    }

    @JsonIgnore
    public Set<AssociacaoDispositivoEquipamento> getDispositivoAssociado() {
        return dispositivosAssociados;
    }

    public void setDispositivoAssociado(Set<AssociacaoDispositivoEquipamento> dispositivoAssociado) {
        this.dispositivosAssociados = dispositivoAssociado;
    }

    @Transient
    public abstract String getTipoEquipamento();

    @Transient
    public abstract String getTipoEquipamentoNome();

}
