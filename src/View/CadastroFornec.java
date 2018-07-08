package View;

import controller.EntradaJpaController;
import controller.FornecedorJpaController;
import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import metodos.ButtonColumn;
import metodos.ValidarInteiro;
import metodos.ValidarString;
import modelo.Fornecedor;
import modelo.Produto;

/**
 *
 * @author rjose
 */
public final class CadastroFornec extends javax.swing.JFrame {

    int t = 0;

    List<Fornecedor> tipoProd = new ArrayList<>();
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"id", "Codigo", "Nome","Email","Endereco", "Tel", "Fax", "Editar", "Remover"});

    @Override
    public void list() {
        super.list();
    }

    public CadastroFornec() {
        initComponents();
        this.setLocation(40, 225);
        // this.setResizable(false);
        this.setTitle("Cadastro de Fornecedores");
        loadListShow(new FornecedorJpaController(emf).findFornecedorEntities(), "");
        //txtCampoPesq.setDocument(new ValidarString());
        this.TableProduto.setAutoCreateRowSorter(true);
        this.TableProduto.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableProduto.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableProduto.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.TableProduto.getColumnModel().getColumn(7).setMaxWidth(50);//setPreferredWidth(3);
        this.TableProduto.getColumnModel().getColumn(8).setMaxWidth(70);//setPreferredWidth(3);
        this.TableProduto.setRowHeight(30);
        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
        ButtonColumn bce = new ButtonColumn(this.TableProduto, editar, 7, ie);
        ButtonColumn bcr = new ButtonColumn(this.TableProduto, delete, 8, ir);
        // System.out.println(new ProdutoJpaController(emf).getProdNomeLike("P"));

    }

    public void loadListShow(List<Fornecedor> lista, String nome) {
        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }
        if (!nome.isEmpty()) {
            lista = new FornecedorJpaController(emf).getFornecedorLike(nome);//new Consultas().formarProduto("%" + nome + "%");
        }
        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < lista.size(); i++) {
            dTableModel.addRow(linha);
            dTableModel.setValueAt(lista.get(i), i, 0);
            dTableModel.setValueAt(lista.get(i).getCodigo(), i, 1);
            dTableModel.setValueAt(lista.get(i).getNome(), i, 2);
            dTableModel.setValueAt(lista.get(i).getEmail(), i, 3);
            dTableModel.setValueAt(lista.get(i).getEndereco(), i, 4);
            dTableModel.setValueAt(lista.get(i).getTelefone(), i, 5);
            dTableModel.setValueAt(lista.get(i).getFax(), i, 6);
        }
        // JTable table = new JTable(dTableModel);

    }
    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "{Pretende remover esta Fornecedor?","ATENÇÃO!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,   
                null,new String[]{"SIM", "NÃO"},null);
            if(respo==1||respo == -1)return;
            Fornecedor p = (Fornecedor) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            Boolean b = new EntradaJpaController(emf).getExistF(p);
            if (b) {
                try {
                    System.out.println(p.getIdfornecedor()+"");
                    new FornecedorJpaController(emf).destroy(p.getIdfornecedor());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CadastroFornec.class.getName()).log(Level.SEVERE, null, ex);
                }
                //  labelSucesso.setText("Produto removido com Sucesso");
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                loadListShow(new FornecedorJpaController(emf).findFornecedorEntities(), "");
            } else {
                JOptionPane.showMessageDialog(null, "Este Fornecedor nao pode ser removido,\n "
                        + "porque faz parte do historial de entrada de produtos","Atencao",JOptionPane.WARNING_MESSAGE);
            }
        }
    };
    Action editar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Fornecedor p = (Fornecedor) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormFornec f = new FormFornec(new javax.swing.JFrame(), p, "Editar Fornec", true);
            f.setVisible(true);
            loadListShow(new FornecedorJpaController(emf).findFornecedorEntities(), "");
        }
    };

      //buttonColumn.setMnemonic(keyEvent.VK_D);
    /**
     *
     * @param lista
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCampoPesq = new javax.swing.JTextField();
        painelProduto = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProduto = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btSalvarProd = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        labelHistory = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        TableProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProdutoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProduto);

        javax.swing.GroupLayout painelProdutoLayout = new javax.swing.GroupLayout(painelProduto);
        painelProduto.setLayout(painelProdutoLayout);
        painelProdutoLayout.setHorizontalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProdutoLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        painelProdutoLayout.setVerticalGroup(
            painelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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
                .addGap(23, 23, 23)
                .addComponent(btSalvarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(909, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btSalvarProd)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 204, 102));

        labelHistory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelHistory.setText("Fornecedores");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(567, Short.MAX_VALUE)
                .addComponent(labelHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(494, 494, 494))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(labelHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jMenu3.setBackground(new java.awt.Color(25, 255, 51));
        jMenu3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu3.setForeground(new java.awt.Color(0, 25, 255));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/aplicativos.png"))); // NOI18N
        jMenu3.setText("Operacoes");
        jMenu3.setFont(new java.awt.Font("Lucida Console", 1, 14)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/shopp++.png"))); // NOI18N
        jMenuItem1.setText("Venda e Emprestimo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/document_write.png"))); // NOI18N
        jMenuItem3.setText("Relatorios");
        jMenu3.add(jMenuItem3);

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Close.png"))); // NOI18N
        jMenuItem4.setText("Sair");
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSalvarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarProdActionPerformed
        FormFornec f = new FormFornec(new javax.swing.JFrame(), null, "Adicionar Fornecedor", true);
        f.setVisible(true);
        loadListShow(new FornecedorJpaController(emf).findFornecedorEntities(), "");


    }//GEN-LAST:event_btSalvarProdActionPerformed


    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {

        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void TableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProdutoMouseClicked

    }//GEN-LAST:event_TableProdutoMouseClicked

    private void txtCampoPesqMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseReleased

    }//GEN-LAST:event_txtCampoPesqMouseReleased

    private void txtCampoPesqMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseExited

    }//GEN-LAST:event_txtCampoPesqMouseExited

    private void txtCampoPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyReleased
        loadListShow(tipoProd, txtCampoPesq.getText());
        if (txtCampoPesq.getText().trim().equals("")) {
            loadListShow(new FornecedorJpaController(emf).findFornecedorEntities(), "");
        }
    }//GEN-LAST:event_txtCampoPesqKeyReleased

    private void txtCampoPesqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyPressed

    }//GEN-LAST:event_txtCampoPesqKeyPressed

    @SuppressWarnings("unchecked")

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableProduto;
    private javax.swing.JButton btSalvarProd;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JPanel painelProduto;
    private javax.swing.JTextField txtCampoPesq;
    // End of variables declaration//GEN-END:variables
}
