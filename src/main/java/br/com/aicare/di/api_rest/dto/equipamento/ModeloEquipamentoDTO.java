/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.equipamento;

/**
 *
 * @author Paulo Collares
 */
public class ModeloEquipamentoDTO {

    private int id;
    
    private String nome;

    private Integer fabricante;

    private Integer intervaloManutencaoObrigatoria;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFabricante() {
        return fabricante;
    }

    public void setFabricante(Integer fabricante) {
        this.fabricante = fabricante;
    }

    public Integer getIntervaloManutencaoObrigatoria() {
        return intervaloManutencaoObrigatoria;
    }

    public void setIntervaloManutencaoObrigatoria(Integer intervaloManutencaoObrigatoria) {
        this.intervaloManutencaoObrigatoria = intervaloManutencaoObrigatoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
