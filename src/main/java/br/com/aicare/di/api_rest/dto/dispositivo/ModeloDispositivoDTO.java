/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dto.dispositivo;

/**
 *
 * @author Paulo Collares
 */
public class ModeloDispositivoDTO {

    private int id;
    
    private String nome;

    private Integer fabricante;

    private Float tensaoMinimaBateria;

    private Float tensaoMaximaBateria;

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

    public Float getTensaoMinimaBateria() {
        return tensaoMinimaBateria;
    }

    public void setTensaoMinimaBateria(Float tensaoMinimaBateria) {
        this.tensaoMinimaBateria = tensaoMinimaBateria;
    }

    public Float getTensaoMaximaBateria() {
        return tensaoMaximaBateria;
    }

    public void setTensaoMaximaBateria(Float tensaoMaximaBateria) {
        this.tensaoMaximaBateria = tensaoMaximaBateria;
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
