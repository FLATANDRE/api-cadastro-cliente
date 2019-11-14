package br.com.surittec.api_rest.dominio.autenticacao;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "api_key")
public class ApiKey implements Serializable {

    @Id
    @SequenceGenerator(name = "api_key_seq", sequenceName = "api_key_seq", allocationSize = 1)
    @GeneratedValue(generator = "api_key_seq", strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "data_criacao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "data_vencimento")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataVencimento;

    @Column(name = "key", columnDefinition = "text")
    private String key;

    @Column(name = "nome")
    private String nome;

    @Column
    private boolean habilitado;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "api_key_perfil", joinColumns = {
        @JoinColumn(name = "id_api_key")}, inverseJoinColumns = {
        @JoinColumn(name = "id_perfil")})
    private Set<Perfil> perfis;

    public ApiKey() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @ApiModelProperty(notes = "Data de criação formatada", dataType = "Data", example = "01/05/2015", required = true)
    @Transient
    public String getDataCriacaoFormatada() {
        if (getDataCriacao() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getDataCriacao());
        }
        return null;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @ApiModelProperty(notes = "Data de vencimento formatada", dataType = "Data", example = "01/05/2015", required = true)
    @Transient
    public String getDataVencimentoFormatada() {
        if (getDataVencimento() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getDataVencimento());
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

}
