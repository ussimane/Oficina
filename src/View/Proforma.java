/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static View.VendaEmprestimo.lproe;
import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.ClienteJpaController;
import controller.EmprestimoJpaController;
import controller.exceptions.NonexistentEntityException;
import java.io.File;
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
import javax.swing.JDialog;
import javax.swing.SwingWorker;
import modelo.Detalorg;
import modelo.Emprestimo;
import modelo.Venda;
import modelo.Vendedor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Ussimane
 */
public class Proforma extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static List<Produto> p;
    private static List<Produto> pv;
    private static Date d;
    private static Cliente n;
    private static String no;
    private static String nu;
    private static int v;
    private static Detalorg dog;
    private static String impprof;

    JasperPrint jpPrint;
    JasperViewer jv;
    Map parametros = new HashMap<>();

    public Proforma(java.awt.Frame parent, List<Produto> pv, List<Produto> p, Detalorg i,String imp, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        this.p = p;
        this.d = d;
        this.n = n;
        this.dog = i;
        this.impprof=imp;
        // this.nu = nu;
        this.v = v;
        this.no = no;
        this.pv = pv;
        txtIdP.setVisible(false);
        lbValidacao.setVisible(false);
        this.setLocationRelativeTo(null);
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
        txtIdP = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        lbValidacao = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtn = new javax.swing.JTextField();
        txtnu = new javax.swing.JTextField();

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
        labelTitle.setText("                                      Cliente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        lbValidacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbValidacao.setForeground(new java.awt.Color(255, 51, 51));
        lbValidacao.setText("jLabel4");

        jLabel1.setText("Nome:");

        jLabel2.setText("Nuit:");

        txtn.setText(" ");

        txtnu.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtnu, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                                    .addComponent(txtn))))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbValidacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//       new SwingWorker() {
//            @Override
//            protected Object doInBackground() throws Exception {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        PrintService printService = null;
        //System.out.println(printServices.length + "");
        for (PrintService ps : printServices) {
            if (ps.getName().equals(impprof)) {
                printService = ps;
                break;
            }
        }
        if (printService == null) {
            JOptionPane.showMessageDialog(this, "A impressora " + impprof + "nao foi encontrada\n"
                    + "por favor configure um nome valido", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int desc = 0;
        float tde = 0;
        float tvp = 0;
        float tiv = 0;
        for (Produto p : lproe) {
            float valo = p.getPrecovenda() * p.getQtdvenda();
            float de = new Double(Math.ceil((new Integer(desc).floatValue() / 100) * valo)).floatValue();
            float vp = valo - de;
            tde = tde + de;
            tvp = tvp + vp;
            tiv = tiv + (p.getPrecovenda() * p.getQtdvenda() - (p.getMargem() * p.getCusto() * p.getQtdvenda()));
        }

        String pl = new File("saslogo.png").getAbsolutePath();
        parametros.clear();
        parametros.put("cli", txtn.getText());// txtClient.getText());
        parametros.put("nuit", txtnu.getText());
        parametros.put("img", pl);       
        parametros.put("data", d);
        parametros.put("org", dog.getOrg());
        parametros.put("end", dog.getEnde());
        //parametros.put("dir", dog.getDir());
        parametros.put("nif", dog.getNif());
        parametros.put("cid", dog.getCid());
        parametros.put("tel", dog.getTel());
        parametros.put("tde", tde + "(" + desc + "%)");
        parametros.put("tiv", tiv);
        parametros.put("tvp", tvp);
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lproe);
        String path = new File("relatorios/proproforma.jasper").getAbsolutePath();
//        try {
////                    JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametros, ds);
////                    JasperPrintManager.printPage(jasperPrint, 0, false);
//            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
//            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
//            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Factura Proforma", true);
//            viewer.setSize(1024, 768);
//            viewer.setLocationRelativeTo(null);
//            viewer.getContentPane().add(jv.getContentPane());
//            viewer.setVisible(true);
//        } catch (JRException ex) {
//            Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
          JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(path, parametros, ds);
        } catch (JRException ex) {
            Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
        }
            // JasperPrintManager.printPage(jasperPrint, 0, false);
//                    JasperPrintManager.printPage(jasperPrint, 0, false);
        //  PrinterJob job = PrinterJob.getPrinterJob();

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
//                return null;
//            }
//
//            @Override
//            protected void done() {
//            }
//        }.execute();
        JOptionPane.showMessageDialog(this, "Proforma efectuada com sucesso");
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed
    public void limpar() {
        txtn.setText("");
        txtnu.setText("");
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
                Proforma dialog = new Proforma(new javax.swing.JFrame(), pv, p, dog,impprof, true);
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
        limpar();
        return 1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel lbValidacao;
    private javax.swing.JTextField txtIdP;
    private javax.swing.JTextField txtn;
    private javax.swing.JTextField txtnu;
    // End of variables declaration//GEN-END:variables
}
