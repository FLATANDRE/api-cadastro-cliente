/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.pessoal;

import java.util.Set;

/**
 *
 * @author Paulo Collares
 */
public class GrupoPJDTO {

    private String nome;
    private Integer lider;
    private Set<Integer> participantes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getLider() {
        return lider;
    }

    public void setLider(Integer lider) {
        this.lider = lider;
    }

    public Set<Integer> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<Integer> participantes) {
        this.participantes = participantes;
    }

}
