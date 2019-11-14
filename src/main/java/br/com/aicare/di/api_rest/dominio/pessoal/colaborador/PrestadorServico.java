/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.colaborador;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author FERNANDA
 */
@Entity
@Table(name = "prestador_servico")
public class PrestadorServico extends Colaborador {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contato", referencedColumnName = "id")
    private Vinculado contato;
        
    public PrestadorServico() {
    }

    public Vinculado getContato() {
        return contato;
    }

    public void setContato(Vinculado contato) {
        this.contato = contato;
    }



}
