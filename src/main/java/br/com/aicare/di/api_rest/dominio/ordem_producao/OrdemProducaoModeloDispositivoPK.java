/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.ordem_producao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Paulo Collares
 */
@Embeddable
public class OrdemProducaoModeloDispositivoPK implements Serializable {

    @Column(name = "id_ordem_producao")
    private int idOrdemProducao;

    @Column(name = "id_modelo_dispositivo")
    private int idModeloDispositivo;

    public OrdemProducaoModeloDispositivoPK() {
    }

    public OrdemProducaoModeloDispositivoPK(int idOrdemProducao, int idModeloDispositivo) {
        this.idOrdemProducao = idOrdemProducao;
        this.idModeloDispositivo = idModeloDispositivo;
    }

    public int getIdOrdemProducao() {
        return idOrdemProducao;
    }

    public void setIdOrdemProducao(int idOrdemProducao) {
        this.idOrdemProducao = idOrdemProducao;
    }

    public int getIdModeloDispositivo() {
        return idModeloDispositivo;
    }

    public void setIdModeloDispositivo(int idModeloDispositivo) {
        this.idModeloDispositivo = idModeloDispositivo;
    }

}
