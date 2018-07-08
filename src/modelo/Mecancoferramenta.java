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
@Table(name = "mecancoferramenta", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mecancoferramenta.findAll", query = "SELECT m FROM Mecancoferramenta m")})
public class Mecancoferramenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MecancoferramentaPK mecancoferramentaPK;
    @Column(name = "qtd")
    private Integer qtd;
    @JoinColumn(name = "idferramenta", referencedColumnName = "idferramenta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ferramenta idferramenta;
    @JoinColumn(name = "idmecanico", referencedColumnName = "idmecanico", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Mecanico mecanico;

    public Mecancoferramenta() {
    }

    public Mecancoferramenta(MecancoferramentaPK mecancoferramentaPK) {
        this.mecancoferramentaPK = mecancoferramentaPK;
    }

    public Mecancoferramenta(int idmecanico, Date data) {
        this.mecancoferramentaPK = new MecancoferramentaPK(idmecanico, data);
    }

    public MecancoferramentaPK getMecancoferramentaPK() {
        return mecancoferramentaPK;
    }

    public void setMecancoferramentaPK(MecancoferramentaPK mecancoferramentaPK) {
        this.mecancoferramentaPK = mecancoferramentaPK;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Ferramenta getIdferramenta() {
        return idferramenta;
    }

    public void setIdferramenta(Ferramenta idferramenta) {
        this.idferramenta = idferramenta;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mecancoferramentaPK != null ? mecancoferramentaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mecancoferramenta)) {
            return false;
        }
        Mecancoferramenta other = (Mecancoferramenta) object;
        if ((this.mecancoferramentaPK == null && other.mecancoferramentaPK != null) || (this.mecancoferramentaPK != null && !this.mecancoferramentaPK.equals(other.mecancoferramentaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Mecancoferramenta[ mecancoferramentaPK=" + mecancoferramentaPK + " ]";
    }
    
}
