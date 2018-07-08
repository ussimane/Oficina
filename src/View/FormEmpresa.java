/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.ClienteJpaController;
import controller.EmpresaJpaController;
import controller.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import metodos.ValidarFloat;
import metodos.ValidarInteiro;
import metodos.ValidarString;
import modelo.Produto;
import modelo.Cliente;
import modelo.Empresa;

/**
 *
 * @author Ussimane
 */
public class FormEmpresa extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static Empresa p;
    private static String t;
    private static int posi;
    private int pos;
    private static TableModel tm;

    public FormEmpresa(java.awt.Frame parent, Empresa p, TableModel tm, int pos, String t, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        //  this.setUndecorated(true);
        txtIdP.setVisible(false);
        txtNuit.setDocument(new ValidarInteiro());
        txtdesc.setDocument(new ValidarInteiro());
        this.setLocationRelativeTo(null);
        cvd.setSelected(true);
        cvc.setSelected(false);
        this.p = p;
        this.t = t;
        this.pos = pos;
        this.tm = tm;
        labelTitle.setText(t);
        lbValidacao.setVisible(false);
        if (p != null) {
            //Boolean b = new VendaJpaController(emf).getExistC(p);
            txtIdP.setText(p.getIdempresa() + "");
            txtNuit.setText(p.getCodigo());
            txtcel1.setText(p.getTelefone());
            txtenderec.setText(p.getEndereco());
            txtemail.setText(p.getEmail());
            txtresp.setText(p.getNome());
            txtdesc.setText(p.getDesconto() + "");
            if (p.getTipovend() == 1) {
                cvc.setSelected(true);
                cvd.setSelected(false);
            }
            if (p.getTipovend() == 0) {
                cvd.setSelected(true);
                cvc.setSelected(false);
            }
            if (p.getTipovend() == 2) {
                cvc.setSelected(true);
                cvd.setSelected(true);
            }
            chEstado.setSelected(!p.getEstado());
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNuit = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtIdP = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        lbValidacao = new javax.swing.JLabel();
        txtenderec = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtcel1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtresp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtdesc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cvd = new javax.swing.JCheckBox();
        cvc = new javax.swing.JCheckBox();
        chEstado = new javax.swing.JCheckBox();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("dialogProd"); // NOI18N

        jLabel1.setText("Codigo:");

        jLabel3.setText("Endereco:");

        txtNuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuitActionPerformed(evt);
            }
        });

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

        jLabel4.setText("Telemovel:");

        txtcel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcel1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Email:");

        jLabel5.setText("Nome:");

        jLabel2.setText("Desconto:");

        jLabel6.setText("%");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cvd.setText("Venda p/ dinheiro");

        cvc.setText("Venda p/ credito");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(cvd)
                .addGap(46, 46, 46)
                .addComponent(cvc)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cvd)
                .addComponent(cvc))
        );

        chEstado.setText("Desabilitado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(txtNuit, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtresp, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(txtenderec)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(15, 15, 15)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addComponent(txtemail, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                            .addComponent(txtcel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jButton1)
                        .addGap(50, 50, 50)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lbValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(chEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbValidacao)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtresp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtenderec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtcel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(chEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtNuit.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o codigo");
            txtNuit.requestFocusInWindow();
            return;
        }
//        if (txtemp.getText().trim().equals("")) {
//            lbValidacao.setVisible(true);
//            lbValidacao.setText("Insira o nome do Cliente / Empresa");
//            txtemp.requestFocusInWindow();
//            return;
//        }
        if (txtresp.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o Nome");
            txtresp.requestFocusInWindow();
            return;
        }
        if (!cvd.isSelected() && !cvc.isSelected()) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Escolha tipos de Venda");
            jPanel2.requestFocusInWindow();
            return;
        }

        if (txtIdP.getText().trim().equals("")) {
            EmpresaJpaController cc = new EmpresaJpaController(emf);
            if (cc.existeCodigo(txtNuit.getText()) != 0) {
                lbValidacao.setVisible(true);
                lbValidacao.setText("Este codigo ja existe. Introduza outro");
                txtNuit.requestFocusInWindow();
                return;
            }
            Empresa pro = new Empresa();
            //  pro.setNuit(txtNuit.getText());
            pro.setNome(txtresp.getText());
            pro.setCodigo(txtNuit.getText());
            pro.setEmail(txtemail.getText());
            pro.setEndereco(txtenderec.getText());
            pro.setTelefone(txtcel1.getText());
            if (txtdesc.getText().trim().equals("")) {
                pro.setDesconto(0);
            } else {
                pro.setDesconto(Integer.parseInt(txtdesc.getText()));
            }
            if (cvd.isSelected()) {
                pro.setTipovend(new Short("0"));
                if (cvc.isSelected()) {
                    pro.setTipovend(new Short("2"));
                }
            } else {
                pro.setTipovend(new Short("1"));
            }
            if (chEstado.isSelected()) {
                pro.setEstado(false);
            } else {
                pro.setEstado(true);
            }

            cc.create(pro);
            limpar();
            JOptionPane.showMessageDialog(this, "Adicionado com Sucesso");
        } else {
            int respo = JOptionPane.showOptionDialog(null, "Pretende modificar os dados desta Empresa?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            try {
                EmpresaJpaController cc = new EmpresaJpaController(emf);
                Empresa pro = cc.findEmpresa(Integer.parseInt(txtIdP.getText()));
                String c = pro.getCodigo();
                if ((!c.equals(txtNuit.getText())) && cc.existeCodigo(txtNuit.getText()) != 0) {
                    lbValidacao.setVisible(true);
                    lbValidacao.setText("Este codigo ja existe. Introduza outro");
                    txtNuit.requestFocusInWindow();
                    return;
                }
//                pro.setNuit(txtNuit.getText());
                pro.setNome(txtresp.getText());
                pro.setCodigo(txtNuit.getText());
                pro.setEmail(txtemail.getText());
                pro.setEndereco(txtenderec.getText());
                pro.setTelefone(txtcel1.getText());
                if (txtdesc.getText().trim().equals("")) {
                    pro.setDesconto(0);
                } else {
                    pro.setDesconto(Integer.parseInt(txtdesc.getText()));
                }
                if (cvd.isSelected()) {
                    pro.setTipovend(new Short("0"));
                    if (cvc.isSelected()) {
                        pro.setTipovend(new Short("2"));
                    }
                } else {
                    pro.setTipovend(new Short("1"));
                }
                if (chEstado.isSelected()) {
                    pro.setEstado(false);
                } else {
                    pro.setEstado(true);
                }
                new EmpresaJpaController(emf).edit(pro);
                tm.setValueAt(pro, pos, 0);
                tm.setValueAt(pro.getCodigo(), pos, 1);
                tm.setValueAt(pro.getNome(), pos, 2);
                tm.setValueAt(pro.getEndereco(), pos, 3);
                tm.setValueAt(pro.getTelefone(), pos, 4);
                tm.setValueAt(pro.getEmail(), pos, 5);
                tm.setValueAt(pro.getDesconto(), pos, 6);
                int tv = pro.getTipovend();
                if (tv == 0) {
                    tm.setValueAt("Por Dinheiro", pos, 7);
                }
                if (tv == 1) {
                    tm.setValueAt("Por Credito", pos, 7);
                }
                if (tv == 2) {
                    tm.setValueAt("Dinheiro/Credito", pos, 7);
                }
                if (pro.getEstado()) {
                    tm.setValueAt("Sim", pos, 8);
                } else {
                    tm.setValueAt("Não", pos, 8);
                }
                JOptionPane.showMessageDialog(this, "Editado com Sucesso");
                this.setVisible(false);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    public void limpar() {
        txtIdP.setText("");
        txtNuit.setText("");
        txtcel1.setText("");
        txtenderec.setText("");
        txtemail.setText("");
        txtresp.setText("");
        txtdesc.setText("");
        cvd.setSelected(true);
        cvc.setSelected(false);
        lbValidacao.setVisible(false);
    }

    private void txtNuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuitActionPerformed

    }//GEN-LAST:event_txtNuitActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpar();
        this.getParent().setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcel1ActionPerformed

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
                FormEmpresa dialog = new FormEmpresa(new javax.swing.JFrame(), p, tm, posi, t, true);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chEstado;
    private javax.swing.JCheckBox cvc;
    private javax.swing.JCheckBox cvd;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel lbValidacao;
    private javax.swing.JTextField txtIdP;
    private javax.swing.JTextField txtNuit;
    private javax.swing.JTextField txtcel1;
    private javax.swing.JTextField txtdesc;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtenderec;
    private javax.swing.JTextField txtresp;
    // End of variables declaration//GEN-END:variables
}
