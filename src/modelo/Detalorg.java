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
@Table(name = "detalorg", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detalorg.findAll", query = "SELECT d FROM Detalorg d"),
    @NamedQuery(name = "Detalorg.findByOrg", query = "SELECT d FROM Detalorg d WHERE d.org = :org"),
    @NamedQuery(name = "Detalorg.findByIddetal", query = "SELECT d FROM Detalorg d WHERE d.iddetal = :iddetal"),
    @NamedQuery(name = "Detalorg.findByCid", query = "SELECT d FROM Detalorg d WHERE d.cid = :cid"),
    @NamedQuery(name = "Detalorg.findByTel", query = "SELECT d FROM Detalorg d WHERE d.tel = :tel"),
    @NamedQuery(name = "Detalorg.findByIa", query = "SELECT d FROM Detalorg d WHERE d.ia = :ia"),
    @NamedQuery(name = "Detalorg.findByIr", query = "SELECT d FROM Detalorg d WHERE d.ir = :ir"),
    @NamedQuery(name = "Detalorg.findByNif", query = "SELECT d FROM Detalorg d WHERE d.nif = :nif"),
    @NamedQuery(name = "Detalorg.findByEnde", query = "SELECT d FROM Detalorg d WHERE d.ende = :ende"),
    @NamedQuery(name = "Detalorg.findByDir", query = "SELECT d FROM Detalorg d WHERE d.dir = :dir")})
public class Detalorg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "org", length = 60)
    private String org;
    @Id
    @Basic(optional = false)
    @Column(name = "iddetal", nullable = false)
    private Integer iddetal;
    @Column(name = "cid", length = 60)
    private String cid;
    @Column(name = "tel", length = 60)
    private String tel;
    @Column(name = "ia", length = 40)
    private String ia;
    @Column(name = "ir", length = 40)
    private String ir;
    @Column(name = "nif", length = 40)
    private String nif;
    @Column(name = "ende", length = 60)
    private String ende;
    @Column(name = "dir", length = 50)
    private String dir;

    public Detalorg() {
    }

    public Detalorg(Integer iddetal) {
        this.iddetal = iddetal;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Integer getIddetal() {
        return iddetal;
    }

    public void setIddetal(Integer iddetal) {
        this.iddetal = iddetal;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIa() {
        return ia;
    }

    public void setIa(String ia) {
        this.ia = ia;
    }

    public String getIr() {
        return ir;
    }

    public void setIr(String ir) {
        this.ir = ir;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEnde() {
        return ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

   

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetal != null ? iddetal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalorg)) {
            return false;
        }
        Detalorg other = (Detalorg) object;
        if ((this.iddetal == null && other.iddetal != null) || (this.iddetal != null && !this.iddetal.equals(other.iddetal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Detalorg[ iddetal=" + iddetal + " ]";
    }
    
}
