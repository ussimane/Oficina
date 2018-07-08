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
@Table(name = "tipomanutencao", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipomanutencao.findAll", query = "SELECT t FROM Tipomanutencao t")})
public class Tipomanutencao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "designacao", length = 45)
    private String designacao;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipomanutencao", nullable = false)
    private Integer idtipomanutencao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipomanutencao", fetch = FetchType.LAZY)
    private List<Manutencao> manutencaoList;
    @Column(name = "preco", precision = 8, scale = 8)
    private Float preco;

    public Tipomanutencao() {
    }

    public Tipomanutencao(Integer idtipomanutencao) {
        this.idtipomanutencao = idtipomanutencao;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Integer getIdtipomanutencao() {
        return idtipomanutencao;
    }

    public void setIdtipomanutencao(Integer idtipomanutencao) {
        this.idtipomanutencao = idtipomanutencao;
    }

    @XmlTransient
    public List<Manutencao> getManutencaoList() {
        return manutencaoList;
    }

    public void setManutencaoList(List<Manutencao> manutencaoList) {
        this.manutencaoList = manutencaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipomanutencao != null ? idtipomanutencao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipomanutencao)) {
            return false;
        }
        Tipomanutencao other = (Tipomanutencao) object;
        if ((this.idtipomanutencao == null && other.idtipomanutencao != null) || (this.idtipomanutencao != null && !this.idtipomanutencao.equals(other.idtipomanutencao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return designacao+" -- Prec:"+preco;
    }
    
}
