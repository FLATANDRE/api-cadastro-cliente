/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato;

import br.com.aicare.di.api_rest.dominio.predial.LocalizacaoFisica;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "local_planejado")
public class LocalPlanejado implements Serializable {

    @EmbeddedId
    private LocalPlanejadoPK id = new LocalPlanejadoPK();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_condicoes_contratuais")   
    @MapsId("idCondicoesContratuais")
    private CondicoesContratuais condicoesContratuais;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_localizacao_fisica")   
    @MapsId("idLocalizacaoFisica")
    private LocalizacaoFisica localizacaoFisica;

    @Column(name = "quantidade_planejada")
    private int quantidadePlanejada;

    public LocalPlanejado(CondicoesContratuais condicoesContratuais, LocalizacaoFisica localizacaoFisica) {
        this.condicoesContratuais = condicoesContratuais;
        this.localizacaoFisica = localizacaoFisica;
    }

    public LocalPlanejado() {
    }

    public LocalPlanejadoPK getId() {
        return id;
    }

    public void setId(LocalPlanejadoPK id) {
        this.id = id;
    }

    public CondicoesContratuais getCondicoesContratuais() {
        return condicoesContratuais;
    }

    public void setCondicoesContratuais(CondicoesContratuais condicoesContratuais) {
        this.condicoesContratuais = condicoesContratuais;
    }

    public LocalizacaoFisica getLocalizacaoFisica() {
        return localizacaoFisica;
    }

    public void setLocalizacaoFisica(LocalizacaoFisica localizacaoFisica) {
        this.localizacaoFisica = localizacaoFisica;
    }

    public int getQuantidadePlanejada() {
        return quantidadePlanejada;
    }

    public void setQuantidadePlanejada(int quantidadePlanejada) {
        this.quantidadePlanejada = quantidadePlanejada;
    }

}
