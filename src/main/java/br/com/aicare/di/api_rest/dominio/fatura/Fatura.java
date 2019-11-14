/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.dominio.fatura;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import br.com.aicare.di.api_rest.dominio.contrato.Contrato;
import br.com.aicare.di.api_rest.dominio.fatura.enums.Natureza;
import br.com.aicare.di.api_rest.dominio.fatura.itens.insumo.ItemFaturaInsumo;
import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_dispositivo.ItemFaturaModeloDispositivo;
import br.com.aicare.di.api_rest.dominio.fatura.itens.modelo_equipamento.ItemFaturaModeloEquipamento;
import br.com.aicare.di.api_rest.dominio.pessoal.pessoa.PessoaJuridica;


/**
 *
 * @author fernanda
 */
@Entity
@Table(name = "fatura")
public class Fatura implements Serializable {

    @Id
    @SequenceGenerator(name = "fatura_seq", sequenceName = "fatura_seq", allocationSize = 1)
    @GeneratedValue(generator = "fatura_seq", strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "numero_nota_fiscal")
    private String numeroNotaFiscal;

    @Column(name = "data")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "natureza")
    @Enumerated(EnumType.STRING)
    private Natureza natureza;

    @ManyToOne
    @JoinColumn(name = "id_pessoa_juridica")
    private PessoaJuridica pessoaJuridica;

    /**
     * Uma fatura pode estar associada a 0 ou um contrato
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "fatura_contrato", joinColumns = {
        @JoinColumn(name = "id_fatura")}, inverseJoinColumns = {
        @JoinColumn(name = "id_contrato")})
    private Set<Contrato> contratos;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "fatura", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemFaturaModeloDispositivo> itensModeloDispositivo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "fatura", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemFaturaModeloEquipamento> itensModeloEquipamento;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "fatura", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemFaturaInsumo> itensInsumo;

    public Fatura() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public Date getData() {
        return data;
    }

    @Transient
    public String getDataFormatada() {
        if (getData() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(getData());
        }
        return null;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Natureza getNatureza() {
        return natureza;
    }

    public void setNatureza(Natureza natureza) {
        this.natureza = natureza;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public Set<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(Set<Contrato> contratos) {
        this.contratos = contratos;
    }

    public Set<ItemFaturaModeloDispositivo> getItensModeloDispositivo() {
        return itensModeloDispositivo;
    }

    public void setItensModeloDispositivo(Set<ItemFaturaModeloDispositivo> itensModeloDispositivo) {
        this.itensModeloDispositivo = itensModeloDispositivo;
    }

    public Set<ItemFaturaModeloEquipamento> getItensModeloEquipamento() {
        return itensModeloEquipamento;
    }

    public void setItensModeloEquipamento(Set<ItemFaturaModeloEquipamento> itensModeloEquipamento) {
        this.itensModeloEquipamento = itensModeloEquipamento;
    }

    public Set<ItemFaturaInsumo> getItensInsumo() {
        return itensInsumo;
    }

    public void setItensInsumo(Set<ItemFaturaInsumo> itensInsumo) {
        this.itensInsumo = itensInsumo;
    }

}
