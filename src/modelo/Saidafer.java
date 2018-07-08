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
@Table(name = "saidafer", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Saidafer.findAll", query = "SELECT s FROM Saidafer s")})
public class Saidafer implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SaidaferPK saidaferPK;
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

    public Saidafer() {
    }

    public Saidafer(SaidaferPK saidaferPK) {
        this.saidaferPK = saidaferPK;
    }

     public Saidafer(int idferramenta, Date data) {
        this.saidaferPK = new SaidaferPK(idferramenta, data);
    }

    public SaidaferPK getSaidaferPK() {
        return saidaferPK;
    }

    public void setSaidaferPK(SaidaferPK saidaferPK) {
        this.saidaferPK = saidaferPK;
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
        hash += (saidaferPK != null ? saidaferPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saidafer)) {
            return false;
        }
        Saidafer other = (Saidafer) object;
        if ((this.saidaferPK == null && other.saidaferPK != null) || (this.saidaferPK != null && !this.saidaferPK.equals(other.saidaferPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Saidafer[ saidaferPK=" + saidaferPK + " ]";
    }
    
}
