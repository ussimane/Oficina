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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "modelo", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modelo.findAll", query = "SELECT m FROM Modelo m")})
public class Modelo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "tipo", length = 2147483647)
    private String tipo;
    @Column(name = "serie", length = 2147483647)
    private String serie;
    @Column(name = "descricao", length = 2147483647)
    private String descricao;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmodelo", nullable = false)
    private Long idmodelo;
    @JoinColumn(name = "marca", referencedColumnName = "idmarca")
    @ManyToOne(fetch = FetchType.LAZY)
    private Marca marca;
    @OneToMany(mappedBy = "modelo", fetch = FetchType.LAZY)
    private List<Produto> produtoList;
    @OneToMany(mappedBy = "modelo", fetch = FetchType.LAZY)
    private List<Entradaviatura> entradaviaturaList;

    public Modelo() {
    }

    public Modelo(Long idmodelo) {
        this.idmodelo = idmodelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdmodelo() {
        return idmodelo;
    }

    public void setIdmodelo(Long idmodelo) {
        this.idmodelo = idmodelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @XmlTransient
    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }
    
     @XmlTransient
    public List<Entradaviatura> getEntradaviaturaList() {
        return entradaviaturaList;
    }

    public void setEntradaviaturaList(List<Entradaviatura> entradaviaturaList) {
        this.entradaviaturaList = entradaviaturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmodelo != null ? idmodelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modelo)) {
            return false;
        }
        Modelo other = (Modelo) object;
        if ((this.idmodelo == null && other.idmodelo != null) || (this.idmodelo != null && !this.idmodelo.equals(other.idmodelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return marca+" "+descricao+" "+serie+" "+tipo;
    }
    
}
