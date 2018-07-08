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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ussimane
 */
@Entity
@Table(name = "manutencao", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manutencao.findAll", query = "SELECT m FROM Manutencao m")})
public class Manutencao implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ManutencaoPK manutencaoPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "customanutencao", precision = 8, scale = 8)
    private Float customanutencao;
    @Column(name = "descricao", length = 2147483647)
    private String descricao;
    @JoinColumn(name = "identrada", referencedColumnName = "identrada", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Entradaviatura entradaviatura;
    @JoinColumn(name = "idtipomanutencao", referencedColumnName = "idtipomanutencao", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tipomanutencao idtipomanutencao;
    @JoinColumn(name = "utilizador", referencedColumnName = "idvendedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor utilizador;

    public Manutencao() {
    }

    public Manutencao(ManutencaoPK manutencaoPK) {
        this.manutencaoPK = manutencaoPK;
    }

    public Manutencao(Date data, long identrada) {
        this.manutencaoPK = new ManutencaoPK(data, identrada);
    }

    public ManutencaoPK getManutencaoPK() {
        return manutencaoPK;
    }

    public void setManutencaoPK(ManutencaoPK manutencaoPK) {
        this.manutencaoPK = manutencaoPK;
    }

    public Float getCustomanutencao() {
        return customanutencao;
    }

    public void setCustomanutencao(Float customanutencao) {
        this.customanutencao = customanutencao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Entradaviatura getEntradaviatura() {
        return entradaviatura;
    }

    public void setEntradaviatura(Entradaviatura entradaviatura) {
        this.entradaviatura = entradaviatura;
    }

    public Tipomanutencao getIdtipomanutencao() {
        return idtipomanutencao;
    }

    public void setIdtipomanutencao(Tipomanutencao idtipomanutencao) {
        this.idtipomanutencao = idtipomanutencao;
    }

    public Vendedor getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Vendedor utilizador) {
        this.utilizador = utilizador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manutencaoPK != null ? manutencaoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manutencao)) {
            return false;
        }
        Manutencao other = (Manutencao) object;
        if ((this.manutencaoPK == null && other.manutencaoPK != null) || (this.manutencaoPK != null && !this.manutencaoPK.equals(other.manutencaoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Manutencao[ manutencaoPK=" + manutencaoPK + " ]";
    }
    
}
