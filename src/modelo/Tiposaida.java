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
@Table(name = "tiposaida", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiposaida.findAll", query = "SELECT t FROM Tiposaida t")})
public class Tiposaida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idtiposaida", nullable = false)
    private Integer idtiposaida;
    @Column(name = "descricao", length = 32)
    private String descricao;
    @OneToMany(mappedBy = "tiposaida", fetch = FetchType.LAZY)
    private List<Saida> saidaList;

    public Tiposaida() {
    }

    public Tiposaida(Integer idtiposaida) {
        this.idtiposaida = idtiposaida;
    }

    public Integer getIdtiposaida() {
        return idtiposaida;
    }

    public void setIdtiposaida(Integer idtiposaida) {
        this.idtiposaida = idtiposaida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Saida> getSaidaList() {
        return saidaList;
    }

    public void setSaidaList(List<Saida> saidaList) {
        this.saidaList = saidaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtiposaida != null ? idtiposaida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiposaida)) {
            return false;
        }
        Tiposaida other = (Tiposaida) object;
        if ((this.idtiposaida == null && other.idtiposaida != null) || (this.idtiposaida != null && !this.idtiposaida.equals(other.idtiposaida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
}
