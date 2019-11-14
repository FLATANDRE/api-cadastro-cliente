/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.presets;

import br.com.aicare.di.api_rest.dominio.contrato.CondicoesContratuais;
import br.com.aicare.di.api_rest.dominio.dispositivo.TipoDispositivo;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author fernanda
 */
@Entity
@Table(name = "preset")
public class Preset implements Serializable {

    @Id
    @SequenceGenerator(name = "preset_seq", sequenceName = "preset_seq", allocationSize = 1)
    @GeneratedValue(generator = "preset_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome", nullable = true)
    private String nome;

    @Column(name = "quantidade", nullable = true)
    private int quantidade;

    @OneToMany(mappedBy = "preset", targetEntity = CondicoesContratuais.class, cascade = CascadeType.ALL)
    private Set<CondicoesContratuais> condicoesContratuais;

    @ManyToOne
    @JoinColumn(name = "id_tipo_dispositivo")
    private TipoDispositivo tipoDispositivo;

    public Preset() {
    }

    public Preset(int id, String nome, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
    }

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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Set<CondicoesContratuais> getCondicoesContratuais() {
        return condicoesContratuais;
    }

    public void setCondicoesContratuais(Set<CondicoesContratuais> condicoesContratuais) {
        this.condicoesContratuais = condicoesContratuais;
    }

    public TipoDispositivo getTipoDispositivo() {
        return tipoDispositivo;
    }

    public void setTipoDispositivo(TipoDispositivo tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }

}
