/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author Ussimane
 */
@Entity
@Table(name = "ferramenta", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ferramenta.findAll", query = "SELECT f FROM Ferramenta f")})
public class Ferramenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "descricao", length = 2147483647)
    private String descricao;
    @Column(name = "qtd")
    private Integer qtd;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idferramenta", nullable = false)
    private Integer idferramenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco", precision = 8, scale = 8)
    private Float preco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ferramenta", fetch = FetchType.LAZY)
    private List<Saidafer> saidaferList;
    @OneToMany(mappedBy = "idferramenta", fetch = FetchType.LAZY)
    private List<Mecancoferramenta> mecancoferramentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ferramenta", fetch = FetchType.LAZY)
    private List<Entradafer> entradaferList;

    public Ferramenta() {
    }

    public Ferramenta(Integer idferramenta) {
        this.idferramenta = idferramenta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getIdferramenta() {
        return idferramenta;
    }

    public void setIdferramenta(Integer idferramenta) {
        this.idferramenta = idferramenta;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    @XmlTransient
    public List<Saidafer> getSaidaferList() {
        return saidaferList;
    }

    public void setSaidaferList(List<Saidafer> saidaferList) {
        this.saidaferList = saidaferList;
    }

    @XmlTransient
    public List<Mecancoferramenta> getMecancoferramentaList() {
        return mecancoferramentaList;
    }

    public void setMecancoferramentaList(List<Mecancoferramenta> mecancoferramentaList) {
        this.mecancoferramentaList = mecancoferramentaList;
    }

    @XmlTransient
    public List<Entradafer> getEntradaferList() {
        return entradaferList;
    }

    public void setEntradaferList(List<Entradafer> entradaferList) {
        this.entradaferList = entradaferList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idferramenta != null ? idferramenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ferramenta)) {
            return false;
        }
        Ferramenta other = (Ferramenta) object;
        if ((this.idferramenta == null && other.idferramenta != null) || (this.idferramenta != null && !this.idferramenta.equals(other.idferramenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
}
