/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ussimane
 */
@Embeddable
public class MecancoferramentaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idmecanico", nullable = false)
    private int idmecanico;
    @Basic(optional = false)
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    public MecancoferramentaPK() {
    }

    public MecancoferramentaPK(int idmecanico, Date data) {
        this.idmecanico = idmecanico;
        this.data = data;
    }

    public int getIdmecanico() {
        return idmecanico;
    }

    public void setIdmecanico(int idmecanico) {
        this.idmecanico = idmecanico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmecanico;
        hash += (data != null ? data.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MecancoferramentaPK)) {
            return false;
        }
        MecancoferramentaPK other = (MecancoferramentaPK) object;
        if (this.idmecanico != other.idmecanico) {
            return false;
        }
        if ((this.data == null && other.data != null) || (this.data != null && !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MecancoferramentaPK[ idmecanico=" + idmecanico + ", data=" + data + " ]";
    }
    
}
