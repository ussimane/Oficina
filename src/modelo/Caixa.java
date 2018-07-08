/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ussimane
 */
@Entity
@Table(name = "caixa", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caixa.findAll", query = "SELECT c FROM Caixa c")})
public class Caixa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcx", nullable = false)
    private Integer idcx;
    @Column(name = "impPOS", length = 2147483647)
    private String impPOS;
    @Column(name = "imppartilhado", length = 2147483647)
    private String imppartilhado;
    @Column(name = "impGrande", length = 2147483647)
    private String impGrande;

    public Caixa() {
    }

    public Caixa(Integer idcx) {
        this.idcx = idcx;
    }

    public Integer getIdcx() {
        return idcx;
    }

    public void setIdcx(Integer idcx) {
        this.idcx = idcx;
    }

    public String getImpPOS() {
        return impPOS;
    }

    public void setImpPOS(String impPOS) {
        this.impPOS = impPOS;
    }

    public String getImppartilhado() {
        return imppartilhado;
    }

    public void setImppartilhado(String imppartilhado) {
        this.imppartilhado = imppartilhado;
    }

    public String getImpGrande() {
        return impGrande;
    }

    public void setImpGrande(String impGrande) {
        this.impGrande = impGrande;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcx != null ? idcx.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caixa)) {
            return false;
        }
        Caixa other = (Caixa) object;
        if ((this.idcx == null && other.idcx != null) || (this.idcx != null && !this.idcx.equals(other.idcx))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Caixa[ idcx=" + idcx + " ]";
    }
    
}
