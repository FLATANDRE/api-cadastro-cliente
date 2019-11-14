/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento.bomba;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "bomba")
public class Bomba extends Equipamento {

    @ManyToOne
    @JoinColumn(name = "tipo_bomba")
    private TipoBomba tipoBomba;

    @ApiModelProperty(notes = "Versão do software do equipamento")
    @Column(name = "versao_software", nullable = true)
    private String versaoSoftware;

    @ApiModelProperty(notes = "Versão do hardware do equipamento")
    @Column(name = "versao_hardware", nullable = true)
    private String versaoHardware;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rackBomba", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BombaRackBomba> rackBombas;

    public Bomba() {
    }

    @ApiModelProperty(notes = "Tipo da bomba", required = true)
    @JsonManagedReference
    public TipoBomba getTipoBomba() {
        return tipoBomba;
    }

    public void setTipoBomba(TipoBomba tipoBomba) {
        this.tipoBomba = tipoBomba;
    }

    public Set<BombaRackBomba> getRackBombas() {
        return rackBombas;
    }

    public void setRackBombas(Set<BombaRackBomba> rackBombas) {
        this.rackBombas = rackBombas;
    }

    public String getVersaoSoftware() {
        return versaoSoftware;
    }

    public void setVersaoSoftware(String versaoSoftware) {
        this.versaoSoftware = versaoSoftware;
    }

    public String getVersaoHardware() {
        return versaoHardware;
    }

    public void setVersaoHardware(String versaoHardware) {
        this.versaoHardware = versaoHardware;
    }

    @Transient
    @Override
    public String getTipoEquipamento() {
        return "bomba";
    }

    @Transient
    @Override
    public String getTipoEquipamentoNome() {
        return Translator.toLocale("bomba_infusao");
    }

}
