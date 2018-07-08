/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.MecanicoJpaController;
import controller.EmpresaJpaController;
import controller.MecanicoJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import metodos.ValidarFloat;
import metodos.ValidarInteiro;
import metodos.ValidarString;
import modelo.Produto;
import modelo.Mecanico;
import modelo.Empresa;
import modelo.Mecanico;

/**
 *
 * @author Ussimane
 */
public class FormMecanico extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static Mecanico p;
    private static String t;
    private static int posi;
    private static TableModel tm;
    private int pos;

    public FormMecanico(java.awt.Frame parent, Mecanico p, TableModel tm, int pos, String t, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        //  this.setUndecorated(true);
        this.getRootPane().setDefaultButton(jButton1);
        txtIdP.setVisible(false);
//        txtNuit.setDocument(new ValidarInteiro());
        this.setLocationRelativeTo(null);
        this.p = p;
        this.t = t;
        this.tm = tm;
        this.pos=pos;
        labelTitle.setText(t);
        lbValidacao.setVisible(false);
        if (p != null) {
          //  tm.setValueAt("sfsdfsdfsdfsdf", 1, 1);
            //Boolean b = new VendaJpaController(emf).getExistC(p);
            txtIdP.setText(p.getIdmecanico() + "");
           // txtNuit.setText(p.getCodigo());
            txtarea.setText(p.getArea());
            txtcel1.setText(p.getTelefone());
            txtenderec.setText(p.getEndereco());
            txtemail.setText(p.getEmail());
            txtnome.setText(p.getNome());
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
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtIdP = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        lbValidacao = new javax.swing.JLabel();
        txtarea = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtcel1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtnome = new javax.swing.JTextField();
        chEstado = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        txtenderec = new javax.swing.JTextField();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("dialogProd"); // NOI18N

        jLabel3.setText("Endereco:");

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

        jLabel8.setText("Email:");

        jLabel5.setText("Nome:");

        chEstado.setText("Desabilitado");

        jLabel6.setText("Area de Trabalho:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lbValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel5)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtnome, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(67, 67, 67)
                                    .addComponent(jButton1)
                                    .addGap(52, 52, 52)
                                    .addComponent(jButton2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtarea, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                    .addComponent(txtenderec))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel8))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtemail, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                    .addComponent(txtcel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(chEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbValidacao)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtnome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtenderec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtcel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        if (txtNuit.getText().trim().equals("")) {
//            lbValidacao.setVisible(true);
//            lbValidacao.setText("Insira o codigo");
//            txtNuit.requestFocusInWindow();
//            return;
//        }
//        if (txtemp.getText().trim().equals("")) {
//            lbValidacao.setVisible(true);
//            lbValidacao.setText("Insira o nome do Mecanico / Empresa");
//            txtemp.requestFocusInWindow();
//            return;
//        }
        if (txtnome.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o Nome");
            txtnome.requestFocusInWindow();
            return;
        }
        
        if (txtIdP.getText().trim().equals("")) {
            MecanicoJpaController cc = new MecanicoJpaController(emf);
//            if (cc.existeCodigo(txtNuit.getText(), id) != 0) {
//                lbValidacao.setVisible(true);
//                lbValidacao.setText("Este codigo ja existe. Introduza outro");
//                txtNuit.requestFocusInWindow();
//                return;
//            }
            Mecanico pro = new Mecanico();
            //  pro.setNuit(txtNuit.getText());
            pro.setTelefone(txtcel1.getText());
          //  pro.setCodigo(txtNuit.getText());
            pro.setEndereco(txtenderec.getText());
            pro.setEmail(txtemail.getText());
            pro.setNome(txtnome.getText());
            pro.setArea(txtarea.getText());
            if (chEstado.isSelected()) {
                pro.setEstado(false);
            } else {
                pro.setEstado(true);
            }
            cc.create(pro);
            limpar();
            JOptionPane.showMessageDialog(this, "Adicionado com Sucesso");
        } else {
            int respo = JOptionPane.showOptionDialog(null, "Pretende modificar os dados deste Mecanico?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            try {
                MecanicoJpaController cc = new MecanicoJpaController(emf);
                Mecanico pro = cc.findMecanico(Integer.parseInt(txtIdP.getText()));
//                String c = pro.getCodigo();
//                if ((!c.equals(txtNuit.getText())) && cc.existeCodigo(txtNuit.getText(), id) != 0) {
//                    lbValidacao.setVisible(true);
//                    lbValidacao.setText("Este codigo ja existe. Introduza outro");
//                    txtNuit.requestFocusInWindow();
//                    return;
//                }
//                pro.setNuit(txtNuit.getText());
                pro.setTelefone(txtcel1.getText());
          //  pro.setCodigo(txtNuit.getText());
            pro.setEndereco(txtenderec.getText());
            pro.setEmail(txtemail.getText());
            pro.setNome(txtnome.getText());
            pro.setArea(txtarea.getText());
            if (chEstado.isSelected()) {
                pro.setEstado(false);
            } else {
                pro.setEstado(true);
            }
                new MecanicoJpaController(emf).edit(pro);
//                tm.setValueAt(pro, pos, 0);
//                tm.setValueAt(pro.getCodigo(), pos, 1);
//                tm.setValueAt(pro.getNome(), pos, 2);
//                tm.setValueAt(pro.getEndereco(), pos, 3);
//                tm.setValueAt(pro.getIdempresa().getNome(), pos, 4);
//                tm.setValueAt(pro.getCell(), pos, 5);
//                tm.setValueAt(pro.getEmail(), pos, 6);
//                if (pro.getEstado()) {
//                    tm.setValueAt("Sim", pos, 7);
//                } else {
//                    tm.setValueAt("Não", pos, 7);
//                }
                limpar();
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
       // txtNuit.setText("");
        txtcel1.setText("");
        // txtemp.setText("");
        txtarea.setText("");
        txtenderec.setText("");
        txtemail.setText("");
        txtnome.setText("");
        lbValidacao.setVisible(false);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpar();
        this.getParent().setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

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
                FormMecanico dialog = new FormMecanico(new javax.swing.JFrame(), p, tm, posi, t, true);
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

    public void combobox(JComboBox c) {
        List<Empresa> listaemp = new EmpresaJpaController(emf).findEmpresaEntities();
        c.removeAllItems();
        for (Empresa lista : listaemp) {
            c.addItem(lista);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel lbValidacao;
    private javax.swing.JTextField txtIdP;
    private javax.swing.JTextField txtarea;
    private javax.swing.JTextField txtcel1;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtenderec;
    private javax.swing.JTextField txtnome;
    // End of variables declaration//GEN-END:variables
}
