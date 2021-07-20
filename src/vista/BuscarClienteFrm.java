package vista;

import controlador.GestionarClientes;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class BuscarClienteFrm extends javax.swing.JFrame {

    private Cliente miCliente;
    private GestionarClientes gestionaCliente = new GestionarClientes();
    DefaultTableModel modelotablaCliente = new DefaultTableModel();

    PrestamoFrm pre;

    public BuscarClienteFrm() {
        initComponents();
        //this.setLocationRelativeTo(null);
    }

    public BuscarClienteFrm(PrestamoFrm pre) {
        initComponents();
        this.setLocationRelativeTo(null);

        headersTable();
        showRegistros();

        this.pre = pre;
    }

    private void obtenerDatos() {

        int rowSelect = tablaListado.getSelectedRow();

        int codCliente;
        String dni, nombres, apellidos;

        codCliente = (int) tablaListado.getValueAt(rowSelect, 0);
        dni = tablaListado.getValueAt(rowSelect, 1).toString();
        nombres = tablaListado.getValueAt(rowSelect, 2).toString();
        apellidos = tablaListado.getValueAt(rowSelect, 3).toString();

        pre.txtCodCliente.setText(String.valueOf(codCliente));
        pre.txtCliente.setText(String.valueOf(dni + " - " + nombres + " " + apellidos));

    }

    private void headersTable() {
        String[] headerTable = {"Codigo", "Dni", "Nombres", "Apellidos", "Direccion", "Fecha Nac", "Celular", "Email"};
        modelotablaCliente.setColumnIdentifiers(headerTable);
        tablaListado.setModel(modelotablaCliente);
    }

    private void showRegistros() {
        gestionaCliente.showAllCliente(tablaListado, modelotablaCliente);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtInputSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnCancelar.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAceptar.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        obtenerDatos();
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void tablaListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaListadoMouseClicked
        obtenerDatos();
    }//GEN-LAST:event_tablaListadoMouseClicked

    private void txtInputSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputSearchKeyReleased
        // TODO add your handling code here:
        String searchNombre = txtInputSearch.getText();
        gestionaCliente.searchCliente(tablaListado, modelotablaCliente, searchNombre);
    }//GEN-LAST:event_txtInputSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAceptar;
    public javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tablaListado;
    public javax.swing.JTextField txtInputSearch;
    // End of variables declaration//GEN-END:variables
}
