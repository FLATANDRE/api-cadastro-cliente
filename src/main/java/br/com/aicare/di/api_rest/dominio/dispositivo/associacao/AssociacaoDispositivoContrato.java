
/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo.associacao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "associacao_dispositivo_contrato")
public class AssociacaoDispositivoContrato extends AssociacaoDispositivo {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contrato", referencedColumnName = "id")
    private Contrato contrato;

    public AssociacaoDispositivoContrato() {
    }

    public AssociacaoDispositivoContrato(Contrato contrato, Dispositivo dispositivo, Date associacao) {
        super(dispositivo, associacao);
        this.contrato = contrato;
    }

    public Contrato getContrato() {
        Contrato contratoSimplificado = new Contrato();
        contratoSimplificado.setId(contrato.getId());
        contratoSimplificado.setNumero(contrato.getNumero());
        contratoSimplificado.setTipoContrato(contrato.getTipoContrato());
        contratoSimplificado.setContratado(contrato.getContratado());
        contratoSimplificado.setContratante(contrato.getContratante());
        contratoSimplificado.setContatoInterno(contrato.getContatoInterno());
        contratoSimplificado.setContatoCliente(contrato.getContatoCliente());

        return contratoSimplificado;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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
        return getContrato().getNumero() + " (" + getContrato().getTipoContrato().getNome() + ")";
    }

}
