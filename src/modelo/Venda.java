/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USSIMANE
 */
@Entity
@Table(name = "venda", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v"),
    @NamedQuery(name = "Venda.findByDatavenda", query = "SELECT v FROM Venda v WHERE v.datavenda = :datavenda"),
    @NamedQuery(name = "Venda.findByQtd", query = "SELECT v FROM Venda v WHERE v.qtd = :qtd"),
    @NamedQuery(name = "Venda.findByValor", query = "SELECT v FROM Venda v WHERE v.valor = :valor"),
    @NamedQuery(name = "Venda.findBySeriefactura", query = "SELECT v FROM Venda v WHERE v.seriefactura = :seriefactura"),
    @NamedQuery(name = "Venda.findByDesconto", query = "SELECT v FROM Venda v WHERE v.desconto = :desconto"),
    @NamedQuery(name = "Venda.findByIdvenda", query = "SELECT v FROM Venda v WHERE v.idvenda = :idvenda")})
public class Venda implements Serializable {
    @Column(name = "qc")
    private Integer qc;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "iva")
    private Integer iva;
    private static final long serialVersionUID = 1L;
    @Column(name = "datavenda")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datavenda;
    @Column(name = "datapag")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datapag;
    @Basic(optional = false)
    @Column(name = "qtd", nullable = false)
    private int qtd;
    @Column(name = "caixa")
    private Integer caixa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 8, scale = 8)
    private Float valor;
    @Column(name = "tdesc", precision = 8, scale = 8)
    private Float tdesc;
    @Column(name = "tiva", precision = 8, scale = 8)
    private Float tiva;
    @Column(name = "prec", precision = 8, scale = 8)
    private Float prec;
    @Column(name = "seriefactura", length = 25)
    private String seriefactura;
    @Column(name = "datae")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datae;
    @Column(name = "desconto")
    private Integer desconto;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvenda", nullable = false)
    private Long idvenda;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente idcliente;
    @JoinColumn(name = "idproduto", referencedColumnName = "idproduto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto idproduto;
    @JoinColumn(name = "idvendedor", referencedColumnName = "idvendedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor idvendedor;
    @Column(name = "qstock")
    private Integer qstock;
    @Column(name = "qpac")
    private Integer qpac;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "venda", fetch = FetchType.LAZY)
    private Vendaanulada vendaanulada;

    public Venda() {
    }

    public Venda(Long idvenda) {
        this.idvenda = idvenda;
    }

    public Venda(Long idvenda, int qtd) {
        this.idvenda = idvenda;
        this.qtd = qtd;
    }

    public Date getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(Date datavenda) {
        this.datavenda = datavenda;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }


    public Date getDatae() {
        return datae;
    }

    public void setDatae(Date datae) {
        this.datae = datae;
    }


    public Date getDatapag() {
        return datapag;
    }

    public void setDatapag(Date datapag) {
        this.datapag = datapag;
    }

    
    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Integer getCaixa() {
        return caixa;
    }

    public void setCaixa(Integer caixa) {
        this.caixa = caixa;
    }

    

    public Float getTdesc() {
        return tdesc;
    }

    public void setTdesc(Float tdesc) {
        this.tdesc = tdesc;
    }

    public Float getTiva() {
        return tiva;
    }

    public void setTiva(Float tiva) {
        this.tiva = tiva;
    }
    
    public Float getPrec() {
        return prec;
    }

    public void setPrec(Float prec) {
        this.prec = prec;
    }

    
    
    public String getSeriefactura() {
        return seriefactura;
    }

    public void setSeriefactura(String seriefactura) {
        this.seriefactura = seriefactura;
    }

    public Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(Integer desconto) {
        this.desconto = desconto;
    }

    public Long getIdvenda() {
        return idvenda;
    }

    public void setIdvenda(Long idvenda) {
        this.idvenda = idvenda;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getQstock() {
        return qstock;
    }

    public void setQstock(Integer qstock) {
        this.qstock = qstock;
    }

    public Integer getQpac() {
        return qpac;
    }

    public void setQpac(Integer qpac) {
        this.qpac = qpac;
    }
    
    

    public Produto getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Produto idproduto) {
        this.idproduto = idproduto;
    }

    public Vendedor getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Vendedor idvendedor) {
        this.idvendedor = idvendedor;
    }
    
    public Vendaanulada getVendaanulada() {
        return vendaanulada;
    }

    public void setVendaanulada(Vendaanulada vendaanulada) {
        this.vendaanulada = vendaanulada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvenda != null ? idvenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.idvenda == null && other.idvenda != null) || (this.idvenda != null && !this.idvenda.equals(other.idvenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Venda[ idvenda=" + idvenda + " ]";
    }

    public Integer getQc() {
        return qc;
    }

    public void setQc(Integer qc) {
        this.qc = qc;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }
    
}
