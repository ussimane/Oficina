package View;

import controller.DetalorgJpaController;
import controller.ProdutoJpaController;
import controller.SeriefacturaJpaController;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
import modelo.Detalorg;
import modelo.Produto;
import modelo.Seriefactura;

/**
 *
 * @author rjose
 */
public final class Configuracao extends javax.swing.JFrame {

    int t = 0;
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    Seriefactura sf = new Seriefactura();
    Detalorg dog = new Detalorg();
    String i55="";
    String ia4="";

    @Override
    public void list() {
        super.list();
    }

    public Configuracao() {
        initComponents();
        this.setLocation(40, 225);
        // this.setResizable(false);
        this.setTitle("Configuracao");
        txtserie.setDocument(new ValidarInteiro());
        caixa.setDocument(new ValidarInteiro());
        sf = new SeriefacturaJpaController(emf).findSeriefacturaEntities().get(0);
        dog = new DetalorgJpaController(emf).findDetalorgEntities().get(0);
        txtserie.setText(sf.getSeriefactura());

        txorg.setText(dog.getOrg());
        txend.setText(dog.getEnde());
        txdir.setText(dog.getDir());
        txnif.setText(dog.getNif());
        txcid.setText(dog.getCid());
        txtel.setText(dog.getTel());
        //   txir.setText(dog.getIr());
//        txia.setText(dog.getIa());

        txorg.setEditable(false);
        txend.setEditable(false);
        txdir.setEditable(false);
        txnif.setEditable(false);
        txcid.setEditable(false);
        txtel.setEditable(false);

        txir.setEditable(false);
        txia.setEditable(false);

        txtserie.setEditable(false);
        btaceitar.setEnabled(false);
        btcanc.setEnabled(false);
        btaceitar1.setEnabled(false);
        btcanc1.setEnabled(false);
        btaceitar2.setEnabled(false);
        btcanc2.setEnabled(false);
        // cbimp.removeAllItems();
        //  File arquivo = new File("Farmacia/META-INF/config.properties");
        String cpath = new File("config.properties").getAbsolutePath();
        File arquivo = new File(cpath);
        String url = null;
        cbimp.removeAllItems();
        cbimp.addItem("Impressora 55-80mm");
        cbimp.addItem("Impressora A4");
        try {
            RandomAccessFile raf = new RandomAccessFile(arquivo, "rw");
            url = raf.readLine();
            caixa.setText(url);
            url = raf.readLine();
            i55=url;
            txir.setText(url);
            url = raf.readLine();
            ia4=url;
            txia.setText(url);
            url = raf.readLine();
            if (url!=null&&url.equals("1")) {
                cbimp.setSelectedIndex(1);
            } else {
                cbimp.setSelectedIndex(0);
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //  buttonColumn.setMnemonic(keyEvent.VK_D);
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
        txtserie = new javax.swing.JTextField();
        bteditserie = new javax.swing.JButton();
        btcanc = new javax.swing.JButton();
        btaceitar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txorg = new javax.swing.JTextField();
        txend = new javax.swing.JTextField();
        txnif = new javax.swing.JTextField();
        txtel = new javax.swing.JTextField();
        txcid = new javax.swing.JTextField();
        txdir = new javax.swing.JTextField();
        bteditserie1 = new javax.swing.JButton();
        btcanc1 = new javax.swing.JButton();
        btaceitar1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txir = new javax.swing.JTextField();
        txia = new javax.swing.JTextField();
        bteditserie2 = new javax.swing.JButton();
        btcanc2 = new javax.swing.JButton();
        btaceitar2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cbimp = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        caixa = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Numero Actual de Factura"));

        txtserie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtserieActionPerformed(evt);
            }
        });

        bteditserie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        bteditserie.setText("Actualizar");
        bteditserie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteditserieActionPerformed(evt);
            }
        });

        btcanc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"))); // NOI18N
        btcanc.setText("Cancelar");
        btcanc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcancActionPerformed(evt);
            }
        });

        btaceitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_ok.png"))); // NOI18N
        btaceitar.setText("Aceitar");
        btaceitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btaceitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btaceitar)
                        .addGap(27, 27, 27)
                        .addComponent(btcanc))
                    .addComponent(txtserie, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91)
                .addComponent(bteditserie)
                .addGap(60, 60, 60))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtserie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bteditserie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btaceitar)
                    .addComponent(btcanc))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Detalhes de Factura"));

        jLabel1.setText("Organizacao:");

        jLabel2.setText("Endereco:");

        jLabel3.setText("NIF:");

        jLabel4.setText("Tel.:");

        jLabel5.setText("Cidade:");

        jLabel6.setText("Director:");

        bteditserie1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        bteditserie1.setText("Actualizar");
        bteditserie1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteditserie1ActionPerformed(evt);
            }
        });

        btcanc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"))); // NOI18N
        btcanc1.setText("Cancelar");
        btcanc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcanc1ActionPerformed(evt);
            }
        });

        btaceitar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_ok.png"))); // NOI18N
        btaceitar1.setText("Aceitar");
        btaceitar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btaceitar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txnif, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                .addComponent(txend, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txorg, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txcid, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                    .addComponent(txtel)
                                    .addComponent(txdir))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bteditserie1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(btaceitar1)
                        .addGap(27, 27, 27)
                        .addComponent(btcanc1)))
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txorg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txnif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bteditserie1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txcid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btaceitar1)
                    .addComponent(btcanc1)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Impressoras"));

        jLabel7.setText("Impressora A4");

        jLabel8.setText("Impressora 50-80mm");

        bteditserie2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        bteditserie2.setText("Actualizar");
        bteditserie2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteditserie2ActionPerformed(evt);
            }
        });

        btcanc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"))); // NOI18N
        btcanc2.setText("Cancelar");
        btcanc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcanc2ActionPerformed(evt);
            }
        });

        btaceitar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_ok.png"))); // NOI18N
        btaceitar2.setText("Aceitar");
        btaceitar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btaceitar2ActionPerformed(evt);
            }
        });

        jLabel9.setText("Impressora de Recibo");

        cbimp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setText("Numero da Caixa");

        caixa.setText(" ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txia)
                            .addComponent(txir, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                        .addGap(53, 53, 53)
                        .addComponent(bteditserie2)
                        .addGap(59, 59, 59))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbimp, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(caixa, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btaceitar2)
                                .addGap(18, 18, 18)
                                .addComponent(btcanc2)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bteditserie2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbimp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(caixa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btaceitar2)
                            .addComponent(btcanc2)))))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.getAccessibleContext().setAccessibleName("Outras Configuracoes");

        jPanel10.setBackground(new java.awt.Color(255, 204, 102));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
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
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {

        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtserieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtserieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtserieActionPerformed

    private void bteditserieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteditserieActionPerformed
        txtserie.setEditable(true);
        btaceitar.setEnabled(true);
        btcanc.setEnabled(true);
    }//GEN-LAST:event_bteditserieActionPerformed

    private void btcancActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcancActionPerformed
        txtserie.setText(sf.getSeriefactura());
        txtserie.setEditable(false);
        btaceitar.setEnabled(false);
        btcanc.setEnabled(false);
    }//GEN-LAST:event_btcancActionPerformed

    private void btaceitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btaceitarActionPerformed
        if (txtserie.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Por favor insira um numero", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int respo = JOptionPane.showOptionDialog(null, "{Pretende modificar o Numero de Factura?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"SIM", "NÃO"}, null);
        if (respo == 1 || respo == -1) {
            return;
        }
        sf.setIdf(1);
        sf.setSeriefactura(txtserie.getText());
        try {
            new SeriefacturaJpaController(emf).edit(sf);
            txtserie.setEditable(false);
            btaceitar.setEnabled(false);
            btcanc.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Modificado com sucesso");
        } catch (Exception ex) {
            Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btaceitarActionPerformed

    private void bteditserie1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteditserie1ActionPerformed
        txorg.setEditable(true);
        txend.setEditable(true);
        txdir.setEditable(true);
        txnif.setEditable(true);
        txcid.setEditable(true);
        txtel.setEditable(true);
        btaceitar1.setEnabled(true);
        btcanc1.setEnabled(true);
    }//GEN-LAST:event_bteditserie1ActionPerformed

    private void bteditserie2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteditserie2ActionPerformed
        txir.setEditable(true);
        txia.setEditable(true);
        btaceitar2.setEnabled(true);
        btcanc2.setEnabled(true);
    }//GEN-LAST:event_bteditserie2ActionPerformed

    private void btcanc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcanc1ActionPerformed
        txorg.setText(dog.getOrg());
        txend.setText(dog.getEnde());
        txdir.setText(dog.getDir());
        txnif.setText(dog.getNif());
        txcid.setText(dog.getCid());
        txtel.setText(dog.getTel());
        txorg.setEditable(false);
        txend.setEditable(false);
        txdir.setEditable(false);
        txnif.setEditable(false);
        txcid.setEditable(false);
        txtel.setEditable(false);
        btaceitar1.setEnabled(false);
        btcanc1.setEnabled(false);
    }//GEN-LAST:event_btcanc1ActionPerformed

    private void btaceitar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btaceitar1ActionPerformed
        int respo = JOptionPane.showOptionDialog(null, "{Pretende modificar os Detalhes de Factura?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"SIM", "NÃO"}, null);
        if (respo == 1 || respo == -1) {
            return;
        }
        dog.setOrg(txorg.getText());
        dog.setEnde(txend.getText());
        dog.setDir(txdir.getText());
        dog.setNif(txnif.getText());
        dog.setCid(txcid.getText());
        dog.setTel(txtel.getText());
        try {
            new DetalorgJpaController(emf).edit(dog);
            txorg.setEditable(false);
            txend.setEditable(false);
            txdir.setEditable(false);
            txnif.setEditable(false);
            txcid.setEditable(false);
            txtel.setEditable(false);
            btaceitar1.setEnabled(false);
            btcanc1.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Modificado com sucesso");
        } catch (Exception ex) {
            Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btaceitar1ActionPerformed

    private void btcanc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcanc2ActionPerformed
        txir.setText(i55);
        txia.setText(ia4);
        txir.setEditable(false);
        txia.setEditable(false);
        btaceitar2.setEnabled(false);
        btcanc2.setEnabled(false);
    }//GEN-LAST:event_btcanc2ActionPerformed

    private void btaceitar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btaceitar2ActionPerformed
        int respo = JOptionPane.showOptionDialog(null, "{Pretende modificar os Nomes das Impressoras?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"SIM", "NÃO"}, null);
        if (respo == 1 || respo == -1) {
            return;
        }
//        dog.setIa(txia.getText());
//        dog.setIr(txir.getText());
        try {
//            new DetalorgJpaController(emf).edit(dog);
//            txia.setEditable(false);
//            txir.setEditable(false);
//            btaceitar2.setEnabled(false);
//            btcanc2.setEnabled(false);
            //  File arquivo = new File("Farmacia/META-INF/config.properties");
            String cpath = new File("config.properties").getAbsolutePath();
            File arquivo = new File(cpath);
            String url = null;
            try {
                RandomAccessFile raf = new RandomAccessFile(arquivo, "rw");
                url=caixa.getText();
                raf.write(url.getBytes());
                raf.writeBytes("\n");
                url = txir.getText();
                if(url!=null)raf.write(url.getBytes());
                raf.writeBytes("\n");
                url = txia.getText();
                if(url!=null)raf.write(url.getBytes());
                raf.writeBytes("\n");
                url = cbimp.getSelectedIndex()+"";
                if(url!=null)raf.write(url.getBytes());
                raf.writeBytes("\n");
                raf.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Modificado com sucesso");
        } catch (Exception ex) {
            Logger.getLogger(ConsultasInternas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btaceitar2ActionPerformed

    @SuppressWarnings("unchecked")

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btaceitar;
    private javax.swing.JButton btaceitar1;
    private javax.swing.JButton btaceitar2;
    private javax.swing.JButton btcanc;
    private javax.swing.JButton btcanc1;
    private javax.swing.JButton btcanc2;
    private javax.swing.JButton bteditserie;
    private javax.swing.JButton bteditserie1;
    private javax.swing.JButton bteditserie2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JTextField caixa;
    private javax.swing.JComboBox cbimp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txcid;
    private javax.swing.JTextField txdir;
    private javax.swing.JTextField txend;
    private javax.swing.JTextField txia;
    private javax.swing.JTextField txir;
    private javax.swing.JTextField txnif;
    private javax.swing.JTextField txorg;
    private javax.swing.JTextField txtel;
    private javax.swing.JTextField txtserie;
    // End of variables declaration//GEN-END:variables
}
