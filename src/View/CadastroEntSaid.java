package View;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.EntradaJpaController;
import controller.FornecedorJpaController;
import controller.ProdutoJpaController;
import controller.SaidaJpaController;
import controller.exceptions.NonexistentEntityException;
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
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Fornecedor;
import modelo.Produto;
import modelo.Saida;
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
public final class CadastroEntSaid extends javax.swing.JDialog {

    JasperPrint jpPrint;
    JasperViewer jv;
    HashMap<String, Object> par = new HashMap<String, Object>();
    Map parametros = new HashMap<>();

    int t = 0;
    List<Entrada> listaE = new ArrayList<>();
    List<Saida> listaS = new ArrayList<>();
    static Produto produ;
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"id", "Serie/Lote Nr.", "Data Entrada", "Qtd. Armazem", "Qtd. Loja", "Preco", "Fornecedor", "Add", "Detalhes", "Editar", "Remover"});
    DefaultTableModel dTableModel2 = new DefaultTableModel(null, new String[]{"id", "Cod. Prod.", "Serie/Lote Nr.", "Preco", "Data Saida", "Tipo Saida", "Qtd", "Observacao", "Remover"});
    private int client = 0;

    @Override
    public void list() {
        super.list();
    }

    public CadastroEntSaid(Produto p, int c) throws Exception {
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
//        jdie.setDate(d);
//        jdfe.setDate(d);
        jdis.setDate(n);
        comboboxp(cbpro);
        comboboxp(cbpro1);
        comboboxf(cbfo);
        if (cbpro.getItemCount() > 0) {
            cbpro.setSelectedItem(p);
        }
        if (cbpro1.getItemCount() > 0) {
            cbpro1.setSelectedItem(p);
        }
        // jdfe.setDate(d);
        //  listaE = new EntradaJpaController(emf).getEntradaPeriodo(d, null, p, cbfo.getSelectedItem());
        listaE = new EntradaJpaController(emf).getEntFull(p);
        mostraEntrada();
        this.TableEntrada.setAutoCreateRowSorter(true);
        this.TableEntrada.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableEntrada.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableEntrada.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableEntrada.getColumnModel().getColumn(7).setMaxWidth(50);//setPreferredWidth(3);
        this.TableEntrada.getColumnModel().getColumn(8).setMaxWidth(70);//setPreferredWidth(3);
        this.TableEntrada.getColumnModel().getColumn(9).setMaxWidth(50);//setPreferredWidth(3);
        this.TableEntrada.getColumnModel().getColumn(10).setMaxWidth(70);//setPreferredWidth(3);
        this.TableEntrada.setRowHeight(30);

        Icon id = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_ver.png"));
        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
        Icon is = new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"));
        ButtonColumn bcs = new ButtonColumn(this.TableEntrada, addent, 7, is);
        ButtonColumn bcd = new ButtonColumn(this.TableEntrada, ver, 8, id);
        ButtonColumn bce = new ButtonColumn(this.TableEntrada, editar, 9, ie);
        ButtonColumn bcr = new ButtonColumn(this.TableEntrada, delete, 10, ir);

        listaS = new SaidaJpaController(emf).getSaidaPeriodo(d, null, p);
        mostraSaida();
        this.TableSaida.setAutoCreateRowSorter(true);
        this.TableSaida.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableSaida.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableSaida.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableSaida.getColumnModel().getColumn(7).setMaxWidth(50);//setPreferredWidth(3);
        this.TableSaida.setRowHeight(30);
        // Icon ies = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        // Icon irs = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
//        ButtonColumn bces = new ButtonColumn(this.TableSaida, editars, 7, ie);
        ButtonColumn bcrs = new ButtonColumn(this.TableSaida, deletes, 8, ir);

    }

    public void comboboxp(JComboBox c) {
        c.removeAllItems();
        //c.addItem("Todos");
        List<Produto> lv = new ProdutoJpaController(emf).getProdAsc();
        if (lv.isEmpty()) {
            return;
        }
        Produto[] elements = lv.toArray(new Produto[]{});
        AutoCompleteSupport support = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));
    }

    public void comboboxf(JComboBox c) {
        c.removeAllItems();
        c.addItem("Todos Fornec.");
        List<Fornecedor> lv = new FornecedorJpaController(emf).findFornecedorEntities();
        if (lv.isEmpty()) {
            return;
        }
        for (Fornecedor v : lv) {
            c.addItem(v);
        }
    }

    public void mostraEntrada() throws Exception {

        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listaE.size(); i++) {

            dTableModel.addRow(linha);
            dTableModel.setValueAt(listaE.get(i), i, 0);
            dTableModel.setValueAt(listaE.get(i).getSerie(), i, 1);
            dTableModel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaE.get(i).getEntradaPK().getData()), i, 2);
            dTableModel.setValueAt(listaE.get(i).getQtd() - listaE.get(i).getQs(), i, 3);
            dTableModel.setValueAt(listaE.get(i).getQv(), i, 4);
            dTableModel.setValueAt(listaE.get(i).getPreco(), i, 5);
            dTableModel.setValueAt(listaE.get(i).getIdfornecedor().getNome(), i, 6);
        }
    }

    public void mostraSaida() throws Exception {

        while (dTableModel2.getRowCount() > 0) {
            dTableModel2.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listaS.size(); i++) {
            Entrada entr = new EntradaJpaController(emf).getEntData(listaS.get(i).getDatae());
            dTableModel2.addRow(linha);
            dTableModel2.setValueAt(listaS.get(i), i, 0);
            dTableModel2.setValueAt(listaS.get(i).getSaidaPK().getIdproduto(), i, 1);
            dTableModel2.setValueAt(entr.getSerie(), i, 2);//(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaS.get(i).getDatae()), i, 2);
            dTableModel2.setValueAt(entr.getPreco(), i, 3);
            dTableModel2.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaS.get(i).getSaidaPK().getData()), i, 4);
            dTableModel2.setValueAt(listaS.get(i).getTiposaida(), i, 5);
            dTableModel2.setValueAt(listaS.get(i).getQtd(), i, 6);
            if (listaS.get(i).getObs() != null) {
                dTableModel2.setValueAt(listaS.get(i).getObs(), i, 7);
            }
        }
    }
    Action addent = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Entrada p = (Entrada) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormAddEntrada f = new FormAddEntrada(new javax.swing.JFrame(), p, client, true);
            f.setVisible(true);
            p = new EntradaJpaController(emf).findEntrada(p.getEntradaPK());
            int i = listaE.indexOf(p);
            listaE.set(i, p);
            try {
                mostraEntrada();
            } catch (Exception ex) {
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
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
            int respo = JOptionPane.showOptionDialog(null, "{Pretende remover esta Entrada?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            Entrada p = (Entrada) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            Boolean b = new SaidaJpaController(emf).getSaidaM(p.getEntradaPK().getData());
            if (b) {
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();/////////////////////////////
                try {
                    p = new EntradaJpaController(emf).findEntradaS(p.getEntradaPK(), em);
                    Produto pr = new ProdutoJpaController(emf).findProdutoS(p.getEntradaPK().getIdproduto(), em);
                    pr.setQtstock(pr.getQtstock() - p.getQtd());
                    produ.setQtstock(pr.getQtstock());

                    new ProdutoJpaController(emf).edit(pr, pr, em);

                    new EntradaJpaController(emf).destroy(p.getEntradaPK(), em);
                    List<Entrada> le = new EntradaJpaController(emf).getEntDataref(p.getEntradaPK().getData());
                    for (Entrada ee : le) {
                        new EntradaJpaController(emf).destroy(ee.getEntradaPK(), em);
                    }
                    em.getTransaction().commit();
                } catch (NonexistentEntityException ex) {
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (em != null) {
                        em.close();
                    }
                }
                //  labelSucesso.setText("Produto removido com Sucesso");
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                listaE = new EntradaJpaController(emf).getEntradaPeriodo(jdie.getDate(), null, cbpro.getSelectedItem(), cbfo.getSelectedItem());
                try {
                    mostraEntrada();
                } catch (Exception ex) {
                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Esta Serie/Lote nao pode ser removida,\n "
                        + "porque os seus produto ja foram retirados do Armazem\n"
                        + "Primeiro remova todos registos de Saida nesta serie", "Atencao", JOptionPane.WARNING_MESSAGE);
            }
        }
    };
    Action deletes = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "{Pretende remover esta Saida?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            Saida p = (Saida) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();/////////////////////////////
            try {
                p = new SaidaJpaController(emf).findSaidaS(p.getSaidaPK(), em);
                Entrada ent = new EntradaJpaController(emf).findEntradaS(new EntradaPK(p.getProduto().getIdproduto(),
                        p.getDatae()), em);
                Produto pr = new ProdutoJpaController(emf).findProdutoS(p.getSaidaPK().getIdproduto(), em);
                //Entrada ent = new EntradaJpaController(emf).getEntrada(p.getDatae());

                if ((p.getTiposaida().getIdtiposaida() == 3) || (p.getTiposaida().getIdtiposaida() == 1 && Objects.equals(p.getQpac(), pr.getQtdvenda()))) {
                    pr.setQtstock(pr.getQtstock() + p.getQtd());
                    ent.setQs(ent.getQs() - p.getQtd());
                    if (p.getObs() == null) {
                        pr.setQtdvenda(pr.getQtdvenda()- p.getQtd());
                        ent.setQv(ent.getQv() - p.getQtd());
                    } else {
                        ent.setQd(ent.getQd() - p.getQtd());
                    }
                    produ.setQtdvenda(pr.getQtdvenda());
                    produ.setQtstock(pr.getQtstock());

                    new EntradaJpaController(emf).edit(ent, ent, em);
                    new ProdutoJpaController(emf).edit(pr, pr, em);
                    new SaidaJpaController(emf).destroy(p.getSaidaPK(), em);

                    //  labelSucesso.setText("Produto removido com Sucesso");
                    ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                    listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());

                    mostraSaida();

                }
                if (p.getTiposaida().getIdtiposaida() != 4 && p.getTiposaida().getIdtiposaida() != 2) {
                    em.clear();
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, "Esta Saida nao pode ser removida,\n "
                            + "porque o seu produto ja foi vendido", "Atencao", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if ((p.getTiposaida().getIdtiposaida() == 4) || (p.getTiposaida().getIdtiposaida() == 2 && Objects.equals(p.getQstock(), pr.getQtstock()))) {
                    pr.setQtdvenda(pr.getQtdvenda()+ p.getQtd());
                    ent.setQv(ent.getQv() + p.getQtd());
                    if (p.getObs() == null) {
                        pr.setQtstock(pr.getQtstock() - p.getQtd());
                        ent.setQs(ent.getQs() + p.getQtd());
                    } else {
                        ent.setQd(ent.getQd() - p.getQtd());
                    }
                    produ.setQtdvenda(pr.getQtdvenda());
                    produ.setQtstock(pr.getQtstock());

                    new EntradaJpaController(emf).edit(ent, ent, em);
                    new ProdutoJpaController(emf).edit(pr, pr, em);
                    new SaidaJpaController(emf).destroy(p.getSaidaPK(), em);
                    //  labelSucesso.setText("Produto removido com Sucesso");
                    ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                    listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());

                    mostraSaida();

                } else {
                    em.clear();
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, "Esta Saida nao pode ser removida,\n "
                            + "porque o seu produto ja foi vendido", "Atencao", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                em.getTransaction().commit();
            } catch (NonexistentEntityException ex) {
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
    };
    Action editar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Entrada p = (Entrada) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            p = new EntradaJpaController(emf).findEntrada(p.getEntradaPK());//removeRow(modelRow);
            Produto pr = new ProdutoJpaController(emf).findProduto(p.getEntradaPK().getIdproduto());
            Boolean b = new SaidaJpaController(emf).getSaidaM(p.getEntradaPK().getData());
            if (b) {
                FormEntrada f = new FormEntrada(new javax.swing.JFrame(), pr, p, "Editar Entrada", client, true);
                f.setVisible(true);
                listaE = new EntradaJpaController(emf).getEntFull((Produto) cbpro.getSelectedItem());//getEntradaPeriodo(jdie.getDate(), null, cbpro.getSelectedItem(), cbfo.getSelectedItem());
                try {
                    mostraEntrada();
                } catch (Exception ex) {
                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
//                JOptionPane.showMessageDialog(null, "Esta entrada nao pode ser editada,\n "
//                        + "porque os seus produto ja foram retirados do Stock", "Atencao", JOptionPane.WARNING_MESSAGE);
                FormEntrada f = new FormEntrada(new javax.swing.JFrame(), pr, p, "Editar Entrada", client, true);
                f.setVisible(true);
                listaE = new EntradaJpaController(emf).getEntradaPeriodo(jdie.getDate(), null, cbpro.getSelectedItem(), cbfo.getSelectedItem());
                try {
                    mostraEntrada();
                } catch (Exception ex) {
                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    Action editars = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Saida p = (Saida) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            p = new SaidaJpaController(emf).findSaida(p.getSaidaPK());
            Produto pr = new ProdutoJpaController(emf).findProduto(p.getSaidaPK().getIdproduto());
            // System.out.println("qpac, qtdvenda"+p.getQpac()+" "+pr.getQtdvenda());
            if (Objects.equals(p.getQpac(), pr.getQtdvenda())) {
                FormSaida f = new FormSaida(new javax.swing.JFrame(), pr, p, "Editar Saida", client, true);
                f.setVisible(true);
//                listaE = new EntradaJpaController(emf).getEntradaPeriodo(jdis.getDate(), null, pr,cbfo.getSelectedItem());
//                try {
//                    mostraEntrada();
//                } catch (Exception ex) {
//                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
//                }
                listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());
                try {
                    mostraSaida();
                } catch (Exception ex) {
                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
//                JOptionPane.showMessageDialog(null, "Esta Saida nao pode ser editada,\n "
//                        + "porque o seu produto ja foi vendido", "Atencao", JOptionPane.WARNING_MESSAGE);
                FormSaida f = new FormSaida(new javax.swing.JFrame(), pr, p, "Editar Saida", client, true);
                f.setVisible(true);
//                listaE = new EntradaJpaController(emf).getEntradaPeriodo(jdis.getDate(), null, pr,cbfo.getSelectedItem());
//                try {
//                    mostraEntrada();
//                } catch (Exception ex) {
//                    Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
//                }
                listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());
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
        jPanel10 = new javax.swing.JPanel();
        jBpesquisarEnt = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jdfe = new com.toedter.calendar.JDateChooser();
        jdie = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbpro = new javax.swing.JComboBox();
        cbfo = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        btEnt = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        painelProduto1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableSaida = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jBpesquisarSaid = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jdfs = new com.toedter.calendar.JDateChooser();
        jdis = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbpro1 = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        btSaid = new javax.swing.JButton();

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
        labelHistory.setText("Stock");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        painelProdutoLayout.setVerticalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBpesquisarEnt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBpesquisarEnt.setText("Pesquisar Entradas");
        jBpesquisarEnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBpesquisarEntActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(102, 204, 0));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton5.setText("Exportar PDF");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jdfe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdfeKeyReleased(evt);
            }
        });

        jdie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdieKeyReleased(evt);
            }
        });

        jLabel7.setText("Apartir de:");

        jLabel8.setText("ate");

        cbpro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbpro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbproItemStateChanged(evt);
            }
        });

        cbfo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jdie, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdfe, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBpesquisarEnt, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbpro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(cbfo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(26, 26, 26))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBpesquisarEnt)
                        .addComponent(jButton5)
                        .addComponent(cbpro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jdie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btEnt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btEnt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btEnt.setText("Nova Serie/Lote");
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
                .addGap(23, 23, 23)
                .addComponent(btEnt, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1062, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btEnt)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jDesktopPane1.add(jPanel3);
        jPanel3.setBounds(0, 0, 1280, 420);

        Emprestimo.addTab("Entrada no Armazem", jDesktopPane1);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        painelProduto1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TableSaida.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableSaida.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableSaida.setModel(dTableModel2);
        TableSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSaidaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableSaida);

        javax.swing.GroupLayout painelProduto1Layout = new javax.swing.GroupLayout(painelProduto1);
        painelProduto1.setLayout(painelProduto1Layout);
        painelProduto1Layout.setHorizontalGroup(
            painelProduto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProduto1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        painelProduto1Layout.setVerticalGroup(
            painelProduto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProduto1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBpesquisarSaid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBpesquisarSaid.setText("Pesquisar Saidas");
        jBpesquisarSaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBpesquisarSaidActionPerformed(evt);
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

        jdfs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdfsKeyReleased(evt);
            }
        });

        jdis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdisKeyReleased(evt);
            }
        });

        jLabel1.setText("Apartir de:");

        jLabel2.setText("ate");

        cbpro1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbpro1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbpro1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jdis, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdfs, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBpesquisarSaid, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(cbpro1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdfs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBpesquisarSaid)
                        .addComponent(jButton2)
                        .addComponent(cbpro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(jdis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelProduto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelProduto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btSaid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btSaid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btSaid.setText("Nova Saida");
        btSaid.setToolTipText("");
        btSaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaidActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btSaid, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(btSaid)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Emprestimo.addTab("Saida do Armazem / Loja", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Emprestimo)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Emprestimo))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void analisarTab() throws Exception {
        if (Emprestimo.getSelectedIndex() == 0) {
            listaE.clear();
            listaE = new EntradaJpaController(emf).getEntFull((Produto) cbpro.getSelectedItem());//.getEntradaPeriodo(jdie.getDate(), jdfe.getDate(), cbpro.getSelectedItem(), cbfo.getSelectedItem());
            mostraEntrada();
        } else {
            // this.setTitle("Emprestimo de Prouto");
//        Date d = new Date();
//        Calendar cal = new GregorianCalendar();
//        cal.setTime(d);
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        Date n = cal.getTime();
//        jdis.setDate(d);
//        jdfs.setDate(d);
            listaS.clear();
            listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());
            mostraSaida();

        }

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

    private void btEntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntActionPerformed
//        int respo = JOptionPane.showOptionDialog(null, "Antes de Efectuar uma entrada, e necessario parar com as vendas\n"
//                + "Caso contrario os dados serao distorcidos?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//                null, new String[]{"CONTINUAR", "NÃO"}, null);
//        if (respo == 1) {
//            return;
//        }
        FormEntrada f = new FormEntrada(new javax.swing.JFrame(), produ, null, "Registar Nova Entrada", client, true);
        f.setVisible(true);
        listaE = new EntradaJpaController(emf).getEntFull((Produto) cbpro.getSelectedItem());//getEntradaPeriodo(jdie.getDate(), null, cbpro.getSelectedItem(), cbfo.getSelectedItem());
        try {
            mostraEntrada();
        } catch (Exception ex) {
            Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btEntActionPerformed

    private void TableSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableSaidaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableSaidaMouseClicked

    private void btSaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaidActionPerformed
//        int respo = JOptionPane.showOptionDialog(null, "Antes de Efectuar uma Saida, e necessario parar com as vendas\n"
//                + "Caso contrario os dados serao distorcidos?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//                null, new String[]{"CONTINUAR", "NÃO"}, null);
//        if (respo == 1) {
//            return;
//        }
        String[] opc = new String[]{"Do Armazem", "Da loja", "Cancelar"};
        int resp = JOptionPane.showOptionDialog(null, "A saida sera feita apartir", "?", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opc, opc[0]);
        if (resp == 2) {
            return;
        }
        if (resp == 0) {
            Produto pr = new ProdutoJpaController(emf).findProduto(produ.getIdproduto());
            FormSaida f = new FormSaida(new javax.swing.JFrame(), pr, null, "Registar Saida do Armazem", client, true);
            f.setVisible(true);
            listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());
            try {
                mostraSaida();
            } catch (Exception ex) {
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (resp == 1) {
            Produto pr = new ProdutoJpaController(emf).findProduto(produ.getIdproduto());
            FormSaidaBalcao f = new FormSaidaBalcao(new javax.swing.JFrame(), pr, null, "Registar Saida da Loja", client, true);
            f.setVisible(true);
            listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), null, (Produto) cbpro1.getSelectedItem());
            try {
                mostraSaida();
            } catch (Exception ex) {
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btSaidActionPerformed

    private void jBpesquisarSaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBpesquisarSaidActionPerformed
        try {
            listaS.clear();
            listaS = new SaidaJpaController(emf).getSaidaPeriodo(jdis.getDate(), jdfs.getDate(), (Produto) cbpro1.getSelectedItem());
            mostraSaida();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBpesquisarSaidActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();
        parametros.put("di", jdie.getDate());
        parametros.put("df", jdfe.getDate());
        parametros.put("img", pl);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaS);
        // String path = "/relatorios/recibov.jasper";
        try {
            //String path = "C:\\Users\\Ussimane\\Desktop\\GestaoLoja\\src\\relatorios\\proproforma.jasper";
            String path = new File("relatorios/saidastock.jasper").getAbsolutePath();
            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Saidas do Armazem", true);
            viewer.setSize(1024, 768);
            viewer.setLocationRelativeTo(null);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jdfsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdfsKeyReleased
        //        try {
        //            mostraVenda(new VendaJpaController(emf).findVendaEntities(), 0);
        //        } catch (Exception e) {
        //        }
    }//GEN-LAST:event_jdfsKeyReleased

    private void jdisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdisKeyReleased
        //        try {
        //            mostraVenda(new VendaJpaController(emf).findVendaEntities(), 0);
        //        } catch (Exception e) {
        //        }
    }//GEN-LAST:event_jdisKeyReleased

    private void jBpesquisarEntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBpesquisarEntActionPerformed
        try {
            listaE.clear();
            listaE = new EntradaJpaController(emf).getEntradaPeriodo(jdie.getDate(), jdfe.getDate(), cbpro.getSelectedItem(), cbfo.getSelectedItem());
            mostraEntrada();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBpesquisarEntActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();
        parametros.put("di", jdie.getDate());
        parametros.put("df", jdfe.getDate());
        parametros.put("img", pl);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaE);
        // String path = "/relatorios/recibov.jasper";
        try {
            //String path = "C:\\Users\\Ussimane\\Desktop\\GestaoLoja\\src\\relatorios\\proproforma.jasper";
            String path = new File("relatorios/entradstock.jasper").getAbsolutePath();
            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Entradas no Armazem", true);
            viewer.setSize(1024, 768);
            viewer.setLocationRelativeTo(null);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jdfeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdfeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdfeKeyReleased

    private void jdieKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdieKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdieKeyReleased

    private void cbpro1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbpro1ItemStateChanged
        if (cbpro1.getItemCount() > 0 && cbpro1.getSelectedIndex() == -1) {
            cbpro1.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbpro1ItemStateChanged

    private void cbproItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbproItemStateChanged
        if (cbpro.getSelectedItem() != null) {
            listaE = new EntradaJpaController(emf).getEntFull((Produto) cbpro.getSelectedItem());
            try {
                mostraEntrada();
            } catch (Exception ex) {
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cbproItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Emprestimo;
    private javax.swing.JTable TableEntrada;
    private javax.swing.JTable TableSaida;
    private javax.swing.JButton btEnt;
    private javax.swing.JButton btSaid;
    private javax.swing.JComboBox cbfo;
    private javax.swing.JComboBox cbpro;
    private javax.swing.JComboBox cbpro1;
    private javax.swing.JButton jBpesquisarEnt;
    private javax.swing.JButton jBpesquisarSaid;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JDateChooser jdfe;
    private com.toedter.calendar.JDateChooser jdfs;
    private com.toedter.calendar.JDateChooser jdie;
    private com.toedter.calendar.JDateChooser jdis;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JPanel painelProduto;
    private javax.swing.JPanel painelProduto1;
    // End of variables declaration//GEN-END:variables

}
