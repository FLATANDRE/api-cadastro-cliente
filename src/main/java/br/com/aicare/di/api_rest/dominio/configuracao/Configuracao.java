/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.configuracao;

import br.com.aicare.di.api_rest.AICareApplication;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Paulo Collares
 */
@Entity
@Table(name = "configuracao")
public class Configuracao implements Serializable {

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "sobre", columnDefinition = "text")
    private String sobre;

    @Column(name = "registro")
    private String registro;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "pj")
    private PessoaJuridica pj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Transient
    public String getGoogleMapsApiKey() {
        return googleMapsApiKey;
    }

    @Transient
    public String getVersao() {
        return AICareApplication.VERSAO;
    }

    public PessoaJuridica getPj() {
        return pj;
    }

    public void setPj(PessoaJuridica pj) {
        this.pj = pj;
    }

}
