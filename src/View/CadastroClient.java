package View;

import controller.ClienteJpaController;
import controller.EmpresaJpaController;
import controller.EmprestimoJpaController;
import controller.ProdutoJpaController;
import controller.SeriefacturaJpaController;
import controller.VendaJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Font;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import metodos.ButtonColumn;
import metodos.ValidarFloat;
import metodos.ValidarInteiro;
import modelo.Cliente;
import modelo.Empresa;
import modelo.Emprestimo;
import modelo.Produto;
import modelo.Seriefactura;
import modelo.Venda;
import modelo.Vendedor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 *
 * @author rjose
 */
public final class CadastroClient extends javax.swing.JDialog {

    int t = 0;

    JasperPrint jpPrint;
    JasperViewer jv;
    HashMap<String, Object> par = new HashMap<String, Object>();
    Map parametros = new HashMap<>();
    List<Cliente> tipoProd = new ArrayList<>();
    List<Empresa> lempresa = new ArrayList<>();
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"id", "Codigo", "Nome", "Endereco", "Empresa", "Tel", "Email", "Activo", "Editar", "Remover"});
    DefaultTableModel dTableModel2 = new DefaultTableModel(null, new String[]{"id", "Codigo", "Nome", "Endereco", "Tel", "Email", "Desconto(%)", "Tipo Venda", "Activo", "Editar", "Remover"});

    @Override
    public void list() {
        super.list();
    }

    public CadastroClient() throws Exception {
        initComponents();
        this.setLocation(30, 0);
        // this.setResizable(false);
        this.setTitle("Cadastro de Cliente");
        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
        // analisarTab();
        loadListShow(new ClienteJpaController(emf).findClienteEntities(), "");
        this.TableProduto.setAutoCreateRowSorter(true);
        this.TableProduto.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableProduto.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableProduto.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableProduto.getColumnModel().getColumn(8).setMaxWidth(50);//setPreferredWidth(3);
        this.TableProduto.getColumnModel().getColumn(9).setMaxWidth(70);//setPreferredWidth(3);
        this.TableProduto.setRowHeight(30);
        loadListShowe(new EmpresaJpaController(emf).findEmpresaEntities(), "");
        this.TableEmpresa.setAutoCreateRowSorter(true);
        this.TableEmpresa.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableEmpresa.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableEmpresa.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableEmpresa.getColumnModel().getColumn(9).setMaxWidth(50);//setPreferredWidth(3);
        this.TableEmpresa.getColumnModel().getColumn(10).setMaxWidth(70);//setPreferredWidth(3);
        this.TableEmpresa.setRowHeight(30);
        ButtonColumn bcee = new ButtonColumn(this.TableEmpresa, editare, 9, ie);
        ButtonColumn bcre = new ButtonColumn(this.TableEmpresa, deletee, 10, ir);
        ButtonColumn bce = new ButtonColumn(this.TableProduto, editar, 8, ie);
        ButtonColumn bcr = new ButtonColumn(this.TableProduto, delete, 9, ir);
    }

    public void loadListShow(List<Cliente> lista, String nome) {
        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }
        if (!nome.isEmpty()) {
            lista = new ClienteJpaController(emf).getClienteLike(nome);//new Consultas().formarProduto("%" + nome + "%");
        }
        String[] linha = new String[]{null, null, null, null, null, null, null,null, null, null};
        for (int i = 0; i < lista.size(); i++) {
            dTableModel.addRow(linha);
            dTableModel.setValueAt(lista.get(i), i, 0);
            dTableModel.setValueAt(lista.get(i).getCodigo(), i, 1);
            dTableModel.setValueAt(lista.get(i).getNome(), i, 2);
            dTableModel.setValueAt(lista.get(i).getEndereco(), i, 3);
            dTableModel.setValueAt(lista.get(i).getIdempresa().getNome(), i, 4);
            dTableModel.setValueAt(lista.get(i).getCell(), i, 5);
            dTableModel.setValueAt(lista.get(i).getEmail(), i, 6);
            if (lista.get(i).getEstado()) {
                dTableModel.setValueAt("Sim", i, 7);
            } else {
                dTableModel.setValueAt("Não", i, 7);
            }
        }
        // JTable table = new JTable(dTableModel);

    }

    public void loadListShowe(List<Empresa> lista, String nome) {
        while (dTableModel2.getRowCount() > 0) {
            dTableModel2.removeRow(0);
        }
        if (!nome.isEmpty()) {
            lista = new EmpresaJpaController(emf).getEmpresaLike(nome);//new Consultas().formarProduto("%" + nome + "%");
        }
        String[] linha = new String[]{null, null, null, null, null, null, null,null, null, null};
        for (int i = 0; i < lista.size(); i++) {
            dTableModel2.addRow(linha);
            dTableModel2.setValueAt(lista.get(i), i, 0);
            dTableModel2.setValueAt(lista.get(i).getCodigo(), i, 1);
            dTableModel2.setValueAt(lista.get(i).getNome(), i, 2);
            dTableModel2.setValueAt(lista.get(i).getEndereco(), i, 3);
            dTableModel2.setValueAt(lista.get(i).getTelefone(), i, 4);
            dTableModel2.setValueAt(lista.get(i).getEmail(), i, 5);
            dTableModel2.setValueAt(lista.get(i).getDesconto(), i, 6);
            int tv = lista.get(i).getTipovend();
            if (tv == 0) {
                dTableModel2.setValueAt("Por Dinheiro", i, 7);
            }
            if (tv == 1) {
                dTableModel2.setValueAt("Por Credito", i, 7);
            }
            if (tv == 2) {
                dTableModel2.setValueAt("Dinheiro/Credito", i, 7);
            }
            if (lista.get(i).getEstado()) {
                dTableModel2.setValueAt("Sim", i, 8);
            } else {
                dTableModel2.setValueAt("Não", i, 8);
            }
        }
        // JTable table = new JTable(dTableModel);

    }
    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int resp = JOptionPane.showOptionDialog(null, "{Pretende remover este Cliente?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (resp == 1 || resp == -1) {
                return;
            }
            Cliente p = (Cliente) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            Boolean b = new VendaJpaController(emf).getExistC(p);
            if (b) {
                try {
                    System.out.println(p.getIdcliente() + "");
                    new ClienteJpaController(emf).destroy(p.getIdcliente());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CadastroClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                //  labelSucesso.setText("Produto removido com Sucesso");
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                loadListShow(new ClienteJpaController(emf).findClienteEntities(), "");
            } else {
                JOptionPane.showMessageDialog(null, "Este Cliente nao pode ser removido,\n "
                        + "porque faz parte do historial de vendas efectuadas", "Atencao", JOptionPane.WARNING_MESSAGE);
            }
        }
    };
    Action deletee = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int resp = JOptionPane.showOptionDialog(null, "{Pretende remover esta Empresa?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (resp == 1 || resp == -1) {
                return;
            }
            Empresa p = (Empresa) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            Boolean b = new ClienteJpaController(emf).getExistE(p);
            if (b) {
                System.out.println(p.getIdempresa() + "");
                try {
                    try {
                        new EmpresaJpaController(emf).destroy(p.getIdempresa());
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(CadastroClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(CadastroClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                //  labelSucesso.setText("Produto removido com Sucesso");
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                loadListShowe(new EmpresaJpaController(emf).findEmpresaEntities(), "");
            } else {
                JOptionPane.showMessageDialog(null, "Esta Empresa nao pode ser removido,\n "
                        + "porque possui os seu clientes. Primeiro apague os seus clientes", "Atencao", JOptionPane.WARNING_MESSAGE);
            }
        }
    };
    Action editar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Cliente p = (Cliente) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormCliente f = new FormCliente(new javax.swing.JFrame(), p, table.getModel(),modelRow, "Editar Cliente", true);
            f.setVisible(true);
           // loadListShow(new ClienteJpaController(emf).findClienteEntities(), "");
        }
    };

    Action editare = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Empresa p = (Empresa) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormEmpresa f = new FormEmpresa(new javax.swing.JFrame(), p,table.getModel(),modelRow, "Editar Empresa", true);
            f.setVisible(true);
            loadListShow(new ClienteJpaController(emf).findClienteEntities(), "");
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
        jLabel9 = new javax.swing.JLabel();
        txtCampoPesq = new javax.swing.JTextField();
        painelProduto = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProduto = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btSalvarProd = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCampoPesq1 = new javax.swing.JTextField();
        painelProduto1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableEmpresa = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btAddEmp = new javax.swing.JButton();

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
        labelHistory.setText("Clientes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelHistory)
                .addGap(585, 585, 585))
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

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 0, 153));
        jLabel9.setText("Pesquise por codigo ou nome");
        jLabel9.setToolTipText("");

        txtCampoPesq.setToolTipText("");
        txtCampoPesq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtCampoPesqMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCampoPesqMouseReleased(evt);
            }
        });
        txtCampoPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCampoPesqKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCampoPesqKeyReleased(evt);
            }
        });

        painelProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TableProduto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableProduto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableProduto.setModel(dTableModel);
        TableProduto.setToolTipText("clientes");
        TableProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProdutoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProduto);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btSalvarProd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btSalvarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btSalvarProd.setText("Adicionar");
        btSalvarProd.setToolTipText("");
        btSalvarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btSalvarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btSalvarProd)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout painelProdutoLayout = new javax.swing.GroupLayout(painelProduto);
        painelProduto.setLayout(painelProdutoLayout);
        painelProdutoLayout.setHorizontalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1127, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        painelProdutoLayout.setVerticalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton2.setBackground(new java.awt.Color(102, 204, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton2.setText("Exportar PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(painelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jDesktopPane1.add(jPanel3);
        jPanel3.setBounds(-30, 0, 1290, 430);

        Emprestimo.addTab("Clientes", jDesktopPane1);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 0, 153));
        jLabel10.setText("Pesquise por codigo ou nome");
        jLabel10.setToolTipText("");

        txtCampoPesq1.setToolTipText("");
        txtCampoPesq1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtCampoPesq1MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCampoPesq1MouseReleased(evt);
            }
        });
        txtCampoPesq1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCampoPesq1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCampoPesq1KeyReleased(evt);
            }
        });

        painelProduto1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TableEmpresa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableEmpresa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableEmpresa.setModel(dTableModel2);
        TableEmpresa.setToolTipText("empresa");
        TableEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableEmpresaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableEmpresa);

        javax.swing.GroupLayout painelProduto1Layout = new javax.swing.GroupLayout(painelProduto1);
        painelProduto1.setLayout(painelProduto1Layout);
        painelProduto1Layout.setHorizontalGroup(
            painelProduto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProduto1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        painelProduto1Layout.setVerticalGroup(
            painelProduto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProduto1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(txtCampoPesq1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(painelProduto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCampoPesq1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(painelProduto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btAddEmp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btAddEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btAddEmp.setText("Adicionar");
        btAddEmp.setToolTipText("");
        btAddEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddEmpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btAddEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(btAddEmp)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        Emprestimo.addTab("Empresas/Organizacoes", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Emprestimo))
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
            this.setTitle("Nova Venda");
        } else {

            this.setTitle("Emprestimo de Prouto");

            Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
            Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
            loadListShowe(new EmpresaJpaController(emf).findEmpresaEntities(), "");
            this.TableEmpresa.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
            this.TableEmpresa.getColumnModel().getColumn(0).setMaxWidth(0);
            this.TableEmpresa.getColumnModel().getColumn(0).setPreferredWidth(0);
            this.TableEmpresa.getColumnModel().getColumn(8).setMaxWidth(50);//setPreferredWidth(3);
            this.TableEmpresa.getColumnModel().getColumn(9).setMaxWidth(70);//setPreferredWidth(3);
            this.TableEmpresa.setRowHeight(30);
            ButtonColumn bcee = new ButtonColumn(this.TableEmpresa, editare, 9, ie);
            ButtonColumn bcre = new ButtonColumn(this.TableEmpresa, deletee, 10, ir);
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

    private void txtCampoPesqMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseExited

    }//GEN-LAST:event_txtCampoPesqMouseExited

    private void txtCampoPesqMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseReleased

    }//GEN-LAST:event_txtCampoPesqMouseReleased

    private void txtCampoPesqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyPressed

    }//GEN-LAST:event_txtCampoPesqKeyPressed

    private void txtCampoPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyReleased
        loadListShow(tipoProd, txtCampoPesq.getText());
        if (txtCampoPesq.getText().trim().equals("")) {
            loadListShow(new ClienteJpaController(emf).findClienteEntities(), "");
        }
    }//GEN-LAST:event_txtCampoPesqKeyReleased

    public int getLimpaVenda() {
        return 1;
    }

    private void TableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProdutoMouseClicked

    }//GEN-LAST:event_TableProdutoMouseClicked

    private void btSalvarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarProdActionPerformed
        FormCliente f = new FormCliente(new javax.swing.JFrame(), null,null,-1 ,"Adicionar Cliente", true);
        f.setVisible(true);
        loadListShow(new ClienteJpaController(emf).findClienteEntities(), "");
    }//GEN-LAST:event_btSalvarProdActionPerformed

    private void txtCampoPesq1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesq1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCampoPesq1MouseExited

    private void txtCampoPesq1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesq1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCampoPesq1MouseReleased

    private void txtCampoPesq1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesq1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCampoPesq1KeyPressed

    private void txtCampoPesq1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesq1KeyReleased
        loadListShowe(lempresa, txtCampoPesq1.getText());
        if (txtCampoPesq1.getText().trim().equals("")) {
            loadListShowe(new EmpresaJpaController(emf).findEmpresaEntities(), "");
        }
    }//GEN-LAST:event_txtCampoPesq1KeyReleased

    private void TableEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableEmpresaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableEmpresaMouseClicked

    private void btAddEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddEmpActionPerformed
        FormEmpresa f = new FormEmpresa(new javax.swing.JFrame(), null,null,-1, "Adicionar Empresa", true);
        f.setVisible(true);
        loadListShowe(new EmpresaJpaController(emf).findEmpresaEntities(), "");
    }//GEN-LAST:event_btAddEmpActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();

        parametros.put("img", pl);
        if (!txtCampoPesq.getText().isEmpty()) {
            tipoProd = new ClienteJpaController(emf).getClienteLike(txtCampoPesq.getText());//new Consultas().formarProduto("%" + nome + "%");
        } else {
            tipoProd = new ClienteJpaController(emf).findClienteEntities();
        }
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(tipoProd);
        // String path = "/relatorios/recibov.jasper";
        try {
            //String path = "C:\\Users\\Ussimane\\Desktop\\GestaoLoja\\src\\relatorios\\proproforma.jasper";
            String path = new File("relatorios/cliente.jasper").getAbsolutePath();
            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Relatorio de Clientes", true);
            viewer.setSize(1024, 768);
            viewer.setLocationRelativeTo(null);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(CadastroClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Emprestimo;
    private javax.swing.JTable TableEmpresa;
    private javax.swing.JTable TableProduto;
    private javax.swing.JButton btAddEmp;
    private javax.swing.JButton btSalvarProd;
    private javax.swing.JButton jButton2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JPanel painelProduto;
    private javax.swing.JPanel painelProduto1;
    private javax.swing.JTextField txtCampoPesq;
    private javax.swing.JTextField txtCampoPesq1;
    // End of variables declaration//GEN-END:variables

}
