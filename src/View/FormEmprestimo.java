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
import controller.DetalorgJpaController;
import controller.EmprestimoJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.ImageIcon;
import modelo.Detalorg;
import modelo.Emprestimo;
import modelo.Imposto;
import modelo.Venda;
import modelo.Vendedor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Ussimane
 */
public class FormEmprestimo extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static List<Emprestimo> p;
    private static int desc = 0;
    private static float subt = 0;
    private static float soma = 0;
    private static float de = 0;
    private static float timp;
    Detalorg dog = new Detalorg();
    JasperPrint jpPrint;
    JasperViewer jv;
    Map parametros = new HashMap<>();
    String impvenda = "";
    String impprof = "";
    int impv = 0;
     private static boolean nrcaixa = false;
     String caxa="";

    public FormEmprestimo(java.awt.Frame parent, List<Emprestimo> p, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        int i = 1;
        //float soma = 0;
        this.p = p;
        dog = new DetalorgJpaController(emf).findDetalorgEntities().get(0);
        this.setLocationRelativeTo(null);
        vendaLista.add(" " + p.get(0).getIdcliente().getNome());
        for (Emprestimo e : p) {
            float val = e.getPrec() * e.getQtprod();
            mostrarResultdo(i, e.getIdproduto().getNome(), e.getPrec(), e.getQtprod(), val);
            subt = subt + val;
            timp = timp + e.getTiva();
            de = de + e.getTdesc();
            desc = e.getDesconto();
            soma = soma + e.getTotalpagar();
            i++;
        }
        vendaLista.add(" ");
        vendaLista.add("  Total IVA:           " + timp);
        vendaLista.add("  SubTotal:            " + subt);
        vendaLista.add("  Desconto:            " + de + "(" + desc + "%)");
        vendaLista.add("  Total:       " + soma);
        //vendaLista.setForeground(Color.DARK_GRAY);
        String cpath = new File("config.properties").getAbsolutePath();
        File arquivo = new File(cpath);
        String url = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(arquivo, "rw");
            url = raf.readLine();
               if (url == null || url.equals("")) {
                JOptionPane.showMessageDialog(this, "Por favor introduza o nr de caixa para este computador\n"
                        + "O numero de caixa deve ser diferente em cada computador de venda", "Atencao", JOptionPane.WARNING_MESSAGE);
                nrcaixa = false;
            } else {
                nrcaixa = true;
            }
            caxa=url;
            String imp1 = raf.readLine();
            String imp2 = raf.readLine();
            url = raf.readLine();
            // System.out.println(url);
            if (url != null && url.equals("0")) {
                impvenda = imp1;
            } else if (url != null) {
                impvenda = imp2;
            } else {
                impvenda = "";
            }
            impprof = imp2;
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
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
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        vendaLista = new java.awt.List();
        impr = new javax.swing.JButton();

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
        labelTitle.setText("Produtos fornecidos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(273, Short.MAX_VALUE)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        vendaLista.setBackground(new java.awt.Color(255, 255, 204));
        vendaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendaListaActionPerformed(evt);
            }
        });

        impr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_print.png"))); // NOI18N
        impr.setText("Imprimir");
        impr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(jButton1)
                .addGap(79, 79, 79)
                .addComponent(impr)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(impr, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void vendaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendaListaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vendaListaActionPerformed

    private void imprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprActionPerformed
        List<Produto> lproe = new ArrayList<Produto>();
        for (Emprestimo v : p) {
            Produto prod = new Produto();
            prod.setNome(v.getIdproduto().getNome());
            prod.setPrecovenda(v.getPrec());
            prod.setQtdvenda(v.getQtprod());
            prod.setCodigo(v.getIdproduto().getCodigo());
            Imposto i = new Imposto();
            i.setPerc(desc);
            prod.setIdimposto(i);
            lproe.add(prod);
        }
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService printService = null;
        //System.out.println(printServices.length + "");
        for (PrintService ps : printServices) {
            if (ps.getName().equals(dog.getIr())) {
                printService = ps;
                break;
            }
        }
        if (printService == null) {
            JOptionPane.showMessageDialog(this, "A impressora " + dog.getIr() + "nao foi encontrada\n"
                    + "por favor configure um nome valido", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
        }
        parametros.clear();
        parametros.put("cli", p.get(0).getIdcliente().getNome());
        parametros.put("nuit", p.get(0).getIdcliente().getIdempresa().getNuite());
        parametros.put("data", p.get(0).getDataemprestimo());
        parametros.put("org", dog.getOrg());
        parametros.put("end", dog.getEnde());
        // parametros.put("dir", dog.getDir());
        parametros.put("nif", dog.getNif());
        parametros.put("cid", dog.getCid());
        parametros.put("tel", dog.getTel());
        parametros.put("tde", de + "(" + desc + "%)");
        parametros.put("tiv", timp);
        parametros.put("tvp", soma);
        String path = new File("relatorios/recibo.jasper").getAbsolutePath();
        parametros.put("ven", p.get(0).getIdvendedor().getNomecompleto());
        String imge = new File("saslogo.png").getAbsolutePath();
        parametros.put("img", imge);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lproe);
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(path, parametros, ds);
        } catch (JRException ex) {
            Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(2));
        JRExporter jrExporter = new JRPrintServiceExporter();
        jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        jrExporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        jrExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
        jrExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
                printService.getAttributes());
        jrExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        jrExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        try {
            jrExporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Erro na Impressao \n"
                    + "por favor configure a Impressora", "Atencao", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_imprActionPerformed

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
                FormEmprestimo dialog = new FormEmprestimo(new javax.swing.JFrame(), p, true);
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
        desc = 0;
        subt = 0;
        soma = 0;
        de = 0;
        timp = 0;
        return 1;
    }

    public void mostrarResultdo(int i, String prod, float p, int qt, float v) {
        vendaLista.add("_____________________________________________________________________________________");
        float total = p * qt;
        vendaLista.add(i + "                          Produto:  " + prod + "               Preco:  " + p + "                  Qtd: " + qt + "                   Total:  " + v);
        vendaLista.setForeground(Color.BLUE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton impr;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTitle;
    private java.awt.List vendaLista;
    // End of variables declaration//GEN-END:variables
}
