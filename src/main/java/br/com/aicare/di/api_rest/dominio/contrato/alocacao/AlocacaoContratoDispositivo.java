/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato.alocacao;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
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
@Table(name = "alocacao_contrato_dispositivo")
public class AlocacaoContratoDispositivo extends AlocacaoContrato {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dispositivoo", referencedColumnName = "id")
    private Dispositivo dispositivo;

    public AlocacaoContratoDispositivo() {
    }

    public AlocacaoContratoDispositivo(Dispositivo dispositivo, Contrato contrato, Date associacao) {
        super(contrato, associacao);
        this.dispositivo = dispositivo;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    @Override
    @Transient
    public String getTipoAssociacao() {
        return "dispositivo";
    }

}
