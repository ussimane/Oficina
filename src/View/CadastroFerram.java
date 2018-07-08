package View;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.EntradaJpaController;
import controller.FerramentaJpaController;
import controller.FornecedorJpaController;
import controller.ManutencaoJpaController;
import controller.MecancoferramentaJpaController;
import controller.MecanicoJpaController;
import controller.MecanicomanutencaoJpaController;
import controller.PecamanutencaoJpaController;
import controller.ProdutoJpaController;
import controller.SaidaJpaController;
import controller.TipomanutencaoJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import metodos.ButtonColumn;
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Entradaviatura;
import modelo.Ferramenta;
import modelo.Fornecedor;
import modelo.Manutencao;
import modelo.Mecancoferramenta;
import modelo.MecancoferramentaPK;
import modelo.Mecanico;
import modelo.Mecanicomanutencao;
import modelo.Pecamanutencao;
import modelo.PecamanutencaoPK;
import modelo.Produto;
import modelo.Saida;
import modelo.Tipomanutencao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 *
 * @author rjose
 */
public final class CadastroFerram extends javax.swing.JDialog {

    JasperPrint jpPrint;
    JasperViewer jv;
    HashMap<String, Object> par = new HashMap<String, Object>();
    Map parametros = new HashMap<>();

    int t = 0;
    List<Ferramenta> cbferramenta = new ArrayList<>();
    //List<Tipomanutencao> cblistatipomanute = new ArrayList<>();
    List<Mecanico> cblistamecanico = new ArrayList<>();
    List<Pecamanutencao> listapeca = new ArrayList<>();
    List<Ferramenta> listaferramenta = new ArrayList<>();
    List<Mecancoferramenta> listamecanicofer = new ArrayList<>();
    AutoCompleteSupport support3 = null;
    AutoCompleteSupport support2 = null;
    AutoCompleteSupport support = null;
    static Entradaviatura produ;
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"id", "Ferramenta", "Quantidade", "Adicionar", "Retirar", "Editar", "Remover"});
    DefaultTableModel dTableModel2 = new DefaultTableModel(null, new String[]{"id", "Data", "Mecanico", "Ferramenta", "Qtd", "Remover"});
    private int client = 0;
    String cbarra = "";

    @Override
    public void list() {
        super.list();
    }

    public CadastroFerram(int c) throws Exception {
        initComponents();
        this.setLocation(40, 225);
        // this.setResizable(false);
        //  this.produ = p;
        Date d = new Date();
        this.client = c;
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date n = cal.getTime();
//        jdie.setDate(d);
//        jdfe.setDate(d);
        comboboxF(Combofer);
        comboboxMe(Combomecanico);
       // comboboxMa(Combomanute);

        // jdfe.setDate(d);
        //  listaE = new EntradaJpaController(emf).getEntradaPeriodo(d, null, p, cbfo.getSelectedItem());
        listaferramenta = new FerramentaJpaController(emf).getFerramentaAsc();
        mostraferramenta();
        this.TableEntrada.setAutoCreateRowSorter(true);
        this.TableEntrada.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableEntrada.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableEntrada.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableEntrada.getColumnModel().getColumn(3).setMaxWidth(50);//setPreferredWidth(3);
        this.TableEntrada.getColumnModel().getColumn(4).setMaxWidth(50);
        this.TableEntrada.getColumnModel().getColumn(5).setMaxWidth(50);//setPreferredWidth(3);
        this.TableEntrada.getColumnModel().getColumn(6).setMaxWidth(50);
        this.TableEntrada.setRowHeight(30);

        Icon id = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_ver.png"));
        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
        Icon is = new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"));
        ButtonColumn bcs = new ButtonColumn(this.TableEntrada, addent, 3, is);
        ButtonColumn bcd = new ButtonColumn(this.TableEntrada, retirar, 4, id);
        ButtonColumn bce = new ButtonColumn(this.TableEntrada, editar, 5, ie);
        ButtonColumn bcr = new ButtonColumn(this.TableEntrada, delete, 6, ir);

        listamecanicofer = new MecancoferramentaJpaController(emf).getMecanicoFerramenta();
        mostramecanicofer();
        this.TableSaida.setAutoCreateRowSorter(true);
        this.TableSaida.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableSaida.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableSaida.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableSaida.getColumnModel().getColumn(5).setMaxWidth(50);//setPreferredWidth(3);
        this.TableSaida.setRowHeight(30);
        // Icon ies = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        // Icon irs = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
//        ButtonColumn bces = new ButtonColumn(this.TableSaida, editars, 7, ie);
        ButtonColumn bcrs = new ButtonColumn(this.TableSaida, deletes, 5, ir);

    }

    public void comboboxF(JComboBox c) {
        cbferramenta = new FerramentaJpaController(emf).getFerramentaAsc();
        if (cbferramenta.isEmpty()) {
            return;
        }
        Ferramenta[] elements = cbferramenta.toArray(new Ferramenta[]{});
        support = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));

    }

    public void comboboxMe(JComboBox c) {
        cblistamecanico = new MecanicoJpaController(emf).getMecanicoAsc();
        if (cblistamecanico.isEmpty()) {
            return;
        }
        Mecanico[] elements = cblistamecanico.toArray(new Mecanico[]{});
        support2 = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));
    }

    public void mostraferramenta() throws Exception {

        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listaferramenta.size(); i++) {

            dTableModel.addRow(linha);
            dTableModel.setValueAt(listaferramenta.get(i), i, 0);
            dTableModel.setValueAt(listaferramenta.get(i).getDescricao(), i, 1);
            // dTableModel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaferramenta.get(i).getEntradaPK().getData()), i, 2);
            dTableModel.setValueAt(listaferramenta.get(i).getQtd(), i, 2);
//            dTableModel.setValueAt(listaferramenta.get(i).getQv(), i, 4);
//            dTableModel.setValueAt(listaferramenta.get(i).getPreco(), i, 5);
//            dTableModel.setValueAt(listaferramenta.get(i).getIdfornecedor().getNome(), i, 6);
        }
    }

    public void mostramecanicofer() throws Exception {

        while (dTableModel2.getRowCount() > 0) {
            dTableModel2.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listamecanicofer.size(); i++) {
            dTableModel2.addRow(linha);
            dTableModel2.setValueAt(listamecanicofer.get(i), i, 0);
            dTableModel2.setValueAt(listamecanicofer.get(i).getMecancoferramentaPK().getData(), i, 1);
            dTableModel2.setValueAt(listamecanicofer.get(i).getMecanico().getNome(), i, 2);
            dTableModel2.setValueAt(listamecanicofer.get(i).getIdferramenta().getDescricao(), i, 3);
            dTableModel2.setValueAt(listamecanicofer.get(i).getQtd(), i, 4);
        }
    }

    Action ver = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Entrada p = (Entrada) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormEntVista f = new FormEntVista(new javax.swing.JFrame(), p, true);
            f.setVisible(true);
        }
    };

    Action addent = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Ferramenta p = (Ferramenta) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormAddFer f = new FormAddFer(new javax.swing.JFrame(), p, null, 0, client, true);
            f.setVisible(true);
            p = new FerramentaJpaController(emf).findFerramenta(p.getIdferramenta());
            int i = listaferramenta.indexOf(p);
            listaferramenta.set(i, p);
            try {
                mostraferramenta();
            } catch (Exception ex) {
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    Action retirar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Ferramenta p = (Ferramenta) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormSubFer f = new FormSubFer(new javax.swing.JFrame(), p, null, 0, client, true);
            f.setVisible(true);
            p = new FerramentaJpaController(emf).findFerramenta(p.getIdferramenta());
            int i = listaferramenta.indexOf(p);
            listaferramenta.set(i, p);
            try {
                mostraferramenta();
            } catch (Exception ex) {
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    Action editar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Ferramenta p = (Ferramenta) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormFerramenta f = new FormFerramenta(new javax.swing.JFrame(), p, table.getModel(), modelRow, "Editar Ferramenta", true);
            f.setVisible(true);
            //  loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
        }
    };
    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "Pretende remover esta Ferramenta?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            Ferramenta p = (Ferramenta) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            if (new MecancoferramentaJpaController(emf).existeFerramenta(p) != 0) {
                JOptionPane.showMessageDialog(null, "Esta ferramenta nao pode ser removida,\n "
                        + "porque ja foi requisitada!\n"
                        + "Primeiro remova todas as requisicoes desta ferramenta", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
                // }
            } else {
                p = new FerramentaJpaController(emf).findFerramenta(p.getIdferramenta());
                int i = listaferramenta.indexOf(p);
                try {
                    new FerramentaJpaController(emf).destroy(p.getIdferramenta());
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(CadastroFerram.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CadastroFerram.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                listaferramenta.remove(i);
//                try {
//                    mostramanute();
//                } catch (Exception ex) {
//                    Logger.getLogger(CadastroFerram.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        }
    };
    Action deletes = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "Pretende remover esta Requisicao?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            if (true) {
                Mecancoferramenta p = (Mecancoferramenta) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();/////////////////////////////
                try {
                    Ferramenta fer = new FerramentaJpaController(emf).findFerramentaS(p.getIdferramenta().getIdferramenta(), em);
                    fer.setQtd(fer.getQtd() + p.getQtd());

                    new FerramentaJpaController(emf).edit(fer, fer, em);
                    new MecancoferramentaJpaController(emf).destroy(p.getMecancoferramentaPK(), em);

                    //  labelSucesso.setText("Produto removido com Sucesso");
                    ((DefaultTableModel) table.getModel()).removeRow(modelRow);

                    int i = listamecanicofer.indexOf(p);

                    em.getTransaction().commit();
                    // ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                    listamecanicofer.remove(i);
                    int ifer = listaferramenta.indexOf(fer);
                    listaferramenta.set(ifer, fer);
                    mostraferramenta();
//                    mostraPecas();
                } catch (NonexistentEntityException ex) {
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(CadastroFerram.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(CadastroFerram.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (em != null) {
                        em.close();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Esta Saida nao pode ser removida,\n "
                        + "porque", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }

        }
    };

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelHistory = new javax.swing.JLabel();
        Emprestimo = new javax.swing.JTabbedPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        painelProduto = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableEntrada = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btEnt = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableSaida = new javax.swing.JTable();
        btAdd = new javax.swing.JButton();
        Combomecanico = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Combofer = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        txtQtVendida = new javax.swing.JTextField();
        btadimp3 = new javax.swing.JButton();
        btedimp1 = new javax.swing.JButton();
        btdelimp1 = new javax.swing.JButton();

        setDefaultCloseOperation(getLimpaVenda());
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));

        labelHistory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelHistory.setText("Ferramentas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(570, 570, 570)
                .addComponent(labelHistory)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(labelHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        Emprestimo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmprestimoMouseClicked(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(240, 240, 240));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        painelProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TableEntrada.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableEntrada.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableEntrada.setModel(dTableModel);
        TableEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableEntradaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableEntrada);

        javax.swing.GroupLayout painelProdutoLayout = new javax.swing.GroupLayout(painelProduto);
        painelProduto.setLayout(painelProdutoLayout);
        painelProdutoLayout.setHorizontalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        painelProdutoLayout.setVerticalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutoLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btEnt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btEnt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btEnt.setText("Adicionar");
        btEnt.setToolTipText("");
        btEnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEntActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btEnt)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btEnt)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jDesktopPane1.add(jPanel3);
        jPanel3.setBounds(0, 0, 1280, 370);

        Emprestimo.addTab("Lista Geral", jDesktopPane1);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TableSaida.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableSaida.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableSaida.setModel(dTableModel2);
        jScrollPane4.setViewportView(TableSaida);

        btAdd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAdd.setForeground(new java.awt.Color(102, 0, 0));
        btAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/oneb.png"))); // NOI18N
        btAdd.setText("Adicionar");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        Combomecanico.setToolTipText("Seleccione ou pesquise produto");
        Combomecanico.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Combomecanico.setOpaque(false);
        Combomecanico.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CombomecanicoItemStateChanged(evt);
            }
        });

        jLabel3.setText("Mecanico:");

        jLabel4.setText("Ferramenta:");

        Combofer.setToolTipText("Seleccione ou pesquise produto");
        Combofer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Combofer.setOpaque(false);
        Combofer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboferItemStateChanged(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Quantidade:");

        txtQtVendida.setToolTipText("");
        txtQtVendida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtVendidaActionPerformed(evt);
            }
        });

        btadimp3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btadimp3.setToolTipText("Adicionar novo Mecanico");
        btadimp3.setBorder(null);
        btadimp3.setBorderPainted(false);
        btadimp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btadimp3ActionPerformed(evt);
            }
        });

        btedimp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        btedimp1.setToolTipText("Editar o Mecanco Selecionado");
        btedimp1.setBorder(null);
        btedimp1.setBorderPainted(false);
        btedimp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btedimp1ActionPerformed(evt);
            }
        });

        btdelimp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"))); // NOI18N
        btdelimp1.setToolTipText("Apagar o Mecanico seleccionado");
        btdelimp1.setBorder(null);
        btdelimp1.setBorderPainted(false);
        btdelimp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelimp1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Combomecanico, 0, 348, Short.MAX_VALUE)
                    .addComponent(Combofer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btadimp3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btedimp1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btdelimp1))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Combomecanico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addComponent(btadimp3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btedimp1)
                            .addComponent(btdelimp1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(Combofer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btAdd)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
        );

        Emprestimo.addTab("Registo de Requisicoes", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Emprestimo)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(Emprestimo)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void analisarTab() throws Exception {
//        if (Emprestimo.getSelectedIndex() == 0) {
//            listaE.clear();
//            listaE = new EntradaJpaController(emf).getEntFull((Produto) cbpro.getSelectedItem());//.getEntradaPeriodo(jdie.getDate(), jdfe.getDate(), cbpro.getSelectedItem(), cbfo.getSelectedItem());
//            mostramanute();
//        } else {
//            // this.setTitle("Emprestimo de Prouto");
////        Date d = new Date();
////        Calendar cal = new GregorianCalendar();
////        cal.setTime(d);
////        cal.set(Calendar.DAY_OF_MONTH, 1);
////        Date n = cal.getTime();
////        jdis.setDate(d);
////        jdfs.setDate(d);
//            listaS.clear();
//            listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());
//            mostraPecas();
//
//        }

    }

    public void setIndexAba(int i) {
        Emprestimo.setSelectedIndex(i);
    }

    public void selecionarPromocao() {
        Emprestimo.setSelectedIndex(1);
    }

    private void EmprestimoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmprestimoMouseClicked
        try {
            analisarTab();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_EmprestimoMouseClicked


    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus

    }//GEN-LAST:event_formWindowLostFocus

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost

    }//GEN-LAST:event_formFocusLost

    public int getLimpaVenda() {
        return 1;
    }


    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        try {
            int ip = showListaCompra(0);
            txtQtVendida.setText("1");//
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
//        txtcbarra.setText("");
//        cbarra = "";
    }//GEN-LAST:event_btAddActionPerformed
    static List<Entrada> ls = new ArrayList<Entrada>();

    public int showListaCompra(int i) {
        int icb = 0;
        try {
            //lprov tem um unico produto com a quantidade que devera permanecer na base de dados depois da venda
            //lpro tem um unico produto com qunatidade total seleccionada para veificar se esta disponivel
            //lproe tem toda seleccao feita conforme aparecera no recibo, e sera usado para modificar a seleccao
            //lsp tem a lista de entradas que permanecerao na base de dados depois da venda
            //le tem a entrada que devera permanecer na base de dados depois da venda
            //ls tem a entrada com a quantidade total seleccionada
            if (Combomecanico.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccionae um Mecanico", "Atencao", JOptionPane.WARNING_MESSAGE);
                Combomecanico.requestFocusInWindow();//grabFocus();
                return 0;
            }
            if (Combofer.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccionae uma Ferramenta", "Atencao", JOptionPane.WARNING_MESSAGE);
                Combofer.requestFocusInWindow();//grabFocus();
                return 0;
            }
            // int qitem = 0;
            int id = ((Ferramenta) Combofer.getSelectedItem()).getIdferramenta();
            if (txtQtVendida.getText().trim().equals("") || txtQtVendida.getText().equals("0")) {
                JOptionPane.showMessageDialog(this, "Insira a quantidade", "Atencao", JOptionPane.WARNING_MESSAGE);
                txtQtVendida.requestFocusInWindow();//grabFocus();
                return 0;
            }
            int qtt = Integer.valueOf(txtQtVendida.getText());
            Ferramenta fer = new FerramentaJpaController(emf).findFerramenta(id);

            String produto = fer.getDescricao();
            if (fer.getQtd() == 0) {
                JOptionPane.showMessageDialog(this, "Nao ha " + produto + " no Armazem", "Atencao", JOptionPane.WARNING_MESSAGE);
                txtQtVendida.grabFocus();
                return 0;
            }
            if (qtt > fer.getQtd()) {
                JOptionPane.showMessageDialog(this, "A Qtd. de " + produto + " disponivel no Armazem e de  " + fer.getQtd(), "Atencao", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            Mecanico me = (Mecanico) Combomecanico.getSelectedItem();
            Mecancoferramenta mf = new Mecancoferramenta(new MecancoferramentaPK(me.getIdmecanico(), new Date()));
            mf.setIdferramenta(fer);
            mf.setMecanico(me);
            mf.setQtd(qtt);
            int respo = JOptionPane.showOptionDialog(null, "Pretende registar esta Requisicao?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return -1;
            }
            EntityManager em = emf.createEntityManager();
            FerramentaJpaController ec = new FerramentaJpaController(emf);
            em.getTransaction().begin();
            try {
                fer = ec.findFerramentaS(fer.getIdferramenta(), em);
                if (fer.getQtd() < qtt) {
                    JOptionPane.showMessageDialog(this, "A quantidade seleccionada e maior que a quantidade disponivel neste momento", "Atencao", JOptionPane.WARNING_MESSAGE);
                    em.clear();
                    em.getTransaction().commit();
                    return -1;
                }
                fer.setQtd(fer.getQtd() - qtt);
                ec.edit(fer, fer, em);
                new MecancoferramentaJpaController(emf).create(mf, em);
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Registado com Sucesso");
                listamecanicofer.add(mf);
                mostramecanicofer();
                int ifer = listaferramenta.indexOf(fer);
                listaferramenta.set(ifer, fer);
                mostraferramenta();
            } catch (NonexistentEntityException ex) {
                //  em.getTransaction().rollback();
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                //   em.getTransaction().rollback();
                em.clear();
                em.getTransaction().commit();

                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        } catch (NumberFormatException | HeadlessException e) {
        }
        return icb;
    }

    private void CombomecanicoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CombomecanicoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CombomecanicoItemStateChanged

    private void ComboferItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboferItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboferItemStateChanged

    private void txtQtVendidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtVendidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtVendidaActionPerformed

    private void btEntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntActionPerformed
        FormFerramenta f = new FormFerramenta(new javax.swing.JFrame(), null, null, 0, "Adicionar Ferramenta", true);
        f.setVisible(true);
        listaferramenta = new FerramentaJpaController(emf).getFerramentaAsc();
        try {
            mostraferramenta();
        } catch (Exception ex) {
            Logger.getLogger(CadastroFerram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btEntActionPerformed

    private void TableEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableEntradaMouseClicked

    }//GEN-LAST:event_TableEntradaMouseClicked

    private void btadimp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btadimp3ActionPerformed
        FormMecanico f = new FormMecanico(new javax.swing.JFrame(), null, null, 0, "Adicionar novo Mecanico", true);
        f.setVisible(true);
        if (support2 != null && support2.isInstalled()) {
            support2.uninstall();
        }
        comboboxMe(Combomecanico);
    }//GEN-LAST:event_btadimp3ActionPerformed

    private void btedimp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btedimp1ActionPerformed
        if(Combomecanico.getSelectedItem()==null) return;
        Mecanico i = (Mecanico) Combomecanico.getSelectedItem();
        FormMecanico f = new FormMecanico(new javax.swing.JFrame(), null, null, 0, "Editar os dados do Mecanico", true);
        f.setVisible(true);
        if (support2 != null && support2.isInstalled()) {
            support2.uninstall();
        }
        comboboxMe(Combomecanico);
    }//GEN-LAST:event_btedimp1ActionPerformed

    private void btdelimp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelimp1ActionPerformed
        if(Combomecanico.getSelectedItem()==null) return;
        Mecanico i = (Mecanico) Combomecanico.getSelectedItem();
        if ((new MecanicomanutencaoJpaController(emf).existeMecanico(i) != 0)) {
            JOptionPane.showMessageDialog(this, "Este Mecanico tem o seu registo em uma das listas de Manutencao!\n Nao pode ser apagado", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (new MecancoferramentaJpaController(emf).existeMecanico(i) != 0) {
            JOptionPane.showMessageDialog(this, "Este Mecanico tem o seu registo na lista de ferramentas requitadas!\n Nao pode ser apagado", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            try {
                new MecanicoJpaController(emf).destroy(i.getIdmecanico());
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Apagado com Sucesso");
        }
        if (support2 != null && support2.isInstalled()) {
            support2.uninstall();
        }
        comboboxMe(Combomecanico);
    }//GEN-LAST:event_btdelimp1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Combofer;
    private javax.swing.JComboBox Combomecanico;
    private javax.swing.JTabbedPane Emprestimo;
    private javax.swing.JTable TableEntrada;
    private javax.swing.JTable TableSaida;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btEnt;
    private javax.swing.JButton btadimp3;
    private javax.swing.JButton btdelimp1;
    private javax.swing.JButton btedimp1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JPanel painelProduto;
    private javax.swing.JTextField txtQtVendida;
    // End of variables declaration//GEN-END:variables

}
