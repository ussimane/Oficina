/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static View.VendaEmprestimo.lpro;
import static View.VendaEmprestimo.lproe;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.toedter.calendar.JDateChooser;
import controller.ClienteJpaController;
import controller.EmprestimoJpaController;
import controller.EntradaJpaController;
import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.VendaanuladaJpaController;
import controller.VendedorJpaController;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import modelo.Movimento;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import java.sql.CallableStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import metodos.ButtonColumn;
import metodos.ControllerAcess;
import metodos.MoviementoCtr;
import metodos.ValidarInteiro;
import metodos.ValidarString;
import modelo.Cliente;
import modelo.Emprestimo;
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Produto;
import modelo.Venda;
import modelo.Vendaanulada;
import modelo.Vendedor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author acer
 */
public class ConsultasInternas extends javax.swing.JDialog {

    JasperPrint jpPrint;
    JasperViewer jv;
    HashMap<String, Object> par = new HashMap<String, Object>();
    Map parametros = new HashMap<>();
    EntityManagerFactory emf = new ControllerAcess().getEntityManagerFactory();
    private final DefaultTableModel vmodel = new DefaultTableModel(null, new String[]{"Produto", "Preco", "Itens", "Qtd", "Total", "desc(%)", "Vendedor", "Data de Venda", "Estado"});
    private final DefaultTableModel fmodel = new DefaultTableModel(null, new String[]{"id", "Nro Factura", "Client/Empresa", "Vendedor", "Data de Venda", "T. Desc", "T. IVA", "Total", "Estado", "Ver", "Editar", "Anular"});
    private final DefaultTableModel mvmodel = new DefaultTableModel(null, new String[]{"Data", "Tipo Movimento", "qtd", "preco", "Armazem", "Loja", "Estado", "Lote / Entrada"});
    List<Venda> listavedas = new ArrayList<>();
    List<Movimento> listamov = new ArrayList<>();
    List<Venda> listafatura = new ArrayList<>();
    float vp = 0;
    float de = 0;
    float imp = 0;
    private int client = 0;
    static String motivo = null;

    public ConsultasInternas(int c) throws Exception {
        initComponents();
        Date d = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date n = cal.getTime();
        this.client = c;
        txtsfact.setDocument(new ValidarInteiro());
        comboboxp(Comboproduto);
        comboboxp(Comboproduto1);
        combobox(cbtipo);
        comboboxv(cbvendedor);
        if (cbvendedor.getItemCount() != 0) {
            cbvendedor.setSelectedIndex(0);
        }
        jdInicio.setDate(n);
        jdInicio1.setDate(n);
        jdInicio2.setDate(n);
        listamov = new MoviementoCtr(emf).getMovimento(null, null, null);
        listavedas = new VendaJpaController(emf).getVendaPeriodo(n, null, Comboproduto.getSelectedItem());
        listafatura = new VendaJpaController(emf).getFacturaPeriodo(n, null, cbtipo.getSelectedIndex(), cbvendedor.getSelectedItem());
        mostraVenda();
        mostraMovimento();
        mostraFactura();
        this.modeloProduto.setAutoCreateRowSorter(true);
        this.tabelaFactura.setAutoCreateRowSorter(true);
        this.tabelaFactura.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.tabelaFactura.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tabelaFactura.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.tabelaFactura.getColumnModel().getColumn(9).setMaxWidth(50);//setPreferredWidth(3);
        this.tabelaFactura.getColumnModel().getColumn(10).setMaxWidth(50);//setPreferredWidth(3);
        this.tabelaFactura.getColumnModel().getColumn(11).setMaxWidth(70);//setPreferredWidth(3);
//        this.tabelaFactura.getColumnModel().getColumn(1).setMaxWidth(20);
//        this.tabelaFactura.getColumnModel().getColumn(4).setMaxWidth(70);
//        this.tabelaFactura.getColumnModel().getColumn(5).setMaxWidth(50);//setPreferredWidth(3);
//        this.tabelaFactura.getColumnModel().getColumn(6).setMaxWidth(70);//setPreferredWidth(3);
        this.tabelaFactura.setRowHeight(30);
        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"));
        Icon iv = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_ver.png"));
        ButtonColumn bv = new ButtonColumn(this.tabelaFactura, ver, 9, iv);
        ButtonColumn bce = new ButtonColumn(this.tabelaFactura, editar, 10, ie);
        ButtonColumn bcr = new ButtonColumn(this.tabelaFactura, Anular, 11, ir);
        jdpag.getDateEditor().addPropertyChangeListener(
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        if ("date".equals(e.getPropertyName())) {
                            pesquisaDpag();
//                System.out.println(e.getPropertyName()
//                    + ": " + (Date) e.getNewValue());
                        }
                    }
                });
    }

    public void pesquisaDpag() {
        try {
            listafatura.clear();
            listafatura = new VendaJpaController(emf).getFacturaDPag(jdpag.getDate());
            mostraFactura();
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labelHistory = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        painelVenda = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jBpesquisarProdVenda = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jdFim = new com.toedter.calendar.JDateChooser();
        jdInicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Comboproduto = new javax.swing.JComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        modeloProduto = new javax.swing.JTable();
        lbsalmed = new javax.swing.JLabel();
        lbtotalitem = new javax.swing.JLabel();
        painelVenda1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jBpesquisarProdFactura = new javax.swing.JButton();
        jdFim1 = new com.toedter.calendar.JDateChooser();
        jdInicio1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbtipo = new javax.swing.JComboBox();
        cbvendedor = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtsfact = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jdpag = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelaFactura = new javax.swing.JTable();
        lbsaldo = new javax.swing.JLabel();
        lbsaldo1 = new javax.swing.JLabel();
        lbsaldo2 = new javax.swing.JLabel();
        lbiva = new javax.swing.JLabel();
        lbdesc = new javax.swing.JLabel();
        painelVenda2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jBpesquisarProdVenda1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jdFim2 = new com.toedter.calendar.JDateChooser();
        jdInicio2 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Comboproduto1 = new javax.swing.JComboBox();
        jScrollPane7 = new javax.swing.JScrollPane();
        modeloProduto1 = new javax.swing.JTable();
        lbsalmed1 = new javax.swing.JLabel();
        lbtotalitem1 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(255, 204, 102));

        labelHistory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelHistory.setText("Relatorios");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(583, Short.MAX_VALUE)
                .addComponent(labelHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(494, 494, 494))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labelHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        painelVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBpesquisarProdVenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBpesquisarProdVenda.setText("Pesquisar Vendas");
        jBpesquisarProdVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBpesquisarProdVendaActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 204, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton2.setText("Exportar PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jdFim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdFimKeyReleased(evt);
            }
        });

        jdInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdInicioKeyReleased(evt);
            }
        });

        jLabel1.setText("Apartir de:");

        jLabel2.setText("ate");

        Comboproduto.setToolTipText("Seleccione ou pesquise produto");
        Comboproduto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Comboproduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboprodutoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdFim, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBpesquisarProdVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Comboproduto, 0, 377, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jdInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jdFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBpesquisarProdVenda)
                        .addComponent(jButton2)
                        .addComponent(Comboproduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        modeloProduto.setModel(vmodel);
        modeloProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modeloProdutoMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(modeloProduto);

        lbsalmed.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbtotalitem.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout painelVendaLayout = new javax.swing.GroupLayout(painelVenda);
        painelVenda.setLayout(painelVendaLayout);
        painelVendaLayout.setHorizontalGroup(
            painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addContainerGap())
            .addGroup(painelVendaLayout.createSequentialGroup()
                .addGap(325, 325, 325)
                .addComponent(lbtotalitem, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(273, 273, 273)
                .addComponent(lbsalmed, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(322, Short.MAX_VALUE))
        );
        painelVendaLayout.setVerticalGroup(
            painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbsalmed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbtotalitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Vendas/produto", painelVenda);

        painelVenda1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBpesquisarProdFactura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBpesquisarProdFactura.setText("Pesquisar Facturas");
        jBpesquisarProdFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBpesquisarProdFacturaActionPerformed(evt);
            }
        });

        jdFim1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdFim1KeyReleased(evt);
            }
        });

        jdInicio1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdInicio1KeyReleased(evt);
            }
        });

        jLabel3.setText("Apartir de:");

        jLabel4.setText("a");

        cbtipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbtipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbtipoItemStateChanged(evt);
            }
        });

        cbvendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbvendedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbvendedorItemStateChanged(evt);
            }
        });

        jLabel5.setText("Tipo de Venda");

        jLabel6.setText("Vendedor");

        jLabel7.setText("Nr. Factura");

        txtsfact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsfactKeyReleased(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(102, 204, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton3.setText("PDF");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setText("Data Pagamento");

        jLabel9.setText("Data Venda");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel9))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdFim1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBpesquisarProdFactura)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdpag, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(64, 64, 64)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtsfact, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(45, 45, 45)
                .addComponent(jButton3)
                .addGap(33, 33, 33))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtsfact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jBpesquisarProdFactura)
                                .addComponent(jdpag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jdInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)))
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jdFim1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabelaFactura.setModel(fmodel);
        tabelaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFacturaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tabelaFactura);

        lbsaldo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbsaldo1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbsaldo2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbiva.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbdesc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout painelVenda1Layout = new javax.swing.GroupLayout(painelVenda1);
        painelVenda1.setLayout(painelVenda1Layout);
        painelVenda1Layout.setHorizontalGroup(
            painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVenda1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(lbiva, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(lbsaldo2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(265, 265, 265))
            .addGroup(painelVenda1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelVenda1Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelVenda1Layout.createSequentialGroup()
                    .addGap(531, 531, 531)
                    .addComponent(lbsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(531, Short.MAX_VALUE)))
            .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVenda1Layout.createSequentialGroup()
                    .addContainerGap(541, Short.MAX_VALUE)
                    .addComponent(lbsaldo1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(521, 521, 521)))
        );
        painelVenda1Layout.setVerticalGroup(
            painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVenda1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbsaldo2, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(lbiva, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(lbdesc, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
            .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelVenda1Layout.createSequentialGroup()
                    .addGap(209, 209, 209)
                    .addComponent(lbsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(255, Short.MAX_VALUE)))
            .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVenda1Layout.createSequentialGroup()
                    .addContainerGap(264, Short.MAX_VALUE)
                    .addComponent(lbsaldo1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(200, 200, 200)))
        );

        jTabbedPane1.addTab("Vendas/Cliente", painelVenda1);

        painelVenda2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBpesquisarProdVenda1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBpesquisarProdVenda1.setText("Pesquisar Moviemtnos");
        jBpesquisarProdVenda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBpesquisarProdVenda1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 204, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton4.setText("Exportar PDF");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jdFim2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdFim2KeyReleased(evt);
            }
        });

        jdInicio2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdInicio2KeyReleased(evt);
            }
        });

        jLabel10.setText("Apartir de:");

        jLabel11.setText("ate");

        Comboproduto1.setToolTipText("Seleccione ou pesquise produto");
        Comboproduto1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Comboproduto1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Comboproduto1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdFim2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBpesquisarProdVenda1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Comboproduto1, 0, 377, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jdInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jdFim2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBpesquisarProdVenda1)
                        .addComponent(jButton4)
                        .addComponent(Comboproduto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        modeloProduto1.setModel(mvmodel);
        modeloProduto1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modeloProduto1MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(modeloProduto1);

        lbsalmed1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbtotalitem1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout painelVenda2Layout = new javax.swing.GroupLayout(painelVenda2);
        painelVenda2.setLayout(painelVenda2Layout);
        painelVenda2Layout.setHorizontalGroup(
            painelVenda2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVenda2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelVenda2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addContainerGap())
            .addGroup(painelVenda2Layout.createSequentialGroup()
                .addGap(325, 325, 325)
                .addComponent(lbtotalitem1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(273, 273, 273)
                .addComponent(lbsalmed1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(322, Short.MAX_VALUE))
        );
        painelVenda2Layout.setVerticalGroup(
            painelVenda2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVenda2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelVenda2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbsalmed1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(lbtotalitem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Movimento", painelVenda2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    Action editar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Venda v = (Venda) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            FormFactura f = new FormFactura(new javax.swing.JFrame(), v, "Editar Nr Factura", true);
            f.setVisible(true);
            try {
                listafatura = new VendaJpaController(emf).getFacturaPeriodo(jdInicio1.getDate(), jdFim1.getDate(), cbtipo.getSelectedIndex(), cbvendedor.getSelectedItem());
                mostraFactura();
            } catch (Exception ex) {
                Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    Action ver = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Venda v = (Venda) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            List<Venda> le = new VendaJpaController(emf).getVenda(v.getDatavenda());
            FormFacturacao f = new FormFacturacao(new javax.swing.JFrame(), le, true);
            f.setVisible(true);
        }
    };

//    Action exportfact = new AbstractAction() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JTable table = (JTable) e.getSource();
//            int modelRow = Integer.valueOf(e.getActionCommand());
//            Venda v = (Venda) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
//            List<Venda> lv = new VendaJpaController(emf).getVenda(v.getDatavenda());
//            parametros.clear();
//            parametros.put("nome", v.getIdcliente().getNome());//txtClient.getText());
//          //  parametros.put("nuit", v.getNuitc());
//            String imge = new File("saslogo.jpg").getAbsolutePath();
//            parametros.put("img", imge);
//            parametros.put("fatura", v.getSeriefactura());
//            parametros.put("data", v.getDatavenda());
//            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lv);
//            String path = new File("relatorios/recibov2.jasper").getAbsolutePath();
//            try {
//                jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
//            } catch (JRException ex) {
//                Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
//            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Factura", true);
//            viewer.setSize(1024, 768);
//            viewer.setLocationRelativeTo(null);
//            viewer.getContentPane().add(jv.getContentPane());
//            viewer.setVisible(true);
//        }
//    };
    Action Anular = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Venda v = (Venda) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            if (v.getEstado() == 1 || v.getEstado() == 2) {
                JOptionPane.showMessageDialog(null, "Esta venda foi anulada!", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String[] opc = new String[]{"Anular e repor a Quantidade", "Anular sem repor a quantidade", "Cancelar"};
            int resp = JOptionPane.showOptionDialog(null, "{Pretende anular esta Factura?", "ATENÇÃO!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opc, opc[0]);
            if (resp == 2) {
                return;
            }
            motivo = null;
            FormMotivoAnul f = new FormMotivoAnul(new javax.swing.JFrame(), true);
            f.setVisible(true);
            if (motivo == null) {
                return;
            }
            EntityManager em = emf.createEntityManager();
            if (resp == 0) {
                int respo = JOptionPane.showOptionDialog(null, "Tem a certeza que quer anular a Factura?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"SIM", "NÃO"}, null);
                if (respo == 1 || respo == -1) {
                    return;
                }
                List<Venda> le = new VendaJpaController(emf).getVenda(v.getDatavenda());
                em.getTransaction().begin();/////////////////////////////
                try {
                    Venda venda = null;
                    for (Venda ep : le) {
                        //Entrada ent = new EntradaJpaController(emf).getEntrada(ep.getDatae());
                        Entrada ent = new EntradaJpaController(emf).findEntradaS(new EntradaPK(ep.getIdproduto().getIdproduto(), ep.getDatae()),em);
                        Produto p = new ProdutoJpaController(emf).findProdutoS(ep.getIdproduto().getIdproduto(),em);
                        ent.setQv(ent.getQv() + ep.getQc());
                        p.setQtdvenda(p.getQtdvenda() + ep.getQc());
                        ep.setEstado(1);
                        ep.setValor(new Float(0));
                        ep.setTiva(new Float(0));
                        ep.setTdesc(new Float(0));
                        new EntradaJpaController(emf).edit(ent, ent, em);
                        new ProdutoJpaController(emf).edit(p, p, em);
                        new VendaJpaController(emf).edit(ep, em);

                        venda = ep;
                    }
                    Vendaanulada anulada = new Vendaanulada(venda.getIdvenda());
                    anulada.setData(new Date());
                    anulada.setIdutilizador(new Vendedor(client));
                    anulada.setVenda(venda);
                    anulada.setMotivo(motivo);
                    new VendaanuladaJpaController(emf).create(anulada, em);
                    em.getTransaction().commit();
                    vp = vp - v.getValor();
                    de = de - v.getTdesc();
                    imp = imp - v.getTiva();
                    v.setEstado(1);
                    v.setValor(new Float(0));
                    ((DefaultTableModel) table.getModel()).setValueAt(0.0, modelRow, 7);
                    ((DefaultTableModel) table.getModel()).setValueAt("Restaurada", modelRow, 8);
                    lbsaldo2.setText("Valor Total: " + vp);
                    JOptionPane.showMessageDialog(null, "Anulado com sucesso");
                } catch (NonexistentEntityException ex) {
                    //  em.getTransaction().rollback();
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    //   em.getTransaction().rollback();
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (em != null) {
                        em.close();
                    }
                }
            } else if (resp == 1) {
                int respo = JOptionPane.showOptionDialog(null, "Tem a certeza que quer anular a Factura?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"SIM", "NÃO"}, null);
                if (respo == 1 || respo == -1) {
                    return;
                }
                List<Venda> le = new VendaJpaController(emf).getVenda(v.getDatavenda());
                Venda venda = null;
                for (Venda ep : le) {
                    ep.setEstado(2);
                    ep.setTiva(new Float(0));
                    ep.setTdesc(new Float(0));
                    ep.setValor(new Float(0));
                    try {
                        new VendaJpaController(emf).edit(ep);
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    venda = ep;
                }
                Vendaanulada anulada = new Vendaanulada(venda.getIdvenda());
                anulada.setData(new Date());
                anulada.setIdutilizador(new Vendedor(client));
                anulada.setVenda(venda);
                anulada.setMotivo(motivo);
                try {
                    new VendaanuladaJpaController(emf).create(anulada);
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
                }
                vp = vp - v.getValor();
                de = de - v.getTdesc();
                imp = imp - v.getTiva();
                v.setEstado(2);
                v.setValor(new Float(0));
                ((DefaultTableModel) table.getModel()).setValueAt(0.0, modelRow, 7);
                ((DefaultTableModel) table.getModel()).setValueAt("Anulada", modelRow, 8);
                lbsaldo2.setText("Valor Total: " + vp);
                JOptionPane.showMessageDialog(null, "Anulado com sucesso");
            }
        }
    };
    private void tabelaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFacturaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaFacturaMouseClicked

    private void jdInicio1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdInicio1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdInicio1KeyReleased

    private void jdFim1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdFim1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdFim1KeyReleased

    private void jBpesquisarProdFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBpesquisarProdFacturaActionPerformed
        try {
            listafatura.clear();
            System.out.println(jdInicio1.getDate());
            listafatura = new VendaJpaController(emf).getFacturaPeriodo(jdInicio1.getDate(), jdFim1.getDate(), cbtipo.getSelectedIndex(), cbvendedor.getSelectedItem());
            mostraFactura();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBpesquisarProdFacturaActionPerformed

    private void modeloProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modeloProdutoMouseClicked

    }//GEN-LAST:event_modeloProdutoMouseClicked

    public void combobox(JComboBox c) {
        c.removeAllItems();
        c.addItem("Todos");
        c.addItem("Credito");
        c.addItem("Dinheiro");
    }

    public void comboboxv(JComboBox c) {
        c.removeAllItems();
        c.addItem("Todos");
        List<Vendedor> lv = new VendedorJpaController(emf).getVendedorV();
        for (Vendedor v : lv) {
            c.addItem(v);
        }
    }

    private void jdInicioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdInicioKeyReleased
        //        try {
        //            mostraVenda(new VendaJpaController(emf).findVendaEntities(), 0);
        //        } catch (Exception e) {
        //        }
    }//GEN-LAST:event_jdInicioKeyReleased

    private void jdFimKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdFimKeyReleased
        //        try {
        //            mostraVenda(new VendaJpaController(emf).findVendaEntities(), 0);
        //        } catch (Exception e) {
        //        }
    }//GEN-LAST:event_jdFimKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();
        parametros.put("di", jdInicio.getDate());
        parametros.put("df", jdFim.getDate());
        parametros.put("img", pl);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listavedas);
        // String path = "/relatorios/recibov.jasper";
        try {
            //String path = "C:\\Users\\Ussimane\\Desktop\\GestaoLoja\\src\\relatorios\\proproforma.jasper";
            String path = new File("relatorios/vendas.jasper").getAbsolutePath();
            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Relatorio de vendas", true);
            viewer.setSize(1024, 768);
            viewer.setLocationRelativeTo(null);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBpesquisarProdVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBpesquisarProdVendaActionPerformed
        try {
            listavedas.clear();
            listavedas = new VendaJpaController(emf).getVendaPeriodo(jdInicio.getDate(), jdFim.getDate(), Comboproduto.getSelectedItem());
            mostraVenda();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBpesquisarProdVendaActionPerformed

    private void cbtipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbtipoItemStateChanged
        try {
            listafatura.clear();
            listafatura = new VendaJpaController(emf).getFacturaPeriodo(jdInicio1.getDate(), jdFim1.getDate(), cbtipo.getSelectedIndex(), cbvendedor.getSelectedItem());
            mostraFactura();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbtipoItemStateChanged

    private void cbvendedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbvendedorItemStateChanged
        try {
            listafatura.clear();
            listafatura = new VendaJpaController(emf).getFacturaPeriodo(jdInicio1.getDate(), jdFim1.getDate(), cbtipo.getSelectedIndex(), cbvendedor.getSelectedItem());
            mostraFactura();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbvendedorItemStateChanged

    private void txtsfactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsfactKeyReleased
        try {
            listafatura.clear();
            listafatura = new VendaJpaController(emf).getFacturaSerie(txtsfact.getText());
            mostraFactura();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtsfactKeyReleased

    private void ComboprodutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboprodutoItemStateChanged
        if (Comboproduto.getSelectedItem() == null) {
            return;
        }
//        Produto p = (Produto) Comboproduto.getSelectedItem();
//        tfcp.setText(p.getCodigo());
//        txtQtVendida.setText("1");
    }//GEN-LAST:event_ComboprodutoItemStateChanged

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        try {
            analisarTab();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        List<Venda> lvv = new ArrayList<>();
        float saldov = 0;
        float desv = 0;
        float imv = 0;
        Date d = null;
        float vpv = 0;
        float dev = 0;
        float impv = 0;
        int j = -1;
        int k = 0;
        Venda v;
        for (int i = 0; i < listafatura.size(); i++) {
            if (d == null || d.equals(listafatura.get(i).getDatavenda()) == false) {
                //  float valo = (float) new VendaJpaController(emf).getVendaF(listafatura.get(i).getDatavenda());//listafatura.get(i).getValor();
                v = new Venda();
                if (j != -1) {
                    //  System.out.println("j: "+j);
                    lvv.get(j).setTdesc(desv); //fmodel.setValueAt(desv, j, 5);
                    lvv.get(j).setTiva(imv);// fmodel.setValueAt(imv, j, 6);
                    lvv.get(j).setValor(saldov);
                    //fmodel.setValueAt(saldov, j, 7);
                    vpv = vpv + saldov;
                    dev = dev + desv;
                    impv = impv + imv;
                    saldov = 0;
                    imv = 0;
                    desv = 0;
                }
                v.setSeriefactura(listafatura.get(i).getSeriefactura());//fmodel.setValueAt(, k, 1);
                v.setIdcliente(listafatura.get(i).getIdcliente()); //fmodel.setValueAt(listafatura.get(i).getIdcliente().getNome(), k, 2);
                v.setIdvendedor(listafatura.get(i).getIdvendedor()); //fmodel.setValueAt(listafatura.get(i).getIdvendedor(), k, 3);
                v.setDatavenda(listafatura.get(i).getDatavenda()); //fmodel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listafatura.get(i).getDatavenda()), k, 4);
                // fmodel.setValueAt(desc, k, 5);

                saldov = saldov + listafatura.get(i).getValor();
                imv = imv + listafatura.get(i).getTiva();
                desv = desv + listafatura.get(i).getTdesc();
                d = listafatura.get(i).getDatavenda();
//                float de = new Double(Math.ceil((desc.floatValue() / 100) * valo)).floatValue();
                j = k;
                k = k + 1;
                //  }
                lvv.add(v);
            } else {
                saldov = saldov + listafatura.get(i).getValor();
                imv = imv + listafatura.get(i).getTiva();
                desv = desv + listafatura.get(i).getTdesc();
            }
        }
        if (j != -1) {
            //  System.out.println("j: "+j);
            lvv.get(j).setTdesc(desv); //fmodel.setValueAt(desv, j, 5);
            lvv.get(j).setTiva(imv);// fmodel.setValueAt(imv, j, 6);
            lvv.get(j).setValor(saldov);
        }
        vpv = vpv + saldov;
        dev = dev + desv;
        impv = impv + imv;
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();
        parametros.put("di", jdInicio1.getDate());
        parametros.put("df", jdFim1.getDate());
        parametros.put("img", pl);
        parametros.put("tve", vpv);
        parametros.put("tdesc", dev);
        parametros.put("tivas", impv);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lvv);
        // String path = "/relatorios/recibov.jasper";
        try {
            //String path = "C:\\Users\\Ussimane\\Desktop\\GestaoLoja\\src\\relatorios\\proproforma.jasper";
            String path = new File("relatorios/vfactura.jasper").getAbsolutePath();
            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Relatorio de vendas/cliente", true);
            viewer.setSize(1024, 768);
            viewer.setLocationRelativeTo(null);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jBpesquisarProdVenda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBpesquisarProdVenda1ActionPerformed
        try {
            listamov.clear();
            listamov = new MoviementoCtr(emf).getMovimento(jdInicio2.getDate(), jdFim2.getDate(), (Produto) Comboproduto1.getSelectedItem());
            mostraMovimento();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBpesquisarProdVenda1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jdFim2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdFim2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdFim2KeyReleased

    private void jdInicio2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdInicio2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdInicio2KeyReleased

    private void Comboproduto1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Comboproduto1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_Comboproduto1ItemStateChanged

    private void modeloProduto1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modeloProduto1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_modeloProduto1MouseClicked

    public void analisarTab() throws Exception {
        if (jTabbedPane1.getSelectedIndex() == 0) {
            listavedas.clear();
            listavedas = new VendaJpaController(emf).getVendaPeriodo(jdInicio.getDate(), jdFim.getDate(), Comboproduto.getSelectedItem());
            mostraVenda();
        } else {
            listafatura.clear();
            System.out.println(jdInicio1.getDate());
            listafatura = new VendaJpaController(emf).getFacturaPeriodo(jdInicio1.getDate(), jdFim1.getDate(), cbtipo.getSelectedIndex(), cbvendedor.getSelectedItem());
            mostraFactura();
        }
    }

    public void comboboxp(JComboBox c) {
        List<Produto> listaTipo = new ProdutoJpaController(emf).getProdAsc();
        if (listaTipo.isEmpty()) {
            return;
        }
        String[] el = new String[]{" Todos Medicamentos"};
        Produto[] elements = listaTipo.toArray(new Produto[]{});
        AutoCompleteSupport support = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));

    }

    public void mostraMovimento() {
        while (mvmodel.getRowCount() > 0) {
            mvmodel.removeRow(0);
        }
        int titem = 0;
        float tvp = 0;
        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listamov.size(); i++) {
//            float v = listamov.get(i).getValor();
//            int t = 0;
//            if (v > 0) {
//                t = listamov.get(i).getQc();
//            }
            mvmodel.addRow(linha);
            mvmodel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listamov.get(i).getData()), i, 0);
            mvmodel.setValueAt(listamov.get(i).getTipomov(), i, 1);
            mvmodel.setValueAt(listamov.get(i).getQtd(), i, 2);
            mvmodel.setValueAt(listamov.get(i).getPreco(), i, 3);
            mvmodel.setValueAt(listamov.get(i).getStock(), i, 4);
            //  mvmodel.setValueAt(v, i, 4);
            mvmodel.setValueAt(listamov.get(i).getBalcao(), i, 5);
            mvmodel.setValueAt(listamov.get(i).getEstado(), i, 6);
            mvmodel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listamov.get(i).getLot()), i, 7);
//            if (listavedas.get(i).getEstado() == 0) {
//                mvmodel.setValueAt("activa", i, 8);
//            }
//            if (listavedas.get(i).getEstado() == 1) {
//                mvmodel.setValueAt("Restaurada", i, 8);
//            }
//            if (listavedas.get(i).getEstado() == 2) {
//                mvmodel.setValueAt("Anulada", i, 8);
//            }
//            titem = titem + t;
//            tvp = tvp + v;
        }
//        lbtotalitem.setText("Total Itens: " + titem);
//        lbsalmed.setText("Total Valor: " + tvp);
    }

    public void mostraVenda() throws Exception {

        while (vmodel.getRowCount() > 0) {
            vmodel.removeRow(0);
        }
        int titem = 0;
        float tvp = 0;
        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listavedas.size(); i++) {
            float v = listavedas.get(i).getValor();
            int t = 0;
            if (v > 0) {
                t = listavedas.get(i).getQc();
            }
            vmodel.addRow(linha);
            vmodel.setValueAt(listavedas.get(i).getIdproduto().getNome(), i, 0);
            vmodel.setValueAt(listavedas.get(i).getPrec(), i, 1);
            vmodel.setValueAt(listavedas.get(i).getQc(), i, 2);
            vmodel.setValueAt(listavedas.get(i).getQtd(), i, 3);
            vmodel.setValueAt(v, i, 4);
            vmodel.setValueAt(listavedas.get(i).getDesconto() + "%", i, 5);
            vmodel.setValueAt(listavedas.get(i).getIdvendedor().getNomecompleto(), i, 6);
            vmodel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listavedas.get(i).getDatavenda()), i, 7);
            if (listavedas.get(i).getEstado() == 0) {
                vmodel.setValueAt("activa", i, 8);
            }
            if (listavedas.get(i).getEstado() == 1) {
                vmodel.setValueAt("Restaurada", i, 8);
            }
            if (listavedas.get(i).getEstado() == 2) {
                vmodel.setValueAt("Anulada", i, 8);
            }
            titem = titem + t;
            tvp = tvp + v;
        }
        lbtotalitem.setText("Total Itens: " + titem);
        lbsalmed.setText("Total Valor: " + tvp);
    }

    public void mostraFactura() throws Exception {

        while (fmodel.getRowCount() > 0) {
            fmodel.removeRow(0);
        }
        float saldo = 0;
        float des = 0;
        float im = 0;
        Date d = null;
        vp = 0;
        de = 0;
        imp = 0;
        int j = -1;
        int k = 0;
        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listafatura.size(); i++) {
            if (d == null || d.equals(listafatura.get(i).getDatavenda()) == false) {
                //  float valo = (float) new VendaJpaController(emf).getVendaF(listafatura.get(i).getDatavenda());//listafatura.get(i).getValor();
                if (j != -1) {
                    //  System.out.println("j: "+j);
                    fmodel.setValueAt(des, j, 5);
                    fmodel.setValueAt(im, j, 6);
                    fmodel.setValueAt(saldo, j, 7);
                    vp = vp + saldo;
                    de = de + des;
                    imp = imp + im;
                    saldo = 0;
                    im = 0;
                    des = 0;
                }
                Integer desc = listafatura.get(i).getDesconto();
                fmodel.addRow(linha);
                fmodel.setValueAt(listafatura.get(i), k, 0);
                fmodel.setValueAt(listafatura.get(i).getSeriefactura(), k, 1);
                fmodel.setValueAt(listafatura.get(i).getIdcliente().getNome(), k, 2);
                fmodel.setValueAt(listafatura.get(i).getIdvendedor(), k, 3);
                fmodel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listafatura.get(i).getDatavenda()), k, 4);
                // fmodel.setValueAt(desc, k, 5);

                if (listafatura.get(i).getEstado() == 0) {
                    fmodel.setValueAt("activa", k, 8);
                }
                if (listafatura.get(i).getEstado() == 1) {
                    fmodel.setValueAt("Restaurada", k, 8);
                }
                if (listafatura.get(i).getEstado() == 2) {
                    fmodel.setValueAt("Anulada", k, 8);
                }
                saldo = saldo + listafatura.get(i).getValor();
                im = im + listafatura.get(i).getTiva();
                des = des + listafatura.get(i).getTdesc();
                d = listafatura.get(i).getDatavenda();
//                float de = new Double(Math.ceil((desc.floatValue() / 100) * valo)).floatValue();
                j = k;
                k = k + 1;
                //  }
            } else {
                saldo = saldo + listafatura.get(i).getValor();
                im = im + listafatura.get(i).getTiva();
                des = des + listafatura.get(i).getTdesc();
            }
        }
        if (j != -1) {
            fmodel.setValueAt(des, j, 5);
            fmodel.setValueAt(im, j, 6);
            fmodel.setValueAt(saldo, j, 7);
        }
        vp = vp + saldo;
        de = de + des;
        imp = imp + im;
        lbdesc.setText("Total Desconto: " + de);
        lbiva.setText("Total IVA: " + imp);
        lbsaldo2.setText("Valor Total: " + vp);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Comboproduto;
    private javax.swing.JComboBox Comboproduto1;
    private javax.swing.JComboBox cbtipo;
    private javax.swing.JComboBox cbvendedor;
    private javax.swing.JButton jBpesquisarProdFactura;
    private javax.swing.JButton jBpesquisarProdVenda;
    private javax.swing.JButton jBpesquisarProdVenda1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdFim;
    private com.toedter.calendar.JDateChooser jdFim1;
    private com.toedter.calendar.JDateChooser jdFim2;
    private com.toedter.calendar.JDateChooser jdInicio;
    private com.toedter.calendar.JDateChooser jdInicio1;
    private com.toedter.calendar.JDateChooser jdInicio2;
    private com.toedter.calendar.JDateChooser jdpag;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JLabel lbdesc;
    private javax.swing.JLabel lbiva;
    private javax.swing.JLabel lbsaldo;
    private javax.swing.JLabel lbsaldo1;
    private javax.swing.JLabel lbsaldo2;
    private javax.swing.JLabel lbsalmed;
    private javax.swing.JLabel lbsalmed1;
    private javax.swing.JLabel lbtotalitem;
    private javax.swing.JLabel lbtotalitem1;
    private javax.swing.JTable modeloProduto;
    private javax.swing.JTable modeloProduto1;
    private javax.swing.JPanel painelVenda;
    private javax.swing.JPanel painelVenda1;
    private javax.swing.JPanel painelVenda2;
    private javax.swing.JTable tabelaFactura;
    private javax.swing.JTextField txtsfact;
    // End of variables declaration//GEN-END:variables
}
