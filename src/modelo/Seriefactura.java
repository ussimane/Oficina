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
 * @author USSIMANE
 */
@Entity
@Table(name = "seriefactura", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seriefactura.findAll", query = "SELECT s FROM Seriefactura s"),
    @NamedQuery(name = "Seriefactura.findBySeriefactura", query = "SELECT s FROM Seriefactura s WHERE s.seriefactura = :seriefactura"),
    @NamedQuery(name = "Seriefactura.findByIdf", query = "SELECT s FROM Seriefactura s WHERE s.idf = :idf")})
public class Seriefactura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "seriefactura", nullable = false, length = 50)
    private String seriefactura;
    @Id
    @Basic(optional = false)
    @Column(name = "idf", nullable = false)
    private Integer idf;

    public Seriefactura() {
    }

    public Seriefactura(Integer idf) {
        this.idf = idf;
    }

    public Seriefactura(Integer idf, String seriefactura) {
        this.idf = idf;
        this.seriefactura = seriefactura;
    }

    public String getSeriefactura() {
        return seriefactura;
    }

    public void setSeriefactura(String seriefactura) {
        this.seriefactura = seriefactura;
    }

    public Integer getIdf() {
        return idf;
    }

    public void setIdf(Integer idf) {
        this.idf = idf;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idf != null ? idf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seriefactura)) {
            return false;
        }
        Seriefactura other = (Seriefactura) object;
        if ((this.idf == null && other.idf != null) || (this.idf != null && !this.idf.equals(other.idf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Seriefactura[ idf=" + idf + " ]";
    }
    
}
