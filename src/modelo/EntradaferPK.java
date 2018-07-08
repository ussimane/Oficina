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
public class EntradaferPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idferramenta", nullable = false)
    private int idferramenta;
    @Basic(optional = false)
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    public EntradaferPK() {
    }

    public EntradaferPK(int idferramenta, Date data) {
        this.idferramenta = idferramenta;
        this.data = data;
    }

    public int getIdferramenta() {
        return idferramenta;
    }

    public void setIdferramenta(int idferramenta) {
        this.idferramenta = idferramenta;
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
        hash += (int) idferramenta;
        hash += (data != null ? data.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntradaferPK)) {
            return false;
        }
        EntradaferPK other = (EntradaferPK) object;
        if (this.idferramenta != other.idferramenta) {
            return false;
        }
        if ((this.data == null && other.data != null) || (this.data != null && !this.data.equals(other.data))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EntradaferPK[ idferramenta=" + idferramenta + ", data=" + data + " ]";
    }
    
}
