package View;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.EntradaJpaController;
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
import metodos.ButtonColumn;
import metodos.ValidarFloat;
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Entradaviatura;
import modelo.Fornecedor;
import modelo.Manutencao;
import modelo.ManutencaoPK;
import modelo.Mecancoferramenta;
import modelo.Mecanico;
import modelo.Mecanicomanutencao;
import modelo.MecanicomanutencaoPK;
import modelo.Pecamanutencao;
import modelo.PecamanutencaoPK;
import modelo.Produto;
import modelo.Saida;
import modelo.Tipomanutencao;
import modelo.Vendedor;
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
public final class CadastroManute extends javax.swing.JDialog {

    JasperPrint jpPrint;
    JasperViewer jv;
    HashMap<String, Object> par = new HashMap<String, Object>();
    Map parametros = new HashMap<>();

    int t = 0;
    List<Produto> cblistapeca = new ArrayList<>();
    List<Tipomanutencao> cblistatipomanute = new ArrayList<>();
    List<Mecanico> cblistamecanico = new ArrayList<>();
    List<Pecamanutencao> listapeca = new ArrayList<>();
    List<Manutencao> listamanute = new ArrayList<>();
    List<Mecanicomanutencao> listamecanico = new ArrayList<>();
    AutoCompleteSupport support3 = null;
    AutoCompleteSupport support2 = null;
    AutoCompleteSupport support = null;
    static Entradaviatura produ;
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"id", "Tipo de Manutencao", "Valor", "Remover"});
    DefaultTableModel dTableModel2 = new DefaultTableModel(null, new String[]{"id", "Peca", "Serie/Lote Nr.", "Preco", "Qtd", "Qtd Itens", "Remover"});
    DefaultTableModel dTableModel3 = new DefaultTableModel(null, new String[]{"id", "Mecanico", "Area", "Remover"});
    private int client = 0;
    private float soma = 0;
    private float somai = 0;
    String cbarra = "";

    @Override
    public void list() {
        super.list();
    }

    public CadastroManute(Entradaviatura p, int c) throws Exception {
        initComponents();
        this.setLocation(40, 225);
        // this.setResizable(false);
        this.produ = p;
        Date d = new Date();
        this.client = c;
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date n = cal.getTime();
        txpreco.setDocument(new ValidarFloat());
//        jdie.setDate(d);
//        jdfe.setDate(d);
        comboboxP(Comboproduto);
        comboboxMe(Combomecanico);
        comboboxMa(Combomanute);

        // jdfe.setDate(d);
        //  listaE = new EntradaJpaController(emf).getEntradaPeriodo(d, null, p, cbfo.getSelectedItem());
        listamanute = new ManutencaoJpaController(emf).getManutencao(this.produ);
        mostramanute();
        this.TableEntrada.setAutoCreateRowSorter(true);
        this.TableEntrada.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableEntrada.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableEntrada.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableEntrada.getColumnModel().getColumn(3).setMaxWidth(50);//setPreferredWidth(3);
        this.TableEntrada.setRowHeight(30);

//        Icon id = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_ver.png"));
//        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
//        Icon is = new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"));
//        ButtonColumn bcs = new ButtonColumn(this.TableEntrada, addent, 7, is);
//        ButtonColumn bcd = new ButtonColumn(this.TableEntrada, ver, 8, id);
//        ButtonColumn bce = new ButtonColumn(this.TableEntrada, editar, 9, ie);
        ButtonColumn bcr = new ButtonColumn(this.TableEntrada, delete, 3, ir);

        listapeca = new PecamanutencaoJpaController(emf).getPecamanutencao(this.produ);
        mostraPecas();
        this.TableSaida.setAutoCreateRowSorter(true);
        this.TableSaida.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableSaida.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableSaida.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableSaida.getColumnModel().getColumn(6).setMaxWidth(50);//setPreferredWidth(3);
        this.TableSaida.setRowHeight(30);
        // Icon ies = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        // Icon irs = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
//        ButtonColumn bces = new ButtonColumn(this.TableSaida, editars, 7, ie);
        ButtonColumn bcrs = new ButtonColumn(this.TableSaida, deletes, 6, ir);
        labelTotal.setText("Preco total:   " + somai);
        listamecanico = new MecanicomanutencaoJpaController(emf).getMecanicomanutencao(this.produ);
        mostraMecanicos();
        this.TableMecanico.setAutoCreateRowSorter(true);
        this.TableMecanico.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableMecanico.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableMecanico.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableMecanico.getColumnModel().getColumn(3).setMaxWidth(50);//setPreferredWidth(3);
        this.TableMecanico.setRowHeight(30);
        // Icon ies = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        // Icon irs = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
//        ButtonColumn bces = new ButtonColumn(this.TableSaida, editars, 7, ie);
        ButtonColumn bcrm = new ButtonColumn(this.TableMecanico, deletem, 3, ir);

    }

    public void comboboxP(JComboBox c) {
        cblistapeca = new ProdutoJpaController(emf).getProdAsc();
        if (cblistapeca.isEmpty()) {
            return;
        }
        Produto[] elements = cblistapeca.toArray(new Produto[]{});
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

    public void comboboxMa(JComboBox c) {
        cblistatipomanute = new TipomanutencaoJpaController(emf).getTipomanuteAsc();
        if (cblistatipomanute.isEmpty()) {
            return;
        }
        Tipomanutencao[] elements = cblistatipomanute.toArray(new Tipomanutencao[]{});
        support3 = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));
    }

    public void mostramanute() throws Exception {

        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listamanute.size(); i++) {
            dTableModel.addRow(linha);
            dTableModel.setValueAt(listamanute.get(i), i, 0);
            dTableModel.setValueAt(listamanute.get(i).getIdtipomanutencao().getDesignacao(), i, 1);
            // dTableModel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listamanute.get(i).getEntradaPK().getData()), i, 2);
            dTableModel.setValueAt(listamanute.get(i).getCustomanutencao(), i, 2);
//            dTableModel.setValueAt(listamanute.get(i).getQv(), i, 4);
//            dTableModel.setValueAt(listamanute.get(i).getPreco(), i, 5);
//            dTableModel.setValueAt(listamanute.get(i).getIdfornecedor().getNome(), i, 6);
            somai = somai + listamanute.get(i).getCustomanutencao();
        }
    }

    public void mostraPecas() throws Exception {

        while (dTableModel2.getRowCount() > 0) {
            dTableModel2.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listapeca.size(); i++) {
            Entrada entr = new EntradaJpaController(emf).getEntData(listapeca.get(i).getDatae());
            dTableModel2.addRow(linha);
            dTableModel2.setValueAt(listapeca.get(i), i, 0);
            dTableModel2.setValueAt(listapeca.get(i).getIdproduto().getNome(), i, 1);
            dTableModel2.setValueAt(entr.getSerie(), i, 2);//(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaS.get(i).getDatae()), i, 2);
            dTableModel2.setValueAt(listapeca.get(i).getPreco(), i, 3);
            //   dTableModel2.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaS.get(i).getSaidaPK().getData()), i, 4);
            dTableModel2.setValueAt(listapeca.get(i).getQtd(), i, 4);
            dTableModel2.setValueAt(listapeca.get(i).getQc(), i, 5);
//            if (listaS.get(i).getObs() != null) {
//                dTableModel2.setValueAt(listaS.get(i).getObs(), i, 7);
//            }
            somai = somai + listapeca.get(i).getPreco();
        }
    }

    public void mostraMecanicos() throws Exception {

        while (dTableModel3.getRowCount() > 0) {
            dTableModel3.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listamecanico.size(); i++) {
            dTableModel3.addRow(linha);
            dTableModel3.setValueAt(listamecanico.get(i), i, 0);
            dTableModel3.setValueAt(listamecanico.get(i).getMecanico().getNome(), i, 1);
            dTableModel3.setValueAt(listamecanico.get(i).getMecanico().getArea(), i, 2);
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
    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "Pretende remover da lista este Tipo de Manutencao?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            float val = 0;
            Manutencao p = (Manutencao) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            if (true) {
                p = new ManutencaoJpaController(emf).findManutencao(p.getManutencaoPK());
                val = p.getCustomanutencao();
                int i = listamanute.indexOf(p);
                try {
                    new ManutencaoJpaController(emf).destroy(p.getManutencaoPK());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
                }
                soma = soma - val;
                labelTotal.setText("Preco total:   " + soma);
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                listamanute.remove(i);
//                try {
//                    mostramanute();
//                } catch (Exception ex) {
//                    Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
//                }
            } else {
                JOptionPane.showMessageDialog(null, "Este registo de Manutencao nao pode ser removida,\n "
                        + "porque \n"
                        + "Primeiro remova todos registos de Saida nesta serie", "Atencao", JOptionPane.WARNING_MESSAGE);
            }
        }
    };
    Action deletes = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "Pretende remover esta Peca na lista?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            if (true) {
                float val = 0;
                Pecamanutencao p = (Pecamanutencao) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();/////////////////////////////
                try {
                    Entrada ent = new EntradaJpaController(emf).findEntradaS(new EntradaPK(p.getIdproduto().getIdproduto(),
                            p.getDatae()), em);
                    Produto pr = new ProdutoJpaController(emf).findProdutoS(p.getIdproduto().getIdproduto(), em);
                    //Entrada ent = new EntradaJpaController(emf).getEntrada(p.getDatae());
                    pr.setQtdvenda(pr.getQtdvenda() + p.getQtd());
                    ent.setQv(ent.getQv() - p.getQtd());
                    val = p.getPreco();
                    new EntradaJpaController(emf).edit(ent, ent, em);
                    new ProdutoJpaController(emf).edit(pr, pr, em);
                    new PecamanutencaoJpaController(emf).destroy(p.getPecamanutencaoPK(), em);

                    //  labelSucesso.setText("Produto removido com Sucesso");
                    //   ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                    int i = listapeca.indexOf(p);

                    em.getTransaction().commit();
                    soma = soma - val;
                    labelTotal.setText("Preco total:   " + soma);
                    ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                    listapeca.remove(i);
                    limpar();
//                    mostraPecas();
                } catch (NonexistentEntityException ex) {
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
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
    Action deletem = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "Pretende remover da lista este Mecanico?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            Mecanicomanutencao p = (Mecanicomanutencao) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            if (true) {
                p = new MecanicomanutencaoJpaController(emf).findMecanicomanutencao(p.getMecanicomanutencaoPK());
                int i = listamecanico.indexOf(p);
                try {
                    new MecanicomanutencaoJpaController(emf).destroy(p.getMecanicomanutencaoPK());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
                }
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                listamecanico.remove(i);
//                try {
//                    mostraMecanicos();
//                } catch (Exception ex) {
//                    Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
//            else {
//                JOptionPane.showMessageDialog(null, "Esta mecanico nao pode ser removido,\n "
//                        + "porque os seus produto ja foram retirados do Stock\n"
//                        + "Primeiro remova todos registos de Saida nesta serie", "Atencao", JOptionPane.WARNING_MESSAGE);
//            }
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
        Combomanute = new javax.swing.JComboBox();
        btAdd2 = new javax.swing.JButton();
        txpreco = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btdelimp = new javax.swing.JButton();
        btadimp = new javax.swing.JButton();
        btedimp = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableSaida = new javax.swing.JTable();
        txtcbarra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Comboproduto = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        cbtquant = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        txtQtVendida = new javax.swing.JTextField();
        cbtpreco = new javax.swing.JComboBox();
        btAdd = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        painelProduto2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableMecanico = new javax.swing.JTable();
        Combomecanico = new javax.swing.JComboBox();
        btAdd1 = new javax.swing.JButton();
        btadimp1 = new javax.swing.JButton();
        btedimp1 = new javax.swing.JButton();
        btdelimp1 = new javax.swing.JButton();
        labelTotal = new javax.swing.JLabel();

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
        labelHistory.setText("Manutenção");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(621, 621, 621)
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
                .addComponent(jScrollPane3)
                .addGap(20, 20, 20))
        );
        painelProdutoLayout.setVerticalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        Combomanute.setToolTipText("Seleccione ou pesquise produto");
        Combomanute.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Combomanute.setOpaque(false);
        Combomanute.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CombomanuteItemStateChanged(evt);
            }
        });

        btAdd2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAdd2.setForeground(new java.awt.Color(102, 0, 0));
        btAdd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/oneb.png"))); // NOI18N
        btAdd2.setText("Adicionar");
        btAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdd2ActionPerformed(evt);
            }
        });

        jLabel2.setText("MZN");

        btdelimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"))); // NOI18N
        btdelimp.setToolTipText("Apagar o Tipo seleccionado");
        btdelimp.setBorder(null);
        btdelimp.setBorderPainted(false);
        btdelimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelimpActionPerformed(evt);
            }
        });

        btadimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btadimp.setToolTipText("Adicionar Tipo de Manutencao");
        btadimp.setBorder(null);
        btadimp.setBorderPainted(false);
        btadimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btadimpActionPerformed(evt);
            }
        });

        btedimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        btedimp.setToolTipText("Editar o Tipo Seleccionado");
        btedimp.setBorder(null);
        btedimp.setBorderPainted(false);
        btedimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btedimpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btadimp, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btedimp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btdelimp)
                .addGap(18, 18, 18)
                .addComponent(Combomanute, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(txpreco, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(76, 76, 76)
                .addComponent(btAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(248, 248, 248))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txpreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(btAdd2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Combomanute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btadimp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btedimp)
                    .addComponent(btdelimp))
                .addGap(33, 33, 33)
                .addComponent(painelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jDesktopPane1.add(jPanel3);
        jPanel3.setBounds(-10, 0, 1290, 420);

        Emprestimo.addTab("Tipos de Manutencao", jDesktopPane1);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TableSaida.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableSaida.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableSaida.setModel(dTableModel2);
        jScrollPane4.setViewportView(TableSaida);

        txtcbarra.setToolTipText("codigo");
        txtcbarra.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtcbarraInputMethodTextChanged(evt);
            }
        });
        txtcbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcbarraKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Codigo de Barras");

        Comboproduto.setToolTipText("Seleccione ou pesquise produto");
        Comboproduto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Comboproduto.setOpaque(false);
        Comboproduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboprodutoItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Lot/Serie:");

        cbtquant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbtquant.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbtquantItemStateChanged(evt);
            }
        });
        cbtquant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtquantActionPerformed(evt);
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

        cbtpreco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbtpreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtprecoActionPerformed(evt);
            }
        });

        btAdd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAdd.setForeground(new java.awt.Color(102, 0, 0));
        btAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/oneb.png"))); // NOI18N
        btAdd.setText("Adicionar");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbtquant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(Comboproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbtpreco, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(86, 86, 86)
                        .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(313, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtcbarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbtquant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbtpreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Comboproduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
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
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Emprestimo.addTab("Pecas utilizadas", jPanel14);

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        painelProduto2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TableMecanico.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableMecanico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableMecanico.setModel(dTableModel3);
        TableMecanico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMecanicoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableMecanico);

        javax.swing.GroupLayout painelProduto2Layout = new javax.swing.GroupLayout(painelProduto2);
        painelProduto2.setLayout(painelProduto2Layout);
        painelProduto2Layout.setHorizontalGroup(
            painelProduto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProduto2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        painelProduto2Layout.setVerticalGroup(
            painelProduto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProduto2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addContainerGap())
        );

        Combomecanico.setToolTipText("Seleccione ou pesquise produto");
        Combomecanico.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Combomecanico.setOpaque(false);
        Combomecanico.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CombomecanicoItemStateChanged(evt);
            }
        });

        btAdd1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAdd1.setForeground(new java.awt.Color(102, 0, 0));
        btAdd1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/oneb.png"))); // NOI18N
        btAdd1.setText("Adicionar");
        btAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdd1ActionPerformed(evt);
            }
        });

        btadimp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btadimp1.setToolTipText("Adicionar novo Mecanico");
        btadimp1.setBorder(null);
        btadimp1.setBorderPainted(false);
        btadimp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btadimp1ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(painelProduto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btadimp1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btedimp1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btdelimp1)
                        .addGap(5, 5, 5)
                        .addComponent(Combomecanico, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(btAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Combomecanico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btadimp1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btedimp1)
                    .addComponent(btdelimp1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(painelProduto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );

        Emprestimo.addTab("Mecanicos", jPanel15);

        labelTotal.setText("Valor Total:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Emprestimo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(Emprestimo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void TableEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableEntradaMouseClicked

    }//GEN-LAST:event_TableEntradaMouseClicked

    private void TableMecanicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMecanicoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableMecanicoMouseClicked

    private void txtcbarraInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtcbarraInputMethodTextChanged

    }//GEN-LAST:event_txtcbarraInputMethodTextChanged

    private void txtcbarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcbarraKeyReleased
        // cbarra = txtcbarra.getText();
        System.out.println("A: " + txtcbarra.getText());
        if (txtcbarra.getText().trim().isEmpty()) {
            return;
        }
        Entrada e = new EntradaJpaController(emf).getEntradaCbarra(txtcbarra.getText());//entrada com valor
        if (e == null) {
            e = new EntradaJpaController(emf).getEntradaPCbarra(txtcbarra.getText());//entrada sem valor
        }
        if (e != null) {
            Comboproduto.setSelectedItem(e.getProduto());
            //txtcbarra.setSelectionStart(0);
            //txtcbarra.setSelectionStart(txtcbarra.getColumns());
            cbtquant.setSelectedItem(e);
        } else {
            Comboproduto.setSelectedIndex(-1);
            cbtquant.removeAllItems();
            cbtpreco.removeAllItems();
        }
        //  ||txtcbarra.getSelectionEnd()
        if (cbarra.equals("") && e != null) {//cbarra valor da barra
            try {
                System.out.println("sdfsf");
                int ip = showListaCompra(0);
                txtQtVendida.setText("1");//
                Produto p = (Produto) Comboproduto.getSelectedItem();
                List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);//problema nao tras entradas expiradas
                comboboxe(cbtquant, le);
                cbtquant.setSelectedIndex(ip);
            } catch (Exception ee) {
                System.out.println("erro: " + ee.getMessage());
            }
            txtcbarra.setText("");
        }
        txtcbarra.requestFocusInWindow();
        cbarra = txtcbarra.getText();
    }//GEN-LAST:event_txtcbarraKeyReleased

    private void ComboprodutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboprodutoItemStateChanged
        if (Comboproduto.getSelectedItem() == null) {
            return;
        }
        Produto p = (Produto) Comboproduto.getSelectedItem();
        //   tfcp.setText(p.getCodigo());//codigo do produto
        txtQtVendida.setText("1");
        List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);//problema nao tras entradas expiradas
        comboboxe(cbtquant, le);
    }//GEN-LAST:event_ComboprodutoItemStateChanged
    public void comboboxe(JComboBox jComboNomeCliet, List<Entrada> le) {
        jComboNomeCliet.removeAllItems();
        cbtpreco.removeAllItems();
        for (Entrada e : le) {
            e.setQitem(e.getQv());
            jComboNomeCliet.addItem(e);
        }
    }

    public void comboboxpreco(Entrada e) {
        cbtpreco.removeAllItems();
        String prec = "";
        if (e.getNa() != null) {
            prec = e.getNa() + " - ";
        }
        prec = prec + e.getQa() + "x" + e.getPreco();
        cbtpreco.addItem(prec);
        if (e.getPb() != null) {
            prec = "";
            if (e.getNb() != null) {
                prec = e.getNb() + " - ";
            }
            prec = prec + e.getPb() + "x" + e.getPb();
            cbtpreco.addItem(prec);
        }
        if (e.getPc() != null) {
            prec = "";
            if (e.getNc() != null) {
                prec = e.getNc() + " - ";
            }
            prec = prec + e.getPc() + "x" + e.getPc();
            cbtpreco.addItem(e);
        }

    }
    private void cbtquantItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbtquantItemStateChanged
        if (cbtquant.getSelectedItem() == null) {
            return;
        }
        Entrada e = (Entrada) cbtquant.getSelectedItem();
        comboboxpreco(e);
    }//GEN-LAST:event_cbtquantItemStateChanged

    private void cbtquantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtquantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbtquantActionPerformed

    private void txtQtVendidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtVendidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtVendidaActionPerformed

    private void cbtprecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtprecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbtprecoActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        try {
            int ip = showListaCompra(0);
            txtQtVendida.setText("1");//
//            Produto p = (Produto) Comboproduto.getSelectedItem();
//            List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);//problema nao tras entradas expiradas
          //  comboboxe(cbtquant, le);
          //  cbtquant.setSelectedIndex(ip);
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
        txtcbarra.setText("");
        cbarra = "";
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
            int qitem = 0;
            int id = ((Produto) Comboproduto.getSelectedItem()).getIdproduto();
            if (txtQtVendida.getText().trim().equals("") || txtQtVendida.getText().equals("0")) {
                JOptionPane.showMessageDialog(this, "Insira a quantidade", "Atencao", JOptionPane.WARNING_MESSAGE);
                txtQtVendida.requestFocusInWindow();//grabFocus();
                return 0;
            }
            List<Entrada> lsp = new ArrayList<>();
            int qtt = Integer.valueOf(txtQtVendida.getText());
            Produto pro = new ProdutoJpaController(emf).findProduto(id);
            Entrada en = (Entrada) cbtquant.getSelectedItem();
            System.out.println("en: " + en);
            icb = cbtquant.getSelectedIndex();
            int qtdvenda = en.getQv();//pro.getQtdvenda(); pro.getPrecovenda();
            float preco = 0;
            int ip = cbtpreco.getSelectedIndex();
            if (ip == 0) {
                preco = en.getPreco();
                qitem = en.getQa();
            } else if (ip == 1) {
                preco = en.getPb();
                qitem = en.getQb();
            } else if (ip == 2) {
                preco = en.getPc();
                qitem = en.getQc();
            }
            //System.out.println("preco: "+pro.getNome()+" "+preco);
            String produto = pro.getNome();
            if (pro.getQtdvenda() == 0) {
                JOptionPane.showMessageDialog(this, "Nao ha " + produto + " no balcao", "Atencao", JOptionPane.WARNING_MESSAGE);
                txtQtVendida.grabFocus();
                return 0;
            }
            if (qtt * qitem > pro.getQtdvenda()) {
                JOptionPane.showMessageDialog(this, "A Qtd. de " + produto + " disponivel no balcao e de  " + qtdvenda, "Atencao", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (qtt * qitem > qtdvenda) {
                JOptionPane.showMessageDialog(this, "Para esta Serie/Lote, de " + produto + " a Qtd.  disponivel no balcao e de  " + qtdvenda, "Atencao", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            Date d = new Date();
            if (en.getDataexpiracao() != null && en.getDataexpiracao().before(d)) {
                if (qtt > 0) {
                    JOptionPane.showMessageDialog(this, produto + "\n nesta Serie/Lote estao foram do prazo", "Atencao", JOptionPane.WARNING_MESSAGE);
                }
                return 0;
            }
            Entrada entra = new Entrada(en.getEntradaPK());
            entra.setProduto(pro);
            entra.setQv(qtt * qitem);
            Produto p = new Produto(pro.getIdproduto());
            p.setQtdvenda(qtt * qitem);
            Pecamanutencao pm = new Pecamanutencao(new PecamanutencaoPK(this.produ.getIdentrada(), new Date()));
            pm.setPreco(preco * qtt);
            pm.setDatae(en.getEntradaPK().getData());
            pm.setIdproduto(pro);
            pm.setQtd(qtt);
            pm.setQc(qtt * qitem);
            pm.setIva(en.getIdimposto().getPerc());
            pm.setEntradaviatura(produ);
            float valo = preco * qtt;
            float imp = new Double(Math.ceil((en.getIdimposto().getPerc().floatValue() / 100) * valo)).floatValue();
            pm.setTiva(imp);
            int respo = JOptionPane.showOptionDialog(null, "Pretende adicionar este Produto?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return -1;
            }
            EntityManager em = emf.createEntityManager();
            try {
                EntradaJpaController ec = new EntradaJpaController(emf);
                em.getTransaction().begin();

                int q = ec.editVenda(entra, em);
                if (q > 0) {
                    JOptionPane.showMessageDialog(this, entra.getProduto().getNome() + "\n"
                            + "A quantidade seleccionada e maior que a quantidade disponivel neste momento", "Atencao", JOptionPane.WARNING_MESSAGE);
                    em.clear();
                    em.getTransaction().commit();
                    return -1;
                }
                new ProdutoJpaController(emf).editVenda(p, em);
                new PecamanutencaoJpaController(emf).create(pm, em);
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Introduzido com Sucesso");
                soma = soma + pm.getPreco();
                labelTotal.setText("Preco total:   " + soma);
                listapeca.add(pm);
                mostraPecas();
                limpar();
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

    public void limpar() {
        txtQtVendida.setText("1");
        if (support != null) {
            support.uninstall();
            comboboxP(Comboproduto);
        }
    }

    private void CombomecanicoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CombomecanicoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CombomecanicoItemStateChanged

    private void btAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdd1ActionPerformed
        if (Combomecanico.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione um Mecanico\n"
                    + "", "Atencao", JOptionPane.WARNING_MESSAGE);
            Combomecanico.requestFocusInWindow();
            return;
        }
        Mecanico tm = (Mecanico) Combomecanico.getSelectedItem();
        for (Mecanicomanutencao m : listamecanico) {
            if (m.getMecanico().getIdmecanico().intValue() == tm.getIdmecanico().intValue()) {
                JOptionPane.showMessageDialog(this, "Este Mecanico ja foi introduzido na lista", "Atencao", JOptionPane.WARNING_MESSAGE);
                Combomecanico.requestFocusInWindow();
                return;
            }
        }
        Mecanicomanutencao m = new Mecanicomanutencao(new MecanicomanutencaoPK(tm.getIdmecanico(), this.produ.getIdentrada()));
        m.setMecanico(tm);
        m.setData(new Date());
        m.setEntradaviatura(produ);
      //  m.setUtilizador(new Vendedor(client));
        try {
            new MecanicomanutencaoJpaController(emf).create(m);
        } catch (Exception ex) {
            Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
        }
        listamecanico.add(m);
        try {
            mostraMecanicos();
        } catch (Exception ex) {
            Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAdd1ActionPerformed

    private void CombomanuteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CombomanuteItemStateChanged
        if (Combomanute.getSelectedItem() != null) {
            txpreco.setText(((Tipomanutencao) Combomanute.getSelectedItem()).getPreco() + "");
        }
    }//GEN-LAST:event_CombomanuteItemStateChanged

    private void btAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdd2ActionPerformed
        if (Combomanute.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione um tipo de manutencao\n"
                    + "", "Atencao", JOptionPane.WARNING_MESSAGE);
            Combomanute.requestFocusInWindow();
            return;
        }
        if (txpreco.getText().trim().equals("") || txpreco.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "Instroduza o preco", "Atencao", JOptionPane.WARNING_MESSAGE);
            txpreco.requestFocusInWindow();
            return;
        }
        Tipomanutencao tm = (Tipomanutencao) Combomanute.getSelectedItem();
        for (Manutencao m : listamanute) {
            if (m.getIdtipomanutencao().getIdtipomanutencao().intValue() == tm.getIdtipomanutencao().intValue()) {
                JOptionPane.showMessageDialog(this, "Este tipo de manutencao ja foi introduzido na lista", "Atencao", JOptionPane.WARNING_MESSAGE);
                Combomanute.requestFocusInWindow();
                return;
            }
        }
        Manutencao m = new Manutencao(new ManutencaoPK(new Date(), this.produ.getIdentrada()));
        m.setCustomanutencao(Float.parseFloat(txpreco.getText()));
        m.setDescricao(tm.getDesignacao());
        m.setEntradaviatura(produ);
        m.setUtilizador(new Vendedor(client));
        m.setIdtipomanutencao(tm);
        try {
            new ManutencaoJpaController(emf).create(m);
        } catch (Exception ex) {
            Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
        }
        soma = soma + m.getCustomanutencao();
        labelTotal.setText("Preco total:   " + soma);
        listamanute.add(m);
        try {
            mostramanute();
        } catch (Exception ex) {
            Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAdd2ActionPerformed

    private void btadimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btadimpActionPerformed
        FormTipoManute f = new FormTipoManute(new javax.swing.JFrame(), null, "Adicionar novo Tipo de Manutencao", true);
        f.setVisible(true);
        if (support3 != null && support3.isInstalled()) {
            support3.uninstall();
        }
        comboboxMa(Combomanute);
    }//GEN-LAST:event_btadimpActionPerformed

    private void btedimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btedimpActionPerformed
        FormTipoManute f = new FormTipoManute(new javax.swing.JFrame(), (Tipomanutencao) Combomanute.getSelectedItem(), "Editar o Tipo de Manutencao", true);
        f.setVisible(true);
        if (support3 != null && support3.isInstalled()) {
            support3.uninstall();
        }
        comboboxMa(Combomanute);
    }//GEN-LAST:event_btedimpActionPerformed

    private void btdelimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelimpActionPerformed
        Tipomanutencao i = (Tipomanutencao) Combomanute.getSelectedItem();
        //  if (txtIdP.getText().trim().equals("")) {
        if (new ManutencaoJpaController(emf).existeTipoManutencao(i) != 0) {
            JOptionPane.showMessageDialog(this, "Este Tipo de Manutencao esta em uso! Nao pode ser apagado", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
            // }
        } else {
            try {
                new TipomanutencaoJpaController(emf).destroy(i.getIdtipomanutencao());
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CadastroManute.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Apagado com Sucesso");
        }
        if (support3 != null && support3.isInstalled()) {
            support3.uninstall();
        }
        comboboxMa(Combomanute);
    }//GEN-LAST:event_btdelimpActionPerformed

    private void btadimp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btadimp1ActionPerformed
        FormMecanico f = new FormMecanico(new javax.swing.JFrame(), null, null, 0, "Adicionar novo Mecanico", true);
        f.setVisible(true);
        if (support2 != null && support2.isInstalled()) {
            support2.uninstall();
        }
        comboboxMe(Combomecanico);
    }//GEN-LAST:event_btadimp1ActionPerformed

    private void btedimp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btedimp1ActionPerformed
        if(Combomecanico.getSelectedItem()==null) return;
        Mecanico i = (Mecanico) Combomecanico.getSelectedItem();
        FormMecanico f = new FormMecanico(new javax.swing.JFrame(), i, null, 0, "Editar os dados do Mecanico", true);
        f.setVisible(true);
        if (support2 != null && support2.isInstalled()) {
            support2.uninstall();
        }
        comboboxMe(Combomecanico);
    }//GEN-LAST:event_btedimp1ActionPerformed

    private void btdelimp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelimp1ActionPerformed
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
    private javax.swing.JComboBox Combomanute;
    private javax.swing.JComboBox Combomecanico;
    private javax.swing.JComboBox Comboproduto;
    private javax.swing.JTabbedPane Emprestimo;
    private javax.swing.JTable TableEntrada;
    private javax.swing.JTable TableMecanico;
    private javax.swing.JTable TableSaida;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btAdd1;
    private javax.swing.JButton btAdd2;
    private javax.swing.JButton btadimp;
    private javax.swing.JButton btadimp1;
    private javax.swing.JButton btdelimp;
    private javax.swing.JButton btdelimp1;
    private javax.swing.JButton btedimp;
    private javax.swing.JButton btedimp1;
    private javax.swing.JComboBox cbtpreco;
    private javax.swing.JComboBox cbtquant;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JPanel painelProduto;
    private javax.swing.JPanel painelProduto2;
    private javax.swing.JTextField txpreco;
    private javax.swing.JTextField txtQtVendida;
    private javax.swing.JTextField txtcbarra;
    // End of variables declaration//GEN-END:variables

}
