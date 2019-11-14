/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo.associacao;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.dispositivo.Dispositivo;
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
@Table(name = "associacao_dispositivo_equipamento")
public class AssociacaoDispositivoEquipamento extends AssociacaoDispositivo {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_equipamento", referencedColumnName = "id")
    private Equipamento equipamento;

    public AssociacaoDispositivoEquipamento() {
    }

    public AssociacaoDispositivoEquipamento(Equipamento equipamento, Dispositivo dispositivo, Date associacao) {
        super(dispositivo, associacao);
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

    @Override
    @Transient
    public String getTipoAssociacaoNome() {
        return Translator.toLocale("equipamento");
    }

    @Override
    public String getDescricaoAssociacao() {
        return getEquipamento().getTipoEquipamentoNome() + " (" + getEquipamento().getSerialNumber() + ")";
    }

}
