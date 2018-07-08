/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ussimane
 */
@Entity
@Table(name = "entradaviatura", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entradaviatura.findAll", query = "SELECT e FROM Entradaviatura e")})
public class Entradaviatura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "dataentrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataentrada;
    @Column(name = "datasaida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datasaida;
    @Column(name = "matricula", length = 2147483647)
    private String matricula;
    @Column(name = "nomecliente", length = 2147483647)
    private String nomecliente;
    @Column(name = "corviatura", length = 2147483647)
    private String corviatura;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identrada", nullable = false)
    private Long identrada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entradaviatura", fetch = FetchType.LAZY)
    private List<Manutencao> manutencaoList;
    @JoinColumn(name = "cliente", referencedColumnName = "idcliente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entradaviatura", fetch = FetchType.LAZY)
    private List<Pecamanutencao> pecamanutencaoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entradaviatura", fetch = FetchType.LAZY)
    private List<Mecanicomanutencao> mecanicomanutencaoList;
    @JoinColumn(name = "modelo", referencedColumnName = "idmodelo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Modelo modelo;

    public Entradaviatura() {
    }

    public Entradaviatura(Long identrada) {
        this.identrada = identrada;
    }

    public Date getDataentrada() {
        return dataentrada;
    }

    public void setDataentrada(Date dataentrada) {
        this.dataentrada = dataentrada;
    }

    public Date getDatasaida() {
        return datasaida;
    }

    public void setDatasaida(Date datasaida) {
        this.datasaida = datasaida;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
      public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getNomecliente() {
        return nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomecliente = nomecliente;
    }

    public String getCorviatura() {
        return corviatura;
    }

    public void setCorviatura(String corviatura) {
        this.corviatura = corviatura;
    }

    public Long getIdentrada() {
        return identrada;
    }

    public void setIdentrada(Long identrada) {
        this.identrada = identrada;
    }

    @XmlTransient
    public List<Manutencao> getManutencaoList() {
        return manutencaoList;
    }

    public void setManutencaoList(List<Manutencao> manutencaoList) {
        this.manutencaoList = manutencaoList;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @XmlTransient
    public List<Pecamanutencao> getPecamanutencaoList() {
        return pecamanutencaoList;
    }

    public void setPecamanutencaoList(List<Pecamanutencao> pecamanutencaoList) {
        this.pecamanutencaoList = pecamanutencaoList;
    }

    @XmlTransient
    public List<Mecanicomanutencao> getMecanicomanutencaoList() {
        return mecanicomanutencaoList;
    }

    public void setMecanicomanutencaoList(List<Mecanicomanutencao> mecanicomanutencaoList) {
        this.mecanicomanutencaoList = mecanicomanutencaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identrada != null ? identrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entradaviatura)) {
            return false;
        }
        Entradaviatura other = (Entradaviatura) object;
        if ((this.identrada == null && other.identrada != null) || (this.identrada != null && !this.identrada.equals(other.identrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Entradaviatura[ identrada=" + identrada + " ]";
    }
    
}
