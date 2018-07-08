package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author rjose
 */
public class Principal extends javax.swing.JFrame {

    private int client;
    private int t;

    public Principal(int i, String s, int t) {

        Dimension centro = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((centro.width) / 3, (centro.height) / 3);
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());

        setResizable(true);
        // setSize(400, 300);  
        setLocationRelativeTo(null);
        setVisible(true);
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        lbUser.setText(s);
        this.t = t;
        if (t == 0) {
            lbUProf.setText("Vendedor");
//            btCadastro.setVisible(false);
//            jLabel1.setVisible(false);
//            btRelatorio.setVisible(false);
//            jLabel7.setVisible(false);
//            btUtilizador.setVisible(false);
//            jLabel4.setVisible(false);
//            btCliente.setVisible(false);
//            jLabel9.setVisible(false);
//            btfornec.setVisible(false);
//            jLabel11.setVisible(false);
//            btConfig.setVisible(false);
//            jLabel10.setVisible(false);
//            btVenda.setVisible(true);
        }
        if (t == 1) {
            lbUProf.setText("Gestor de produtos e clientes");
//            btVenda.setVisible(false);
//            jLabel5.setVisible(false);
//            btRelatorio.setVisible(false);
//            jLabel7.setVisible(false);
//            btUtilizador.setVisible(false);
//            jLabel4.setVisible(false);
        }
        if (t == 2) {
            lbUProf.setText("Super utilizador");
        }
        this.client = i;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        principalPainel = new javax.swing.JPanel();
        LabelBackground = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        lbUProf = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        botagestaoFormador = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        BotaoGestaoMestre = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        botaoGestaoFormando = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        botaoutras = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        botaoutras1 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        BotaoRelatoriosEstatisticos = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenuItem1.setText("jMenuItem1");

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        principalPainel.setPreferredSize(new java.awt.Dimension(0, 300));

        LabelBackground.setFont(new java.awt.Font("Adobe Caslon Pro", 1, 12)); // NOI18N
        LabelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/imageCapa1.jpg"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Profile.png"))); // NOI18N

        lbUProf.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbUProf.setForeground(new java.awt.Color(153, 0, 51));

        jToolBar1.setBorder(null);
        jToolBar1.setOpaque(false);
        jToolBar1.add(jSeparator3);

        botagestaoFormador.setText("Gestão de Venda");
        botagestaoFormador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botagestaoFormadorActionPerformed(evt);
            }
        });
        jToolBar1.add(botagestaoFormador);
        jToolBar1.add(jSeparator11);

        BotaoGestaoMestre.setText("Gestão de Viaturas");
        BotaoGestaoMestre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoGestaoMestreActionPerformed(evt);
            }
        });
        jToolBar1.add(BotaoGestaoMestre);
        jToolBar1.add(jSeparator4);

        botaoGestaoFormando.setText("Pecas e Acessorios");
        botaoGestaoFormando.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoGestaoFormandoActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoGestaoFormando);
        jToolBar1.add(jSeparator5);

        botaoutras.setText("Ferramentas");
        botaoutras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoutrasActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoutras);
        jToolBar1.add(jSeparator6);

        jButton2.setText("Relatórios");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator10);

        jButton1.setText("Clientes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator8);

        jButton4.setText("Mecânicos");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator12);

        jButton3.setText("Fornecedores");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator9);

        botaoutras1.setText("Utilizadores");
        botaoutras1.setFocusable(false);
        botaoutras1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoutras1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoutras1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoutras1ActionPerformed(evt);
            }
        });
        jToolBar1.add(botaoutras1);
        jToolBar1.add(jSeparator7);

        BotaoRelatoriosEstatisticos.setText("Configurações");
        BotaoRelatoriosEstatisticos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoRelatoriosEstatisticosActionPerformed(evt);
            }
        });
        jToolBar1.add(BotaoRelatoriosEstatisticos);

        javax.swing.GroupLayout principalPainelLayout = new javax.swing.GroupLayout(principalPainel);
        principalPainel.setLayout(principalPainelLayout);
        principalPainelLayout.setHorizontalGroup(
            principalPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(principalPainelLayout.createSequentialGroup()
                .addComponent(LabelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1420, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, principalPainelLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 1009, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(principalPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(principalPainelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, principalPainelLayout.createSequentialGroup()
                        .addComponent(lbUProf, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(173, 173, 173))))
        );
        principalPainelLayout.setVerticalGroup(
            principalPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, principalPainelLayout.createSequentialGroup()
                .addGroup(principalPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(principalPainelLayout.createSequentialGroup()
                        .addGroup(principalPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbUProf, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(principalPainelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LabelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenuBar1.setBackground(new java.awt.Color(45, 126, 237));
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(principalPainel, javax.swing.GroupLayout.DEFAULT_SIZE, 1430, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(principalPainel, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void addVendaFrame() {
        try {
            VendaEmprestimo v = new VendaEmprestimo(client);
            LabelBackground.add(v).setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void botagestaoFormadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botagestaoFormadorActionPerformed
        try {
            VendaEmprestimo v = new VendaEmprestimo(client);
            JDialog dia = new JDialog();
            dia.setModal(true);
            dia.setContentPane(v.getContentPane());
            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
            // dia.setBounds(v.getBounds());
            dia.setSize(v.getWidth(), LabelBackground.getHeight() - 45);
            dia.setLocationRelativeTo(LabelBackground);
            dia.setVisible(true);
            dia.setDefaultCloseOperation(0);
//            LabelBackground.add(dia).setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botagestaoFormadorActionPerformed

    private void BotaoGestaoMestreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoGestaoMestreActionPerformed
          CadastroViatura c = new CadastroViatura(client);
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        dia.setBounds(c.getBounds());
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_BotaoGestaoMestreActionPerformed

    private void botaoGestaoFormandoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoGestaoFormandoActionPerformed
         CadastroProduto c = new CadastroProduto(client);
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        // dia.setBounds(c.getBounds());
        dia.setSize(c.getWidth(), LabelBackground.getHeight() - 45);
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_botaoGestaoFormandoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        CadastroFornec c = new CadastroFornec();
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        dia.setBounds(c.getBounds());
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void botaoutras1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoutras1ActionPerformed
          try {
            CadastroUtilizador v = new CadastroUtilizador();
            JDialog dia = new JDialog();
            dia.setModal(true);
            dia.setContentPane(v.getContentPane());
            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
            //dia.setBounds(v.getBounds());
            dia.setSize(v.getWidth(), LabelBackground.getHeight() - 45);
            dia.setLocationRelativeTo(LabelBackground);
            dia.setLocation(60, 225);
            dia.setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_botaoutras1ActionPerformed

    private void BotaoRelatoriosEstatisticosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoRelatoriosEstatisticosActionPerformed
         Configuracao c = new Configuracao();
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        dia.setBounds(c.getBounds());
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_BotaoRelatoriosEstatisticosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ConsultasInternas c = null;
        try {
            c = new ConsultasInternas(client);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        dia.setContentPane(c.getContentPane());
        // dia.setBounds(c.getBounds());
        dia.setSize(c.getWidth(), LabelBackground.getHeight() - 45);
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
//            CadastroCliente v = new CadastroCliente();
//            JDialog dia = new JDialog();
//            dia.setModal(true);
//            dia.setContentPane(v.getContentPane());
//            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
//            //dia.setBounds(v.getBounds());
//            dia.setSize(v.getWidth(), LabelBackground.getHeight() - 45);
//            dia.setLocationRelativeTo(LabelBackground);
//            dia.setVisible(true);
            CadastroClient v = new CadastroClient();
            JDialog dia = new JDialog();
            dia.setModal(true);
            dia.setContentPane(v.getContentPane());
            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
            //dia.setBounds(v.getBounds());
            dia.setSize(v.getWidth(), LabelBackground.getHeight() - 45);
            dia.setLocationRelativeTo(LabelBackground);
            dia.setVisible(true);
            dia.setDefaultCloseOperation(0);

        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botaoutrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoutrasActionPerformed
         CadastroFerram c = null;
        try {
            c = new CadastroFerram(client);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        // dia.setBounds(c.getBounds());
        dia.setSize(c.getWidth(), LabelBackground.getHeight() - 45);
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_botaoutrasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("ussimane");
                String line;
                Process p = null;
                try {
                    p = Runtime.getRuntime().exec("tasklist");
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line.intern());
                        if (line.contains("java")) {
                            while ((line = input.readLine()) != null) {
                                if (line.contains("java")) {
                                  //  System.exit(0);
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //new Principal().setVisible(true);
                login dialog = new login(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton BotaoGestaoMestre;
    private javax.swing.JButton BotaoRelatoriosEstatisticos;
    private javax.swing.JLabel LabelBackground;
    public static javax.swing.JButton botagestaoFormador;
    public static javax.swing.JButton botaoGestaoFormando;
    public static javax.swing.JButton botaoutras;
    public static javax.swing.JButton botaoutras1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbUProf;
    private javax.swing.JLabel lbUser;
    private javax.swing.JPanel principalPainel;
    // End of variables declaration//GEN-END:variables
}
