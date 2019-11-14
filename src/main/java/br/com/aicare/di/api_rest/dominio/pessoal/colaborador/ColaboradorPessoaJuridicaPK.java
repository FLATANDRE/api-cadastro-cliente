/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.pessoal.colaborador;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author fernanda
 */
@Embeddable
public class ColaboradorPessoaJuridicaPK implements Serializable {

    @Column(name = "id_colaborador")
    private int idColaborador;

    @Column(name = "id_pessoa_juridica")
    private int idPessoaJuridica;

    public ColaboradorPessoaJuridicaPK() {
    }

    public ColaboradorPessoaJuridicaPK(int idColaborador, int idPessoaJuridica) {
        this.idColaborador = idColaborador;
        this.idPessoaJuridica = idPessoaJuridica;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public int getIdPessoaJuridica() {
        return idPessoaJuridica;
    }

    public void setIdPessoaJuridica(int idPessoaJuridica) {
        this.idPessoaJuridica = idPessoaJuridica;
    }

    
}