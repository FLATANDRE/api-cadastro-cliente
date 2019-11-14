/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo.associacao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.dominio.predial.Compartimento;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "associacao_dispositivo_compartimento")
public class AssociacaoDispositivoCompartimento extends AssociacaoDispositivo {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_compartimento", referencedColumnName = "id")
    private Compartimento compartimento;

    public AssociacaoDispositivoCompartimento() {
    }

    public AssociacaoDispositivoCompartimento(Compartimento compartimento, Dispositivo dispositivo, Date associacao) {
        super(dispositivo, associacao);
        this.compartimento = compartimento;
    }

    public Compartimento getCompartimento() {
        return compartimento;
    }

    public void setCompartimento(Compartimento compartimento) {
        this.compartimento = compartimento;
    }

    @Override
    @Transient
    public String getTipoAssociacao() {
        return "compartimento";
    }

    @Override
    @Transient
    public String getTipoAssociacaoNome() {
        return Translator.toLocale("compartimento");
    }

    @Override
    public String getDescricaoAssociacao() {
        return getCompartimento().getNome() + " (" + getCompartimento().getTipoCompartimento().getNome() + ")";
    }

}
