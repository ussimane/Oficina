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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ussimane
 */
@Entity
@Table(name = "mecanicomanutencao", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mecanicomanutencao.findAll", query = "SELECT m FROM Mecanicomanutencao m")})
public class Mecanicomanutencao implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MecanicomanutencaoPK mecanicomanutencaoPK;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @JoinColumn(name = "identrada", referencedColumnName = "identrada", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Entradaviatura entradaviatura;
    @JoinColumn(name = "idmecanico", referencedColumnName = "idmecanico", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Mecanico mecanico;

    public Mecanicomanutencao() {
    }

    public Mecanicomanutencao(MecanicomanutencaoPK mecanicomanutencaoPK) {
        this.mecanicomanutencaoPK = mecanicomanutencaoPK;
    }

    public Mecanicomanutencao(int idmecanico, long identrada) {
        this.mecanicomanutencaoPK = new MecanicomanutencaoPK(idmecanico, identrada);
    }

    public MecanicomanutencaoPK getMecanicomanutencaoPK() {
        return mecanicomanutencaoPK;
    }

    public void setMecanicomanutencaoPK(MecanicomanutencaoPK mecanicomanutencaoPK) {
        this.mecanicomanutencaoPK = mecanicomanutencaoPK;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Entradaviatura getEntradaviatura() {
        return entradaviatura;
    }

    public void setEntradaviatura(Entradaviatura entradaviatura) {
        this.entradaviatura = entradaviatura;
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
        hash += (mecanicomanutencaoPK != null ? mecanicomanutencaoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mecanicomanutencao)) {
            return false;
        }
        Mecanicomanutencao other = (Mecanicomanutencao) object;
        if ((this.mecanicomanutencaoPK == null && other.mecanicomanutencaoPK != null) || (this.mecanicomanutencaoPK != null && !this.mecanicomanutencaoPK.equals(other.mecanicomanutencaoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return mecanico.getNome();
    }
    
}
