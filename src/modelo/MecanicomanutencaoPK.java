/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ussimane
 */
@Embeddable
public class MecanicomanutencaoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idmecanico", nullable = false)
    private int idmecanico;
    @Basic(optional = false)
    @Column(name = "identrada", nullable = false)
    private long identrada;

    public MecanicomanutencaoPK() {
    }

    public MecanicomanutencaoPK(int idmecanico, long identrada) {
        this.idmecanico = idmecanico;
        this.identrada = identrada;
    }

    public int getIdmecanico() {
        return idmecanico;
    }

    public void setIdmecanico(int idmecanico) {
        this.idmecanico = idmecanico;
    }

    public long getIdentrada() {
        return identrada;
    }

    public void setIdentrada(long identrada) {
        this.identrada = identrada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmecanico;
        hash += (int) identrada;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MecanicomanutencaoPK)) {
            return false;
        }
        MecanicomanutencaoPK other = (MecanicomanutencaoPK) object;
        if (this.idmecanico != other.idmecanico) {
            return false;
        }
        if (this.identrada != other.identrada) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MecanicomanutencaoPK[ idmecanico=" + idmecanico + ", identrada=" + identrada + " ]";
    }
    
}
