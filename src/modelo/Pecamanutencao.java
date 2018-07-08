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
 * @author Ussimane
 */
@Entity
@Table(name = "pecamanutencao", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pecamanutencao.findAll", query = "SELECT p FROM Pecamanutencao p")})
public class Pecamanutencao implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PecamanutencaoPK pecamanutencaoPK;
    @JoinColumn(name = "identrada", referencedColumnName = "identrada", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Entradaviatura entradaviatura;
    @JoinColumn(name = "idproduto", referencedColumnName = "idproduto", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Produto idproduto;
    @Column(name = "qtd")
    private Integer qtd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco", precision = 8, scale = 8)
    private Float preco;
    @Column(name = "qc")
    private Integer qc;
    @Column(name = "iva")
    private Integer iva;
    @Column(name = "tiva", precision = 8, scale = 8)
    private Float tiva;
    @Column(name = "datae")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datae;
    @Column(name = "qpac")
    private Integer qpac;
    @Column(name = "qstock")
    private Integer qstock;

    public Pecamanutencao() {
    }

    public Pecamanutencao(PecamanutencaoPK pecamanutencaoPK) {
        this.pecamanutencaoPK = pecamanutencaoPK;
    }

    public Pecamanutencao(long identrada, Date data) {
        this.pecamanutencaoPK = new PecamanutencaoPK(identrada, data);
    }

    public PecamanutencaoPK getPecamanutencaoPK() {
        return pecamanutencaoPK;
    }

    public void setPecamanutencaoPK(PecamanutencaoPK pecamanutencaoPK) {
        this.pecamanutencaoPK = pecamanutencaoPK;
    }

    public Entradaviatura getEntradaviatura() {
        return entradaviatura;
    }

    public void setEntradaviatura(Entradaviatura entradaviatura) {
        this.entradaviatura = entradaviatura;
    }

    public Produto getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Produto idproduto) {
        this.idproduto = idproduto;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Integer getQc() {
        return qc;
    }

    public void setQc(Integer qc) {
        this.qc = qc;
    }

    public Date getDatae() {
        return datae;
    }

    public void setDatae(Date datae) {
        this.datae = datae;
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
    
    

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public Float getTiva() {
        return tiva;
    }

    public void setTiva(Float tiva) {
        this.tiva = tiva;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pecamanutencaoPK != null ? pecamanutencaoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pecamanutencao)) {
            return false;
        }
        Pecamanutencao other = (Pecamanutencao) object;
        if ((this.pecamanutencaoPK == null && other.pecamanutencaoPK != null) || (this.pecamanutencaoPK != null && !this.pecamanutencaoPK.equals(other.pecamanutencaoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pecamanutencao[ pecamanutencaoPK=" + pecamanutencaoPK + " ]";
    }
    
}
