/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.predial;

import br.com.aicare.di.api_rest.dominio.contrato.LocalPlanejado;
import br.com.aicare.di.api_rest.dominio.localizacao.Endereco;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
 * @author fernanda
 */
@Entity
@Table(name = "localizacao_fisica")
public class LocalizacaoFisica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "localizacao_fisica_seq", sequenceName = "localizacao_fisica_seq", allocationSize = 1)
    @GeneratedValue(generator = "localizacao_fisica_seq", strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "endereco", nullable = false)
    private Endereco endereco;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pessoa_juridica", nullable = false)
    private PessoaJuridica pessoaJuridica;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "condicoesContratuais", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LocalPlanejado> condicoesContratuais;

    public LocalizacaoFisica() {
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    @JsonIgnore
    public Set<LocalPlanejado> getCondicoesContratuais() {
        return condicoesContratuais;
    }

    public void setCondicoesContratuais(Set<LocalPlanejado> condicoesContratuais) {
        this.condicoesContratuais = condicoesContratuais;
    }

}
