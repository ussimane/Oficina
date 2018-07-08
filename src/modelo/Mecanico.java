/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ussimane
 */
@Entity
@Table(name = "mecanico", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mecanico.findAll", query = "SELECT m FROM Mecanico m")})
public class Mecanico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "nome", length = 2147483647)
    private String nome;
    @Column(name = "endereco", length = 2147483647)
    private String endereco;
    @Column(name = "telefone", length = 2147483647)
    private String telefone;
    @Column(name = "email", length = 2147483647)
    private String email;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "area", length = 2147483647)
    private String area;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmecanico", nullable = false)
    private Integer idmecanico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mecanico", fetch = FetchType.LAZY)
    private List<Mecancoferramenta> mecancoferramentaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mecanico", fetch = FetchType.LAZY)
    private List<Mecanicomanutencao> mecanicomanutencaoList;

    public Mecanico() {
    }

    public Mecanico(Integer idmecanico) {
        this.idmecanico = idmecanico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getIdmecanico() {
        return idmecanico;
    }

    public void setIdmecanico(Integer idmecanico) {
        this.idmecanico = idmecanico;
    }

    @XmlTransient
    public List<Mecanicomanutencao> getMecanicomanutencaoList() {
        return mecanicomanutencaoList;
    }

    public void setMecanicomanutencaoList(List<Mecanicomanutencao> mecanicomanutencaoList) {
        this.mecanicomanutencaoList = mecanicomanutencaoList;
    }
    
    @XmlTransient
    public List<Mecancoferramenta> getMecancoferramentaList() {
        return mecancoferramentaList;
    }

    public void setMecancoferramentaList(List<Mecancoferramenta> mecancoferramentaList) {
        this.mecancoferramentaList = mecancoferramentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmecanico != null ? idmecanico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mecanico)) {
            return false;
        }
        Mecanico other = (Mecanico) object;
        if ((this.idmecanico == null && other.idmecanico != null) || (this.idmecanico != null && !this.idmecanico.equals(other.idmecanico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
