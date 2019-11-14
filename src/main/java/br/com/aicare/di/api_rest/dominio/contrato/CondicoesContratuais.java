/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato;

import br.com.aicare.di.api_rest.dominio.presets.Preset;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "condicoes_contratuais")
public class CondicoesContratuais implements Serializable {

    @Id
    @SequenceGenerator(name = "condicoes_contratuais_seq", sequenceName = "condicoes_contratuais_seq", allocationSize = 1)
    @GeneratedValue(generator = "condicoes_contratuais_seq", strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contrato")
    private Contrato contrato;

    @Column(name = "numero_aditivo", nullable = true)
    private int numeroAditivo;

    @Column(name = "data_assinatura", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAssinatura;

    @Column(name = "data_encerramento", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEncerramento;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "condicoesContratuais", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LocalPlanejado> possiveisLocais;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "condicoesContratuais", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CondicoesContratuaisEquipamento> modelosEquipamentosContratados;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "condicoesContratuais", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CondicoesContratuaisDispositivo> modelosDispositivosContratados;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "condicoesContratuais", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InsumoContratado> insumosCobertos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_preset")
    private Preset preset;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "condicoes_contratuais_presets", joinColumns = {
        @JoinColumn(name = "id_condicao_contratual")}, inverseJoinColumns = {
        @JoinColumn(name = "id_preset")})
    private Set<Preset> presetsAssociados;

    public CondicoesContratuais() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    @Transient
    public String getDataAssinaturaFormatada() {
        if (getDataAssinatura() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getDataAssinatura());
        }
        return null;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEnceramento) {
        this.dataEncerramento = dataEnceramento;
    }

    @Transient
    public String getDataEncerramentoFormatada() {
        if (getDataEncerramento() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getDataEncerramento());
        }
        return null;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Set<LocalPlanejado> getPossiveisLocais() {
        return possiveisLocais;
    }

    public void setPossiveisLocais(Set<LocalPlanejado> possiveisLocais) {
        this.possiveisLocais = possiveisLocais;
    }

    public Set<CondicoesContratuaisEquipamento> getModelosEquipamentosContratados() {
        return modelosEquipamentosContratados;
    }

    public void setModelosEquipamentosContratados(Set<CondicoesContratuaisEquipamento> modelosEquipamentosContratados) {
        this.modelosEquipamentosContratados = modelosEquipamentosContratados;
    }

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public Set<CondicoesContratuaisDispositivo> getModelosDispositivosContratados() {
        return modelosDispositivosContratados;
    }

    public void setModelosDispositivosContratados(Set<CondicoesContratuaisDispositivo> modelosDispositivosContratados) {
        this.modelosDispositivosContratados = modelosDispositivosContratados;
    }

    public Set<InsumoContratado> getInsumosCobertos() {
        return insumosCobertos;
    }

    public void setInsumosCobertos(Set<InsumoContratado> insumosCobertos) {
        this.insumosCobertos = insumosCobertos;
    }

    public Set<Preset> getPresetsAssociados() {
        return presetsAssociados;
    }

    public void setPresetsAssociados(Set<Preset> presetsAssociados) {
        this.presetsAssociados = presetsAssociados;
    }

    public int getNumeroAditivo() {
        return numeroAditivo;
    }

    public void setNumeroAditivo(int numeroAditivo) {
        this.numeroAditivo = numeroAditivo;
    }

}
