/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author USSIMANE
 */
@Entity
@Table(name = "vendedor", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v"),
    @NamedQuery(name = "Vendedor.findByNomecompleto", query = "SELECT v FROM Vendedor v WHERE v.nomecompleto = :nomecompleto"),
    @NamedQuery(name = "Vendedor.findBySenha", query = "SELECT v FROM Vendedor v WHERE v.senha = :senha"),
    @NamedQuery(name = "Vendedor.findByUsername", query = "SELECT v FROM Vendedor v WHERE v.username = :username"),
    @NamedQuery(name = "Vendedor.findByEmail", query = "SELECT v FROM Vendedor v WHERE v.email = :email"),
    @NamedQuery(name = "Vendedor.findByTipoacesso", query = "SELECT v FROM Vendedor v WHERE v.tipoacesso = :tipoacesso"),
    @NamedQuery(name = "Vendedor.findByTelemovel", query = "SELECT v FROM Vendedor v WHERE v.telemovel = :telemovel"),
    @NamedQuery(name = "Vendedor.findByEndereco", query = "SELECT v FROM Vendedor v WHERE v.endereco = :endereco"),
    @NamedQuery(name = "Vendedor.findByCodigo", query = "SELECT v FROM Vendedor v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "Vendedor.findByEstado", query = "SELECT v FROM Vendedor v WHERE v.estado = :estado"),
    @NamedQuery(name = "Vendedor.findByMasculino", query = "SELECT v FROM Vendedor v WHERE v.masculino = :masculino"),
    @NamedQuery(name = "Vendedor.findByIdvendedor", query = "SELECT v FROM Vendedor v WHERE v.idvendedor = :idvendedor")})
public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nomecompleto", nullable = false, length = 255)
    private String nomecompleto;
    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;
    @Column(name = "username", length = 255)
    private String username;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "tipoacesso")
    private Integer tipoacesso;
    @Column(name = "telemovel", length = 75)
    private String telemovel;
    @Column(name = "endereco", length = 255)
    private String endereco;
    @Column(name = "codigo", length = 255)
    private String codigo;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "masculino")
    private Integer masculino;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvendedor", nullable = false)
    private Integer idvendedor;
    @OneToMany(mappedBy = "idvendedor", fetch = FetchType.LAZY)
    private List<Emprestimo> emprestimoList;
    @OneToMany(mappedBy = "idvendedor", fetch = FetchType.LAZY)
    private List<Venda> vendaList;
    @OneToMany(mappedBy = "idutilizador", fetch = FetchType.LAZY)
    private List<Vendaanulada> vendaanuladaList;
    @OneToMany(mappedBy = "utilizador", fetch = FetchType.LAZY)
    private List<Entrada> entradaList;
    @OneToMany(mappedBy = "utilizador", fetch = FetchType.LAZY)
    private List<Manutencao> manutencaoList;
    @OneToMany(mappedBy = "utilizador", fetch = FetchType.LAZY)
    private List<Entradafer> entradaferList;
    @OneToMany(mappedBy = "utilizador", fetch = FetchType.LAZY)
    private List<Saidafer> saidaferList;
    @OneToMany(mappedBy = "utilizador", fetch = FetchType.LAZY)
    private List<Saida> saidaList;

    public Vendedor() {
    }

    public Vendedor(Integer idvendedor) {
        this.idvendedor = idvendedor;
    }

    public Vendedor(Integer idvendedor, String nomecompleto, String senha) {
        this.idvendedor = idvendedor;
        this.nomecompleto = nomecompleto;
        this.senha = senha;
    }

    public String getNomecompleto() {
        return nomecompleto;
    }

    public void setNomecompleto(String nomecompleto) {
        this.nomecompleto = nomecompleto;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTipoacesso() {
        return tipoacesso;
    }

    public void setTipoacesso(Integer tipoacesso) {
        this.tipoacesso = tipoacesso;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @XmlTransient
    public List<Entrada> getEntradaList() {
        return entradaList;
    }

    public void setEntradaList(List<Entrada> entradaList) {
        this.entradaList = entradaList;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
     @XmlTransient
    public List<Manutencao> getManutencaoList() {
        return manutencaoList;
    }

    public void setManutencaoList(List<Manutencao> manutencaoList) {
        this.manutencaoList = manutencaoList;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Integer getMasculino() {
        return masculino;
    }

    public void setMasculino(Integer masculino) {
        this.masculino = masculino;
    }

    public Integer getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Integer idvendedor) {
        this.idvendedor = idvendedor;
    }

    @XmlTransient
    public List<Emprestimo> getEmprestimoList() {
        return emprestimoList;
    }

    public void setEmprestimoList(List<Emprestimo> emprestimoList) {
        this.emprestimoList = emprestimoList;
    }

    @XmlTransient
    public List<Vendaanulada> getVendaanuladaList() {
        return vendaanuladaList;
    }

    public void setVendaanuladaList(List<Vendaanulada> vendaanuladaList) {
        this.vendaanuladaList = vendaanuladaList;
    }
    
     @XmlTransient
    public List<Saida> getSaidaList() {
        return saidaList;
    }

    public void setSaidaList(List<Saida> saidaList) {
        this.saidaList = saidaList;
    }

    @XmlTransient
    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }
    
     @XmlTransient
    public List<Entradafer> getEntradaferList() {
        return entradaferList;
    }

    public void setEntradaferList(List<Entradafer> entradaferList) {
        this.entradaferList = entradaferList;
    }
    
     @XmlTransient
    public List<Saidafer> getSaidaferList() {
        return saidaferList;
    }

    public void setSaidaferList(List<Saidafer> saidaferList) {
        this.saidaferList = saidaferList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvendedor != null ? idvendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.idvendedor == null && other.idvendedor != null) || (this.idvendedor != null && !this.idvendedor.equals(other.idvendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nomecompleto;
    }

}
