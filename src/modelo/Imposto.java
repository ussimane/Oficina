/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USSIMANE
 */
@Entity
@Table(name = "imposto", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imposto.findAll", query = "SELECT i FROM Imposto i"),
    @NamedQuery(name = "Imposto.findByDescricao", query = "SELECT i FROM Imposto i WHERE i.descricao = :descricao"),
    @NamedQuery(name = "Imposto.findByPerc", query = "SELECT i FROM Imposto i WHERE i.perc = :perc"),
    @NamedQuery(name = "Imposto.findByIdimposto", query = "SELECT i FROM Imposto i WHERE i.idimposto = :idimposto")})
public class Imposto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "descricao", length = 45)
    private String descricao;
    @Column(name = "perc")
    private Integer perc;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idimposto", nullable = false)
    private Integer idimposto;
    @OneToMany(mappedBy = "idimposto", fetch = FetchType.LAZY)
    private List<Produto> produtoList;

    public Imposto() {
    }

    public Imposto(Integer idimposto) {
        this.idimposto = idimposto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPerc() {
        return perc;
    }

    public void setPerc(Integer perc) {
        this.perc = perc;
    }

    public Integer getIdimposto() {
        return idimposto;
    }

    public void setIdimposto(Integer idimposto) {
        this.idimposto = idimposto;
    }

    @XmlTransient
    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idimposto != null ? idimposto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imposto)) {
            return false;
        }
        Imposto other = (Imposto) object;
        if ((this.idimposto == null && other.idimposto != null) || (this.idimposto != null && !this.idimposto.equals(other.idimposto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
}
