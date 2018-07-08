package View;

import controller.ClienteJpaController;
import controller.EmprestimoJpaController;
import controller.EntradaJpaController;
import controller.ProdutoJpaController;
import controller.SaidaJpaController;
import controller.SeriefacturaJpaController;
import controller.VendaJpaController;
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
import modelo.Emprestimo;
import modelo.Entrada;
import modelo.Produto;
import modelo.Saida;
import modelo.SaidaPK;
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
public final class ProdutoExp extends javax.swing.JDialog {

    int t = 0;
    JasperPrint jpPrint;
    JasperViewer jv;
    HashMap<String, Object> par = new HashMap<String, Object>();
    Map parametros = new HashMap<>();
    List<Entrada> listaE = new ArrayList<>();
    static Produto produ;
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"id", "Produto","Data de entrada", "Data de Expiracao","Qtd Stock","Qtd Balcao" ,"Preco", "Fornecedor", "Editar", "Remover"});

    @Override
    public void list() {
        super.list();
    }

    public ProdutoExp() throws Exception {
        initComponents();
        this.setLocation(40, 225);
        // this.setResizable(false);
        Date d = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date n = cal.getTime();
        listaE = new EntradaJpaController(emf).getEntExp();
        mostraEntrada();
         this.TableEntrada.setAutoCreateRowSorter(true);
        this.TableEntrada.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableEntrada.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableEntrada.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableEntrada.getColumnModel().getColumn(8).setMaxWidth(50);//setPreferredWidth(3);
        this.TableEntrada.getColumnModel().getColumn(9).setMaxWidth(70);//setPreferredWidth(3);
        this.TableEntrada.setRowHeight(30);
         
        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
        ButtonColumn bce = new ButtonColumn(this.TableEntrada, editar, 8, ie);
        ButtonColumn bcr = new ButtonColumn(this.TableEntrada, delete, 9, ir);
        
    }

    public void mostraEntrada() throws Exception {

        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listaE.size(); i++) {

            dTableModel.addRow(linha);
            dTableModel.setValueAt(listaE.get(i), i, 0);
            dTableModel.setValueAt(listaE.get(i).getProduto().getNome(), i, 1);
            dTableModel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaE.get(i).getEntradaPK().getData()), i, 2);
            dTableModel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listaE.get(i).getDataexpiracao()), i, 3);
            dTableModel.setValueAt(listaE.get(i).getQtd()-listaE.get(i).getQs(), i, 4);
            dTableModel.setValueAt(listaE.get(i).getQv(), i, 5);
            dTableModel.setValueAt(listaE.get(i).getPreco(), i, 6);
            dTableModel.setValueAt(listaE.get(i).getIdfornecedor().getNome(), i, 7);
        }
    }

   
    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "{Pretende remover este Medicamento?","ATENÇÃO!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,   
                null,new String[]{"SIM", "NÃO"},null);
            if(respo==1||respo == -1)return;
            Entrada p = (Entrada) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            Produto prod = new ProdutoJpaController(emf).findProduto(p.getProduto().getIdproduto());
            prod.setQtstock(prod.getQtstock()-(p.getQtd()-p.getQs()));
            prod.setQtdvenda(prod.getQtdvenda()-p.getQv());
            Saida sa = new Saida();
            sa.setSaidaPK(new SaidaPK(prod.getIdproduto(), new Date()));
            sa.setObs("Expirou");
            sa.setQpac(prod.getQtdvenda());
            sa.setQstock(prod.getQtstock());
            sa.setPreco(p.getPreco());
            sa.setQtd((p.getQtd()-p.getQs())+p.getQv());//deveria ter duas saidas?
            sa.setProduto(produ);
            sa.setDatae(p.getEntradaPK().getData());
            
            p.setQd(p.getQd()+(p.getQtd()-p.getQs())+p.getQv());
            p.setQs(p.getQtd());
            p.setQv(0);
            try {
                new SaidaJpaController(emf).create(sa);
                new ProdutoJpaController(emf).edit(prod);
                new EntradaJpaController(emf).edit(p);
            } catch (Exception ex) {
                Logger.getLogger(CadastroEntSaid.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((DefaultTableModel) table.getModel()).removeRow(modelRow);
        }
    };
     
    Action editar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Entrada p = (Entrada) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            p = new EntradaJpaController(emf).findEntrada(p.getEntradaPK());//removeRow(modelRow);
            EntDataExp f = new EntDataExp(new javax.swing.JFrame(), p, true);
            f.setVisible(true);
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
        jButton5 = new javax.swing.JButton();

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
        labelHistory.setText("Produtos fora do prazo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(565, Short.MAX_VALUE)
                .addComponent(labelHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(474, 474, 474))
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
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        painelProdutoLayout.setVerticalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton5.setBackground(new java.awt.Color(102, 204, 0));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton5.setText("Exportar PDF");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton5)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jDesktopPane1.add(jPanel3);
        jPanel3.setBounds(0, 0, 1280, 420);

        Emprestimo.addTab("Entradas", jDesktopPane1);

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
                .addComponent(Emprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    public void setIndexAba(int i) {
        Emprestimo.setSelectedIndex(i);
    }

    public void selecionarPromocao() {
        Emprestimo.setSelectedIndex(1);
    }


    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus

    }//GEN-LAST:event_formWindowLostFocus

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost

    }//GEN-LAST:event_formFocusLost

    private void EmprestimoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmprestimoMouseClicked
 
    }//GEN-LAST:event_EmprestimoMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();
        parametros.put("img", pl);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaE);
        // String path = "/relatorios/recibov.jasper";
        try {
            //String path = "C:\\Users\\Ussimane\\Desktop\\GestaoLoja\\src\\relatorios\\proproforma.jasper";
            String path = new File("relatorios/prodexp.jasper").getAbsolutePath();
            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Produtos Expirados", true);
            viewer.setSize(1024, 768);
            viewer.setLocationRelativeTo(null);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ProdutoExp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void TableEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableEntradaMouseClicked

    }//GEN-LAST:event_TableEntradaMouseClicked

    public int getLimpaVenda() {
        return 1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Emprestimo;
    private javax.swing.JTable TableEntrada;
    private javax.swing.JButton jButton5;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JPanel painelProduto;
    // End of variables declaration//GEN-END:variables

}
