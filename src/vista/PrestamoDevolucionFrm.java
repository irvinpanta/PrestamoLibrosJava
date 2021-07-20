package vista;

import controlador.GestionarPrestamo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Prestamo;
import utils.Message;

public class PrestamoDevolucionFrm extends javax.swing.JFrame {

    private Prestamo miPrestamo;
    private GestionarPrestamo gestionaPrestamo = new GestionarPrestamo();
    DefaultTableModel modelotablaPrestamo = new DefaultTableModel();

    public PrestamoDevolucionFrm() {
        initComponents();
        this.setLocationRelativeTo(null);

        headersTable();
        showRegistros();
    }

    private void headersTable() {
        String[] headerTable = {"Codigo", "Fecha entrega", "Fecha devolucion", "Dias", "Codigo", "Libro", "Codigo", "Cliente", "Responsable", "Estado"};
        modelotablaPrestamo.setColumnIdentifiers(headerTable);
        tablaListado.setModel(modelotablaPrestamo);
    }

    private void showRegistros() {
        gestionaPrestamo.showAllPrestamosPendientes(tablaListado, modelotablaPrestamo);
    }

    private void grabarDevolucion() {
        try {

            int codPrestamo;

            int rowSelect = tablaListado.getSelectedRow();
            codPrestamo = (int) tablaListado.getValueAt(rowSelect, 0);
            
            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar devolucion de libro?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miPrestamo = new Prestamo(codPrestamo);
                String mensaje = gestionaPrestamo.PrestamosDevolucionGrabar(miPrestamo);

                if (mensaje.equals("exito")) {
                    Message.showMessageExito("!! Devolucion grabada correctamente !!");
                    showRegistros();

                } else {
                    Message.showMessageExito("!! Ocurrio un error G -> " + mensaje);
                }
            }

        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtInputSearch = new javax.swing.JTextField();
        btnGrabar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GESTION DE DEVOLUCIÓN");

        panel2.setBackground(new java.awt.Color(255, 255, 255));
        panel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        panel2.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        tablaListado.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tablaListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tablaListado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaListadoMouseClicked(evt);
            }
        });
        tablaListado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaListadoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaListado);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 0, 12))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel12.setText("Filtrar:");

        txtInputSearch.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtInputSearch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInputSearch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtInputSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInputSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(txtInputSearch)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtInputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGrabar.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnGrabar.setText("Devolver");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });

        btnReportes.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnReportes.setText("Cancelar");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(btnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGrabar)
                    .addComponent(btnReportes))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaListadoMouseClicked

    }//GEN-LAST:event_tablaListadoMouseClicked

    private void tablaListadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaListadoKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tablaListadoKeyReleased

    private void txtInputSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputSearchKeyReleased

        String searchNombre = txtInputSearch.getText();
        gestionaPrestamo.searchPrestamosPendientes(tablaListado, modelotablaPrestamo, searchNombre);
    }//GEN-LAST:event_txtInputSearchKeyReleased

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        grabarDevolucion();
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed

    }//GEN-LAST:event_btnReportesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGrabar;
    public javax.swing.JButton btnReportes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JPanel panel2;
    public javax.swing.JTable tablaListado;
    public javax.swing.JTextField txtInputSearch;
    // End of variables declaration//GEN-END:variables
}
