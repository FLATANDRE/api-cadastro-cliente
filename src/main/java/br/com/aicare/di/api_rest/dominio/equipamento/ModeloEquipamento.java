/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.equipamento;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author pcollares
 */
@Entity
@Table(name = "modelo_equipamento")
public class ModeloEquipamento {

    @Id
    @SequenceGenerator(name = "modelo_equipamento_seq", sequenceName = "modelo_equipamento_seq", allocationSize = 1)
    @GeneratedValue(generator = "modelo_equipamento_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "intervalo_manutencao_obrigatoria", columnDefinition = "int default 0")
    private int intervaloManutencaoObrigatoria;

    @ManyToOne
    @JoinColumn(name = "fabricante")
    private FabricanteEquipamento fabricante;

    @OneToMany(mappedBy = "modelo", targetEntity = Equipamento.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Equipamento> equipamentos; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIntervaloManutencaoObrigatoria() {
        return intervaloManutencaoObrigatoria;
    }

    public void setIntervaloManutencaoObrigatoria(int intervaloManutencaoObrigatoria) {
        this.intervaloManutencaoObrigatoria = intervaloManutencaoObrigatoria;
    }

    public FabricanteEquipamento getFabricante() {
        return fabricante;
    }

    public void setFabricante(FabricanteEquipamento fabricante) {
        this.fabricante = fabricante;
    }

    public Set<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(Set<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

}
