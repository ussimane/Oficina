/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USSIMANE
 */
@Entity
@Table(name = "entrada", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entrada.findAll", query = "SELECT e FROM Entrada e"),
    @NamedQuery(name = "Entrada.findByIdproduto", query = "SELECT e FROM Entrada e WHERE e.entradaPK.idproduto = :idproduto"),
    @NamedQuery(name = "Entrada.findByQtd", query = "SELECT e FROM Entrada e WHERE e.qtd = :qtd"),
    @NamedQuery(name = "Entrada.findByData", query = "SELECT e FROM Entrada e WHERE e.entradaPK.data = :data"),
    @NamedQuery(name = "Entrada.findByCusto", query = "SELECT e FROM Entrada e WHERE e.custo = :custo")})
public class Entrada implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntradaPK entradaPK;
    @Column(name = "qtd")
    private Integer qtd;
    @Column(name = "qs")
    private Integer qs;
    @Column(name = "qv")
    private Integer qv;
    @Column(name = "qd")
    private Integer qd;
    @Column(name = "datacriacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacriacao;
    @Column(name = "dataexpiracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataexpiracao;
    @Column(name = "preco", precision = 8, scale = 8)
    private Float preco;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "custo", precision = 8, scale = 8)
    private Float custo;
    @JoinColumn(name = "idfornecedor", referencedColumnName = "idfornecedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fornecedor idfornecedor;
    @JoinColumn(name = "idproduto", referencedColumnName = "idproduto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto produto;
    @Column(name = "qpac")
    private Integer qpac;
    @Column(name = "qstock")
    private Integer qstock;
    @Column(name = "na", length = 2147483647)
    private String na;
    @Column(name = "nb", length = 2147483647)
    private String nb;
    @Column(name = "pb", precision = 8, scale = 8)
    private Float pb;
    @Column(name = "nc", length = 2147483647)
    private String nc;
    @Column(name = "pc", precision = 8, scale = 8)
    private Float pc;
    @Column(name = "qa")
    private Integer qa;
    @Column(name = "qb")
    private Integer qb;
    @Column(name = "qc")
    private Integer qc;
    @Column(name = "qitem")
    private Integer qitem;
    @Column(name = "cbarra", length = 2147483647)
    private String cbarra;
    @Column(name = "versao")
    private Integer versao;
    @JoinColumn(name = "idimposto", referencedColumnName = "idimposto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Imposto idimposto;
    @Column(name = "serie", length = 2147483647)
    private String serie;
   @JoinColumn(name = "utilizador", referencedColumnName = "idvendedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor utilizador;
    @Column(name = "dataref")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataref;
    @Column(name = "qntinicial")
    private Integer qntinicial;
    @Column(name = "precoinicial", length = 2147483647)
    private String precoinicial;

    public Entrada() {
    }

    public Entrada(EntradaPK entradaPK) {
        this.entradaPK = entradaPK;
    }

    public Entrada(int idproduto, Date data) {
        this.entradaPK = new EntradaPK(idproduto, data);
    }

    public EntradaPK getEntradaPK() {
        return entradaPK;
    }

    public Integer getQd() {
        return qd;
    }

    public void setQd(Integer qd) {
        this.qd = qd;
    }

    public void setEntradaPK(EntradaPK entradaPK) {
        this.entradaPK = entradaPK;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getPrecoinicial() {
        return precoinicial;
    }

    public void setPrecoinicial(String precoinicial) {
        this.precoinicial = precoinicial;
    }

    public String getNb() {
        return nb;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Vendedor getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Vendedor utilizador) {
        this.utilizador = utilizador;
    }

    

    public Date getDataref() {
        return dataref;
    }

    public void setDataref(Date dataref) {
        this.dataref = dataref;
    }

    public Integer getQntinicial() {
        return qntinicial;
    }

    public void setQntinicial(Integer qntinicial) {
        this.qntinicial = qntinicial;
    }

    public void setNb(String nb) {
        this.nb = nb;
    }

    public Float getPb() {
        return pb;
    }

    public void setPb(Float pb) {
        this.pb = pb;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public Float getPc() {
        return pc;
    }

    public void setPc(Float pc) {
        this.pc = pc;
    }

    public Imposto getIdimposto() {
        return idimposto;
    }

    public void setIdimposto(Imposto idimposto) {
        this.idimposto = idimposto;
    }

    public Integer getQa() {
        return qa;
    }

    public void setQa(Integer qa) {
        this.qa = qa;
    }

    public Integer getQb() {
        return qb;
    }

    public void setQb(Integer qb) {
        this.qb = qb;
    }

    public Integer getQc() {
        return qc;
    }

    public void setQc(Integer qc) {
        this.qc = qc;
    }

    public Integer getQitem() {
        return qitem;
    }

    public void setQitem(Integer qitem) {
        this.qitem = qitem;
    }

    public String getCbarra() {
        return cbarra;
    }

    public void setCbarra(String cbarra) {
        this.cbarra = cbarra;
    }

    public Float getCusto() {
        return custo;
    }

    public void setCusto(Float custo) {
        this.custo = custo;
    }

    public Fornecedor getIdfornecedor() {
        return idfornecedor;
    }

    public void setIdfornecedor(Fornecedor idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    public Float getPreco() {
        return preco;
    }

    public Integer getQv() {
        return qv;
    }

    public void setQv(Integer qv) {
        this.qv = qv;
    }

    public Integer getQpac() {
        return qpac;
    }

    public void setQpac(Integer qpac) {
        this.qpac = qpac;
    }

    public Integer getQstock() {
        return qstock;
    }

    public void setQstock(Integer qstock) {
        this.qstock = qstock;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Date getDatacriacao() {
        return datacriacao;
    }

    public void setDatacriacao(Date datacriacao) {
        this.datacriacao = datacriacao;
    }

    public Date getDataexpiracao() {
        return dataexpiracao;
    }

    public Integer getQs() {
        return qs;
    }

    public void setQs(Integer qs) {
        this.qs = qs;
    }

    public void setDataexpiracao(Date dataexpiracao) {
        this.dataexpiracao = dataexpiracao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entradaPK != null ? entradaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entrada)) {
            return false;
        }
        Entrada other = (Entrada) object;
        if ((this.entradaPK == null && other.entradaPK != null) || (this.entradaPK != null && !this.entradaPK.equals(other.entradaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
      //  return new java.text.SimpleDateFormat("dd/MM/yyyy").format(entradaPK.getData()) + "-- Qtd." + (qv);
         return "Nr."+serie + " -- Qtd." + (qitem);
    }

}
