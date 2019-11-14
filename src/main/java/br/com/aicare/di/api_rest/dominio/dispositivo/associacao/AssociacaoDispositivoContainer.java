/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo.associacao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import br.com.aicare.di.api_rest.dominio.predial.Container;
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
@Table(name = "associacao_dispositivo_container")
public class AssociacaoDispositivoContainer extends AssociacaoDispositivo {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_container", referencedColumnName = "id")
    private Container container;

    public AssociacaoDispositivoContainer() {
    }

    public AssociacaoDispositivoContainer(Container container, Dispositivo dispositivo, Date associacao) {
        super(dispositivo, associacao);
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    @Transient
    public String getTipoAssociacao() {
        return "container";
    }

    @Override
    @Transient
    public String getTipoAssociacaoNome() {
        return Translator.toLocale("container");
    }

    @Override
    public String getDescricaoAssociacao() {
        return getContainer().getNome() + " (" + getContainer().getTipoContainer().getNome() + ")";
    }

}
