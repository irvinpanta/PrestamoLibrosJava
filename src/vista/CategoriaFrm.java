package vista;

import controlador.GestionarCategoria;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Categoria;
import utils.Message;
import utils.Validate;

public class CategoriaFrm extends javax.swing.JFrame {

    private Categoria miCategoria;
    private GestionarCategoria gestionaCategoria = new GestionarCategoria();
    DefaultTableModel modeloTablaCategoria = new DefaultTableModel();
    
    private Validate valida = new Validate();
    

    private String accion = "Guardar";

    public CategoriaFrm() {
        initComponents();
        this.setLocationRelativeTo(null);

        headersTable();
        showRegistros();
    }

    private void limpiarAll() {
        txtNombCategoria.setText("");
        txtDescripcion.setText("");
        accion = "Guardar";
        checkEstado.isSelected();
        checkEstado.setEnabled(false);
    }

    private void obtenerDatos() {
        try {
            int rowSelect = tablaListado.getSelectedRow();

            String nombCategoria, descripCategoria;
            boolean estado;

            nombCategoria = tablaListado.getValueAt(rowSelect, 1).toString();
            descripCategoria = tablaListado.getValueAt(rowSelect, 2).toString();
            String formatoEstado = tablaListado.getValueAt(rowSelect, 3).toString();
            estado = (formatoEstado.equals("Activo")) ? true : false;

            /*cajaNombre.setText(nombreTurno);*/
            if (estado == true) {
                checkEstado.setSelected(true);
                checkEstado.setEnabled(false);
            } else {
                checkEstado.setSelected(false);
                checkEstado.setEnabled(true);
            }

            txtNombCategoria.setText(nombCategoria);
            txtDescripcion.setText(descripCategoria);

            accion = "Modificar";
        } catch (Exception e) {
        }

    }

    private void grabarDatos() {
        try {

            String nombCategoria, DescripCategoria;

            nombCategoria = txtNombCategoria.getText().trim();
            DescripCategoria = txtDescripcion.getText().trim();

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar la categoria?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miCategoria = new Categoria(nombCategoria, DescripCategoria);
                String mensaje = gestionaCategoria.insertCategoria(miCategoria);

                if (mensaje.equals("exito")) {
                    Message.showMessageExito("!! Categoria registrado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtNombCategoria.requestFocus();

                } else {
                    Message.showMessageExito("!! Ocurrio un error -> " + mensaje);
                }
            }

        } catch (Exception e) {
        }

    }

    private void modificarDatos() {
        try {
            String nombCategoria, DescripCategoria;
            int codCategoria = 0;
            boolean estado;

            int rowSelect = tablaListado.getSelectedRow();
            codCategoria = (int) tablaListado.getValueAt(rowSelect, 0);

            nombCategoria = txtNombCategoria.getText().trim();
            DescripCategoria = txtDescripcion.getText().trim();

            if (checkEstado.isSelected()) {
                estado = true;
            } else {
                estado = false;
            }

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de modificar categoria?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miCategoria = new Categoria(codCategoria, nombCategoria, DescripCategoria, estado);
                String mensaje = gestionaCategoria.updateCategoria(miCategoria);

                if (mensaje.equals("exito")) {

                    Message.showMessageExito("!! Categoria modificado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtNombCategoria.requestFocus();

                } else {
                    Message.showMessageExito("!! Ocurrio un error -> " + mensaje);
                }
            }

        } catch (Exception e) {
        }

    }

    private void eliminarDatos() {
        try {
            int codCategoria = 0;

            int rowSelect = tablaListado.getSelectedRow();
            codCategoria = (int) tablaListado.getValueAt(rowSelect, 0);

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar categoria?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miCategoria = new Categoria(codCategoria);
                String mensaje = gestionaCategoria.deleteEmpleado(miCategoria);

                if (mensaje.equals("exito")) {

                    Message.showMessageExito("!! Categoria eliminado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtNombCategoria.requestFocus();

                } else {
                    Message.showMessageExito("!! Ocurrio un error -> " + mensaje);
                }
            }

        } catch (Exception e) {
        }

    }

    private void insertarRegistros() {
        try {
            boolean valCajas;

            valCajas = txtNombCategoria.getText().length() == 0 || txtDescripcion.getText().length() == 0;

            if (valCajas == true) {
                JOptionPane.showMessageDialog(null, "Complete campos");
            } else if (accion.equals("Guardar")) {
                grabarDatos();
            } else if (accion.equals("Modificar")) {
                modificarDatos();
            }
        } catch (Exception e) {
        }

    }

    private void headersTable() {
        String[] headerTable = {"Codigo", "Nombre Categoria", "Descripcion Categoria", "Estado"};
        modeloTablaCategoria.setColumnIdentifiers(headerTable);
        tablaListado.setModel(modeloTablaCategoria);
    }

    private void showRegistros() {
        gestionaCategoria.showAllCategoria(tablaListado, modeloTablaCategoria);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        panelDP = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombCategoria = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        checkEstado = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtInputSearch = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnGrabar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GESTION DE CATEGORIAS");

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        panel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        panelDP.setBackground(new java.awt.Color(255, 255, 255));
        panelDP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 0, 12))); // NOI18N
        panelDP.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel2.setText("Nombre Categoria:");

        txtNombCategoria.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtNombCategoria.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombCategoria.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtNombCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombCategoriaKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel3.setText("Descripcion.");

        jLabel13.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel13.setText("Estado :");

        checkEstado.setBackground(new java.awt.Color(255, 255, 255));
        checkEstado.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        checkEstado.setSelected(true);
        checkEstado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        checkEstado.setEnabled(false);
        checkEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEstadoActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

        javax.swing.GroupLayout panelDPLayout = new javax.swing.GroupLayout(panelDP);
        panelDP.setLayout(panelDPLayout);
        panelDPLayout.setHorizontalGroup(
            panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel13))
                .addGap(10, 10, 10)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkEstado)
                    .addComponent(txtNombCategoria)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDPLayout.setVerticalGroup(
            panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDPLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtNombCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDPLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)))
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)))
        );

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

        jLabel11.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel11.setText("Nombre Categoria: :");

        txtInputSearch.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtInputSearch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInputSearch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtInputSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtInputSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtInputSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(txtInputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtInputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEliminar.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnCerrar.setText("Limpiar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnGrabar.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnGrabar.setText("Grabar");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });

        btnReportes.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnReportes.setText("Reportes");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(btnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGrabar)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCerrar)
                    .addComponent(btnReportes))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        insertarRegistros();

    }//GEN-LAST:event_btnGrabarActionPerformed

    private void checkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEstadoActionPerformed
        // TODO add your handling code here:

        if (checkEstado.isSelected()) {
            System.out.println("activo");
        } else {
            System.out.println("inactivo");
        }
    }//GEN-LAST:event_checkEstadoActionPerformed

    private void tablaListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaListadoMouseClicked

        obtenerDatos();
    }//GEN-LAST:event_tablaListadoMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        limpiarAll();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarDatos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtInputSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputSearchKeyReleased
        // TODO add your handling code here:
        String searchNombre = txtInputSearch.getText();
        gestionaCategoria.searchCategoria(tablaListado, modeloTablaCategoria, searchNombre);
    }//GEN-LAST:event_txtInputSearchKeyReleased

    private void txtNombCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombCategoriaKeyTyped
        // TODO add your handling code here:
        valida.writeLetters(evt);
    }//GEN-LAST:event_txtNombCategoriaKeyTyped

    private void txtInputSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputSearchKeyTyped
        // TODO add your handling code here:
        valida.writeLetters(evt);
    }//GEN-LAST:event_txtInputSearchKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCerrar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGrabar;
    public javax.swing.JButton btnReportes;
    public javax.swing.JCheckBox checkEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JPanel panel;
    public javax.swing.JPanel panelDP;
    public javax.swing.JTable tablaListado;
    private javax.swing.JTextArea txtDescripcion;
    public javax.swing.JTextField txtInputSearch;
    public javax.swing.JTextField txtNombCategoria;
    // End of variables declaration//GEN-END:variables
}
