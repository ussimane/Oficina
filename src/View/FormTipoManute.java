/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static View.VendaEmprestimo.lproe;
import static View.VendaEmprestimo.lprov;
import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.ClienteJpaController;
import controller.EmprestimoJpaController;
import controller.FamiliaJpaController;
import controller.ImpostoJpaController;
import controller.ManutencaoJpaController;
import controller.TipomanutencaoJpaController;
import controller.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import metodos.ValidarFloat;
import metodos.ValidarInteiro;
import metodos.ValidarString;
import modelo.Produto;
import modelo.Cliente;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import modelo.Emprestimo;
import modelo.Familia;
import modelo.Imposto;
import modelo.Tipomanutencao;
import modelo.Venda;
import modelo.Vendedor;

/**
 *
 * @author Ussimane
 */
public class FormTipoManute extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static String t;
    private static Tipomanutencao p;

    public FormTipoManute(java.awt.Frame parent, Tipomanutencao p, String t, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        txtIdP.setVisible(false);
        this.p = p;
        this.t = t;
        //  txtdesc.setDocument(new ValidarString());
        txtprec.setDocument(new ValidarFloat());
        labelTitle.setText(t);
        lbValidacao.setVisible(false);
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(jButton1);
        if (p != null) {
            //Boolean b = new VendaJpaController(emf).getExistC(p);
            txtIdP.setText(p.getIdtipomanutencao() + "");
            txtdesc.setText(p.getDesignacao());
            txtprec.setText(p.getPreco() + "");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        txtIdP = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        lbValidacao = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtdesc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtprec = new javax.swing.JTextField();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(getLimpaVenda());
        setName("dialogProd"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));

        labelTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        lbValidacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbValidacao.setForeground(new java.awt.Color(255, 51, 51));
        lbValidacao.setText("jLabel4");

        jLabel3.setText("Descricao:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_ok.png"))); // NOI18N
        jButton1.setText("Aceitar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Preco:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(29, 29, 29)
                                .addComponent(jButton2)
                                .addGap(63, 63, 63)
                                .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtdesc))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtprec, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(49, 49, 49))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(97, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbValidacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtprec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtdesc.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira uma descricao");
            txtdesc.requestFocusInWindow();
            return;
        }
        //        if (txtemp.getText().trim().equals("")) {
        //            lbValidacao.setVisible(true);
        //            lbValidacao.setText("Insira o nome do Cliente / Empresa");
        //            txtemp.requestFocusInWindow();
        //            return;
        //        }

        if (txtIdP.getText().trim().equals("")) {
//           ManutencaoJpaController cc = new ManutencaoJpaController(emf);
//            if(cc.existeTipoManutencao(txtdesc.getText())!=0){
//                lbValidacao.setVisible(true);
//                lbValidacao.setText("Esta familia ja se encontra cadastro!");
//                txtdesc.requestFocusInWindow();
//                return;
//            }
            Tipomanutencao p = new Tipomanutencao();
            //  pro.setNuit(txtNuit.getText());
            p.setDesignacao(txtdesc.getText());
            p.setPreco(Float.parseFloat(txtprec.getText()));
            new TipomanutencaoJpaController(emf).create(p);
            limpar();
            JOptionPane.showMessageDialog(this, "Adicionado com Sucesso");
        } else {
            int respo = JOptionPane.showOptionDialog(null, "Pretende modificar a descricao deste Tipo?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            try {
                Tipomanutencao i = new TipomanutencaoJpaController(emf).findTipomanutencao(Integer.parseInt(txtIdP.getText()));
//                String c= i.getDescricao();
//                if((!c.equals(txtdesc.getText()))&&cc.existeFamilia(txtdesc.getText())!=0){
//                    lbValidacao.setVisible(true);
//                    lbValidacao.setText("Esta familia ja se encontra cadastro!");
//                    txtdesc.requestFocusInWindow();
//                    return;
//                }
                //                pro.setNuit(txtNuit.getText());
                p.setDesignacao(txtdesc.getText());
                p.setPreco(Float.parseFloat(txtprec.getText()));
                new TipomanutencaoJpaController(emf).edit(i);
                JOptionPane.showMessageDialog(this, "Editado com Sucesso");
                this.setVisible(false);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpar();
        this.getParent().setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed
    public void limpar() {
        txtIdP.setText("");
        txtdesc.setText("");
        txtprec.setText("");
        lbValidacao.setVisible(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormTipoManute dialog = new FormTipoManute(new javax.swing.JFrame(), p, t, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public int getLimpaVenda() {

        return g();

    }

    public int g() {
        return 1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel lbValidacao;
    private javax.swing.JTextField txtIdP;
    private javax.swing.JTextField txtdesc;
    private javax.swing.JTextField txtprec;
    // End of variables declaration//GEN-END:variables
}
