/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento.bomba;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import br.com.aicare.di.api_rest.dominio.equipamento.Equipamento;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "rack_bomba")
public class RackBomba extends Equipamento {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bomba", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BombaRackBomba> bombas;

    public RackBomba() {
    }

    public Set<BombaRackBomba> getBombas() {
        return bombas;
    }

    public void setBombas(Set<BombaRackBomba> bombas) {
        this.bombas = bombas;
    }

    @Transient
    @Override
    public String getTipoEquipamento() {
        return "rack_bomba";
    }

    @Transient
     @Override
    public String getTipoEquipamentoNome() {
        return Translator.toLocale("rack_bomba_infusao");
    }
}
