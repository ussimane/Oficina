/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
 * @author USSIMANE
 */
@Entity
@Table(name = "produto", catalog = "oficina", schema = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByNome", query = "SELECT p FROM Produto p WHERE p.nome = :nome"),
    @NamedQuery(name = "Produto.findByQtstock", query = "SELECT p FROM Produto p WHERE p.qtstock = :qtstock"),
    @NamedQuery(name = "Produto.findByPrecovenda", query = "SELECT p FROM Produto p WHERE p.precovenda = :precovenda"),
    @NamedQuery(name = "Produto.findByLucro", query = "SELECT p FROM Produto p WHERE p.lucro = :lucro"),
    @NamedQuery(name = "Produto.findByCodigo", query = "SELECT p FROM Produto p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Produto.findByQtdvenda", query = "SELECT p FROM Produto p WHERE p.qtdvenda = :qtdvenda"),
    @NamedQuery(name = "Produto.findByCusto", query = "SELECT p FROM Produto p WHERE p.custo = :custo"),
    @NamedQuery(name = "Produto.findByMargem", query = "SELECT p FROM Produto p WHERE p.margem = :margem"),
    @NamedQuery(name = "Produto.findByIdproduto", query = "SELECT p FROM Produto p WHERE p.idproduto = :idproduto")})
public class Produto implements Serializable {

    @Column(name = "datacriacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datacriacao;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
    @Basic(optional = false)
    @Column(name = "qtstock", nullable = false)
    private int qtstock;
    @Basic(optional = false)
    @Column(name = "precovenda", nullable = false)
    private float precovenda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lucro", precision = 8, scale = 8)
    private Float lucro;
    @Column(name = "codigo", length = 255)
    private String codigo;
    @Column(name = "qtdvenda")
    private Integer qtdvenda;
    @Column(name = "qitem")
    private Integer qitem;
    @Column(name = "dataexpiracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataexpiracao;
    @Column(name = "custo", precision = 8, scale = 8)
    private Float custo;
    @Column(name = "margem", precision = 8, scale = 8)
    private Float margem;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproduto", nullable = false)
    private Integer idproduto;
    @OneToMany(mappedBy = "idproduto", fetch = FetchType.LAZY)
    private List<Emprestimo> emprestimoList;
    @OneToMany(mappedBy = "idproduto", fetch = FetchType.LAZY)
    private List<Venda> vendaList;
    @JoinColumn(name = "idfamilia", referencedColumnName = "idfamilia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Familia idfamilia;
    @JoinColumn(name = "idimposto", referencedColumnName = "idimposto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Imposto idimposto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto", fetch = FetchType.LAZY)
    private List<Entrada> entradaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto", fetch = FetchType.LAZY)
    private List<Saida> saidaList;
    @Column(name = "versao")
    private Integer versao;
    @JoinColumn(name = "modelo", referencedColumnName = "idmodelo")
    @ManyToOne(fetch = FetchType.LAZY)
    private Modelo modelo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idproduto", fetch = FetchType.LAZY)
    private List<Pecamanutencao> pecamanutencaoList;
    public Produto() {
    }

    public Produto(Integer idproduto) {
        this.idproduto = idproduto;
    }

    public Produto(Integer idproduto, String nome, int qtstock, float precovenda) {
        this.idproduto = idproduto;
        this.nome = nome;
        this.qtstock = qtstock;
        this.precovenda = precovenda;
    }

    public Produto(Integer idproduto, String nome, int qtdv, float precovenda, float marg, float custo, Imposto i, String c, int qc, Date dae, int qstock) {
        this.idproduto = idproduto;
        this.nome = nome;
        this.qtdvenda = qtdv;
        this.precovenda = precovenda;
        this.margem = marg;
        this.custo = custo;
        this.idimposto = i;
        this.codigo = c;
        this.qitem = qc;
        this.dataexpiracao = dae;
        this.qtstock = qstock;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataexpiracao() {
        return dataexpiracao;
    }

    public void setDataexpiracao(Date dataexpiracao) {
        this.dataexpiracao = dataexpiracao;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public int getQtstock() {
        return qtstock;
    }

    public void setQtstock(int qtstock) {
        this.qtstock = qtstock;
    }

    public float getPrecovenda() {
        return precovenda;
    }

    public void setPrecovenda(float precovenda) {
        this.precovenda = precovenda;
    }

    public Float getLucro() {
        return lucro;
    }

    public Integer getQitem() {
        return qitem;
    }

    public void setQitem(Integer qitem) {
        this.qitem = qitem;
    }

    public void setLucro(Float lucro) {
        this.lucro = lucro;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getQtdvenda() {
        return qtdvenda;
    }

    public void setQtdvenda(Integer qtdvenda) {
        this.qtdvenda = qtdvenda;
    }

    public Float getCusto() {
        return custo;
    }
    
     @XmlTransient
    public List<Pecamanutencao> getPecamanutencaoList() {
        return pecamanutencaoList;
    }

    public void setPecamanutencaoList(List<Pecamanutencao> pecamanutencaoList) {
        this.pecamanutencaoList = pecamanutencaoList;
    }

    public void setCusto(Float custo) {
        this.custo = custo;
    }

    public Float getMargem() {
        return margem;
    }

    public void setMargem(Float margem) {
        this.margem = margem;
    }

    public Integer getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Integer idproduto) {
        this.idproduto = idproduto;
    }

    @XmlTransient
    public List<Emprestimo> getEmprestimoList() {
        return emprestimoList;
    }

    public void setEmprestimoList(List<Emprestimo> emprestimoList) {
        this.emprestimoList = emprestimoList;
    }

    @XmlTransient
    public List<Venda> getVendaList() {
        return vendaList;
    }

    public void setVendaList(List<Venda> vendaList) {
        this.vendaList = vendaList;
    }

    public Familia getIdfamilia() {
        return idfamilia;
    }

    public void setIdfamilia(Familia idfamilia) {
        this.idfamilia = idfamilia;
    }

    public Imposto getIdimposto() {
        return idimposto;
    }

    public void setIdimposto(Imposto idimposto) {
        this.idimposto = idimposto;
    }

    @XmlTransient
    public List<Entrada> getEntradaList() {
        return entradaList;
    }

    public void setEntradaList(List<Entrada> entradaList) {
        this.entradaList = entradaList;
    }

    @XmlTransient
    public List<Saida> getSaidaList() {
        return saidaList;
    }

    public void setSaidaList(List<Saida> saidaList) {
        this.saidaList = saidaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproduto != null ? idproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.idproduto == null && other.idproduto != null) || (this.idproduto != null && !this.idproduto.equals(other.idproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (nome.length() > 40) {
            return nome.substring(0, 39) + "- Qtd: " + qtdvenda; //+"- "+precovenda+"MT"
        }
        return nome + "- Qtd: " + qtdvenda;//+precovenda+"MT"
    }

    public Date getDatacriacao() {
        return datacriacao;
    }

    public void setDatacriacao(Date datacriacao) {
        this.datacriacao = datacriacao;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

}
