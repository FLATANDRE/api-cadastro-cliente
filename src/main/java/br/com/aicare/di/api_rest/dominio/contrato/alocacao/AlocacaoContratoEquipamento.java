/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.contrato.alocacao;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
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
@Table(name = "alocacao_contrato_equipamento")
public class AlocacaoContratoEquipamento extends AlocacaoContrato {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_equipamento", referencedColumnName = "id")
    private Equipamento equipamento;

    public AlocacaoContratoEquipamento() {
    }

    public AlocacaoContratoEquipamento(Equipamento equipamento, Contrato contrato, Date associacao) {
        super(contrato, associacao);
        this.equipamento = equipamento;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    @Override
    @Transient
    public String getTipoAssociacao() {
        return "equipamento";
    }

}
