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
@Table(name = "entradafer", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entradafer.findAll", query = "SELECT e FROM Entradafer e")})
public class Entradafer implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntradaferPK entradaferPK;
    @Column(name = "qtd")
    private Integer qtd;
    @Column(name = "fpac")
    private Integer fpac;
    @Column(name = "obs", length = 2147483647)
    private String obs;
    @JoinColumn(name = "idferramenta", referencedColumnName = "idferramenta", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ferramenta ferramenta;
    @JoinColumn(name = "utilizador", referencedColumnName = "idvendedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor utilizador;

    public Entradafer() {
    }

    public Entradafer(EntradaferPK entradaferPK) {
        this.entradaferPK = entradaferPK;
    }

    public Entradafer(int idferramenta, Date data) {
        this.entradaferPK = new EntradaferPK(idferramenta, data);
    }

    public EntradaferPK getEntradaferPK() {
        return entradaferPK;
    }

    public void setEntradaferPK(EntradaferPK entradaferPK) {
        this.entradaferPK = entradaferPK;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getFpac() {
        return fpac;
    }

    public void setFpac(Integer fpac) {
        this.fpac = fpac;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Ferramenta getFerramenta() {
        return ferramenta;
    }

    public void setFerramenta(Ferramenta ferramenta) {
        this.ferramenta = ferramenta;
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
        hash += (entradaferPK != null ? entradaferPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entradafer)) {
            return false;
        }
        Entradafer other = (Entradafer) object;
        if ((this.entradaferPK == null && other.entradaferPK != null) || (this.entradaferPK != null && !this.entradaferPK.equals(other.entradaferPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entradafer[ entradaferPK=" + entradaferPK + " ]";
    }
    
}
