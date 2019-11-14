/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.dispositivo;

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
@Table(name = "modelo_dispositivo")
public class ModeloDispositivo {

    @Id
    @SequenceGenerator(name = "modelo_dispositivo_seq", sequenceName = "modelo_dispositivo_seq", allocationSize = 1)
    @GeneratedValue(generator = "modelo_dispositivo_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "desconhecido", columnDefinition = "boolean default false")
    private boolean desconhecido;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tensao_min_bat", columnDefinition = "float default 0")
    private float tensaoMinimaBateria;

    @Column(name = "tensao_max_bat", columnDefinition = "float default 0")
    private float tensaoMaximaBateria;

    @Column(name = "intervalo_manutencao_obrigatoria", columnDefinition = "int default 0")
    private int intervaloManutencaoObrigatoria;

    @ManyToOne
    @JoinColumn(name = "fabricante")
    private FabricanteDispositivo fabricante;

    @OneToMany(mappedBy = "modelo", targetEntity = Dispositivo.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Dispositivo> dispositivos; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDesconhecido() {
        return desconhecido;
    }

    public void setDesconhecido(boolean desconhecido) {
        this.desconhecido = desconhecido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getTensaoMinimaBateria() {
        return tensaoMinimaBateria;
    }

    public void setTensaoMinimaBateria(float tensaoMinimaBateria) {
        this.tensaoMinimaBateria = tensaoMinimaBateria;
    }

    public float getTensaoMaximaBateria() {
        return tensaoMaximaBateria;
    }

    public void setTensaoMaximaBateria(float tensaoMaximaBateria) {
        this.tensaoMaximaBateria = tensaoMaximaBateria;
    }

    public FabricanteDispositivo getFabricante() {
        return fabricante;
    }

    public void setFabricante(FabricanteDispositivo fabricante) {
        this.fabricante = fabricante;
    }

    public int getIntervaloManutencaoObrigatoria() {
        return intervaloManutencaoObrigatoria;
    }

    public void setIntervaloManutencaoObrigatoria(int intervaloManutencaoObrigatoria) {
        this.intervaloManutencaoObrigatoria = intervaloManutencaoObrigatoria;
    }

    public Set<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(Set<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

}
