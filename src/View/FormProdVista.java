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
import controller.EntradaJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.Color;
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
import modelo.Emprestimo;
import modelo.Entrada;
import modelo.Saida;
import modelo.Venda;
import modelo.Vendedor;

/**
 *
 * @author Ussimane
 */
public class FormProdVista extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static Produto p;

    public FormProdVista(java.awt.Frame parent, Produto p, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        int i = 1;
        float soma = 0;
        this.p = p;
        this.setLocationRelativeTo(null);
//        for (Emprestimo e : p) {
//            mostrarResultdo(i, e.getIdproduto().getNome(), e.getTotalpagar() / e.getQtprod(), e.getQtprod(), e.getTotalpagar());
//            soma = soma + e.getTotalpagar();
//            i++;
//        }
        this.getRootPane().setDefaultButton(jButton1);
        vendaLista.add("_____________________________________________________________________________________");
        vendaLista.add(" Descricao: " + p.getNome());
        vendaLista.add(" Codigo: " + p.getCodigo());
//        if(p.getDatacriacao()!=null){
//        vendaLista.add(" Data de Fabrico: "+new java.text.SimpleDateFormat("dd/MM/yyyy").format(p.getDatacriacao()));
//        }else{
//         vendaLista.add(" Data de Fabrico: ");
//        }
//        if(p.getDataexpiracao()!=null){
//        vendaLista.add(" Data de Expiracao: "+new java.text.SimpleDateFormat("dd/MM/yyyy").format(p.getDataexpiracao()));
//        }else{
//         vendaLista.add(" Data de Expiracao: ");
//        }
        if (p.getIdfamilia() != null) {
            vendaLista.add(" Categoria: " + p.getIdfamilia().getDescricao());
        } else {
            vendaLista.add(" Categoria: ......");
        }
        vendaLista.add("_____________________________________________________________________________________");
        vendaLista.add(" Quantidade no Armazem: " + p.getQtstock());
        vendaLista.add(" Quantidade no Loja: " + p.getQtdvenda());
        vendaLista.add("_____________________________________________________________________________________");
//        vendaLista.add(" Custo: " + p.getCusto());
//        if (p.getIdimposto() != null) {
//            vendaLista.add(" Imposto: " + p.getIdimposto().getDescricao());
//        } else {
//            vendaLista.add(" Imposto: Insento");
//        }
//        vendaLista.add(" Margem: " + p.getMargem());
//        vendaLista.add("_____________________________________________________________________________________");
        List<Entrada> le = new EntradaJpaController(emf).getEntFull(p);
        String prec = "";
        for (Entrada e : le) {
            prec = e.getQa() + "x" + e.getPreco();
            if (e.getPb() != null) {
                prec = prec + " | " + e.getQb() + "x" + e.getPb();
            }
            if (e.getPc() != null) {
                prec = prec + " | " + e.getQc() + "x" + e.getPc();
            }
            vendaLista.add("Serie/Lote Nr."+e.getSerie()+" -- Preco Venda: " + prec + " -- Q. Loja: " + e.getQv() + " -- Q. Armazem: " + (e.getQtd() - e.getQs()));// + " -- Data/Nr. Lot: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(e.getEntradaPK().getData()));
        }
        vendaLista.add("_____________________________________________________________________________________");
        int qe = p.getEntradaList().size();
        Entrada e = null;
        if (qe > 0) {
           if(p.getEntradaList().size()>1) e = p.getEntradaList().get(p.getEntradaList().size() - 1);
            vendaLista.add(" Ultima Entrada: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(e.getEntradaPK().getData()) + "   Quant.: " + e.getQtd());
        } else {
            vendaLista.add(" Sem entrada no Armazem");
        }
        if (p.getSaidaList().size() > 0) {
            Saida s = p.getSaidaList().get(p.getSaidaList().size() - 1);
            vendaLista.add(" Ultima Saida: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(s.getSaidaPK().getData()) + "    Quant.: " + s.getQtd());
        } else {
            vendaLista.add(" Sem Saida no Armazem");
        }
        if (qe > 0) {
            vendaLista.add(" Ultimo Fornecedor: " + e.getIdfornecedor().getNome());
        } else {
            vendaLista.add(" Sem Fornecedores");
        }
        if (p.getVendaList().size() > 0) {
            vendaLista.add("_____________________________________________________________________________________");
            vendaLista.add(" Ultima Venda: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(p.getVendaList().get(p.getVendaList().size() - 1).getDatavenda()));
        } else {
            vendaLista.add(" Nenhuma venda efectuada");
        }
        //vendaLista.setForeground(Color.DARK_GRAY);

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
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        vendaLista = new java.awt.List();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(getLimpaVenda());
        setName("dialogProd"); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_ok.png"))); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));

        labelTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelTitle.setText("Produto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelTitle)
                .addGap(311, 311, 311))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        vendaLista.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(309, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(305, 305, 305))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(vendaLista, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
                .addComponent(jButton1))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(vendaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(29, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.getParent().setVisible(false);
        g();
    }//GEN-LAST:event_jButton1ActionPerformed

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
                FormProdVista dialog = new FormProdVista(new javax.swing.JFrame(), p, true);
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
        vendaLista.clear();
        return 1;
    }

    public void mostrarResultdo(int i, String prod, float p, int qt, float v) {
        vendaLista.add("_____________________________________________________________________________________");
        float total = p * qt;
        vendaLista.add(i + "                          Produto:  " + prod + "               Preco:  " + p + "                  Qtd: " + qt + "                   Total:  " + v);
        vendaLista.setForeground(Color.BLUE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTitle;
    private java.awt.List vendaLista;
    // End of variables declaration//GEN-END:variables
}
