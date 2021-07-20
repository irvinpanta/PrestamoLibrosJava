package vista;

import controlador.GestionarClientes;
import controlador.GestionarPrestamo;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Prestamo;
import utils.Message;

public class PrestamoFrm extends javax.swing.JFrame {

    private Prestamo miPrestamo;
    private GestionarPrestamo gestionaPrestamo = new GestionarPrestamo();
    DefaultTableModel modelotablaPrestamo = new DefaultTableModel();

    private String accion = "Guardar";

    public PrestamoFrm() {
        initComponents();
        this.setLocationRelativeTo(null);

        headersTable();
        showRegistros();
        txtFechaEntrega.setText(fechaActual());

    }

    private String fechaActual() {

        String fecha = null;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fecha = dateFormat.format(date);

        return fecha;
    }

    private void diasEntreFechas() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaInicial = dateFormat.parse(fechaActual());
        Date fechaFinal = dateFormat.parse(txtFechaDevolucion.getText());

        int dias = (int) ((fechaFinal.getTime() - fechaInicial.getTime()) / 86400000);

        if (fechaFinal.getTime() < fechaInicial.getTime()) {
            Message.showMessageError("Fecha de Devolucion no debe ser menor a fecha de entrega");
            txtDias.setText("");
        } else {
            System.out.println("Hay " + dias + " dias de diferencia");
            txtDias.setText(String.valueOf(dias));
        }

    }

    private void obtenerDatos() {

        int rowSelect = tablaListado.getSelectedRow();

        String fechaPrestamo, fechaDevolucion, dias;
        int codLibro, codCliente;
        String libro, cliente;

        fechaPrestamo = tablaListado.getValueAt(rowSelect, 1).toString();
        fechaDevolucion = tablaListado.getValueAt(rowSelect, 2).toString();
        dias = tablaListado.getValueAt(rowSelect, 3).toString();
        codLibro = (int) tablaListado.getValueAt(rowSelect, 4);
        libro = tablaListado.getValueAt(rowSelect, 5).toString();
        codCliente = (int) tablaListado.getValueAt(rowSelect, 6);
        cliente = tablaListado.getValueAt(rowSelect, 7).toString();

        txtFechaEntrega.setText(String.valueOf(fechaPrestamo));
        txtFechaDevolucion.setText(String.valueOf(fechaDevolucion));
        txtDias.setText(String.valueOf(dias));
        txtCodLibro.setText(String.valueOf(codLibro));
        txtLibro.setText(String.valueOf(libro));
        txtCodCliente.setText(String.valueOf(codCliente));
        txtCliente.setText(String.valueOf(cliente));

        accion = "Modificar";

    }

    private void limpiarAll() {
        txtCliente.setText("");
        txtCodCliente.setText("");
        txtLibro.setText("");
        txtCodLibro.setText("");

        txtFechaDevolucion.setText("");
        txtFechaEntrega.setText(fechaActual());
        txtDias.setText("");

        accion = "Guardar";
    }

    private void grabarDatos() {
        try {

            int obtenerCodClientePrestamo;
            obtenerCodClientePrestamo = gestionaPrestamo.getCodigoCliente(txtCodCliente.getText());

            String fechaEntrega, fechaDevolucion;
            int dias;
            int codLibro, codCliente, codUsuario;

            fechaEntrega = txtFechaEntrega.getText().trim();
            fechaDevolucion = txtFechaDevolucion.getText().trim();
            dias = Integer.parseInt(txtDias.getText());
            codLibro = Integer.parseInt(txtCodLibro.getText());
            codCliente = Integer.parseInt(txtCodCliente.getText());
            codUsuario = 1;

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar Prestamo?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miPrestamo = new Prestamo(fechaEntrega, fechaDevolucion, dias, codLibro, codCliente, codUsuario);
                String mensaje = gestionaPrestamo.insertPrestamos(miPrestamo);

                if (obtenerCodClientePrestamo == codCliente) {
                    Message.showMessageError("Cliente ya mantiene prestamo de libro pendiente");

                } else if (mensaje.equals("exito")) {
                    Message.showMessageExito("!! Prestamo registrado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtFechaDevolucion.requestFocus();

                } else {
                    Message.showMessageExito("!! Ocurrio un error G -> " + mensaje);
                }
            }

        } catch (Exception e) {
        }
    }

    private void modificarDatos() {
        try {

            String fechaEntrega, fechaDevolucion;
            int dias;
            int codLibro, codCliente, codUsuario;

            int codPrestamo;

            int rowSelect = tablaListado.getSelectedRow();
            codPrestamo = (int) tablaListado.getValueAt(rowSelect, 0);

            int estadoPrestamo;
            estadoPrestamo = gestionaPrestamo.getEstadoPrestamo(codPrestamo);

            fechaEntrega = txtFechaEntrega.getText().trim();
            fechaDevolucion = txtFechaDevolucion.getText().trim();
            dias = Integer.parseInt(txtDias.getText());
            codLibro = Integer.parseInt(txtCodLibro.getText());
            codCliente = Integer.parseInt(txtCodCliente.getText());
            codUsuario = 1;

            if (estadoPrestamo == 1) {
                Message.showMessageError("No se puede modificar prestamo ya se encuentra devuelto");

            } else {
                int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de modificar Prestamo?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
                if (rspta == JOptionPane.YES_OPTION) {

                    miPrestamo = new Prestamo(codPrestamo, fechaEntrega, fechaDevolucion, dias, codLibro, codCliente, codUsuario);
                    String mensaje = gestionaPrestamo.updatePrestamos(miPrestamo);

                    if (mensaje.equals("exito")) {
                        Message.showMessageExito("!! Prestamo modificado correctamente !!");
                        showRegistros();
                        limpiarAll();
                        txtFechaDevolucion.requestFocus();

                    } else {
                        Message.showMessageExito("!! Ocurrio un error G -> " + mensaje);
                    }
                }

            }

        } catch (Exception e) {
        }
    }

    private void insertarRegistro() {
        try {
            boolean valCaja;

            valCaja = txtFechaEntrega.getText().length() == 0 || txtFechaDevolucion.getText().length() == 0 || txtDias.getText().length() == 0 || txtCodCliente.getText().length() == 0 || txtCodLibro.getText().length() == 0;

            if (valCaja == true) {
                Message.showMessageError("Complete todos los campos");
            } else if (accion.equals("Guardar")) {
                grabarDatos();
            } else if (accion.equals("Modificar")) {
                modificarDatos();
            }
        } catch (Exception e) {
        }
    }

    private void eliminarDatos() {
        try {

            int codPrestamo;

            int rowSelect = tablaListado.getSelectedRow();
            codPrestamo = (int) tablaListado.getValueAt(rowSelect, 0);

            int estadoPrestamo;
            estadoPrestamo = gestionaPrestamo.getEstadoPrestamo(codPrestamo);

            if (estadoPrestamo == 1) {
                Message.showMessageError("No se puede anular prestamo ya se encuentra devuelto");

            } else {

                int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de anular prestamo?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
                if (rspta == JOptionPane.YES_OPTION) {
                    miPrestamo = new Prestamo(codPrestamo);
                    String mensaje = gestionaPrestamo.deletePrestamos(miPrestamo);

                    if (mensaje.equals("exito")) {
                        Message.showMessageExito("!! Prestamo anulado correctamente !!");
                        showRegistros();
                        limpiarAll();
                        txtFechaDevolucion.requestFocus();

                    } else {
                        Message.showMessageExito("!! Ocurrio un error G -> " + mensaje);
                    }
                }

            }

        } catch (Exception e) {
        }
    }

    private void headersTable() {
        String[] headerTable = {"Codigo", "Fecha entrega", "Fecha devolucion", "Dias", "Codigo", "Libro", "Codigo", "Cliente", "Responsable", "Estado"};
        modelotablaPrestamo.setColumnIdentifiers(headerTable);
        tablaListado.setModel(modelotablaPrestamo);
    }

    private void showRegistros() {
        gestionaPrestamo.showAllPrestamos(tablaListado, modelotablaPrestamo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        panelDP2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtLibro = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFechaDevolucion = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        txtFechaEntrega = new javax.swing.JFormattedTextField();
        btnBuscarCliente = new javax.swing.JButton();
        btnBuscarLibro = new javax.swing.JButton();
        txtCliente = new javax.swing.JTextField();
        txtCodLibro = new javax.swing.JTextField();
        txtCodCliente = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDias = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtInputSearch = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnGrabar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GESTION DE PRESTAMOS");

        panel2.setBackground(new java.awt.Color(255, 255, 255));
        panel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        panel2.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        panelDP2.setBackground(new java.awt.Color(255, 255, 255));
        panelDP2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 0, 12))); // NOI18N
        panelDP2.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel7.setText("Cliente:");

        jLabel8.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel8.setText("Libro:");

        txtLibro.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        txtLibro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLibro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtLibro.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel10.setText("Fecha devolucion:");

        txtFechaDevolucion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        txtFechaDevolucion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaDevolucionKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel16.setText("Fecha Entrega:");

        txtFechaEntrega.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        txtFechaEntrega.setEnabled(false);

        btnBuscarCliente.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnBuscarCliente.setText(":::");
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        btnBuscarLibro.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnBuscarLibro.setText(":::");
        btnBuscarLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarLibroActionPerformed(evt);
            }
        });

        txtCliente.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        txtCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtCliente.setEnabled(false);

        txtCodLibro.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        txtCodLibro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodLibro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtCodLibro.setEnabled(false);

        txtCodCliente.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtCodCliente.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel17.setText("Dias:");

        txtDias.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        txtDias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDias.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtDias.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtDias.setEnabled(false);

        btnCalcular.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDP2Layout = new javax.swing.GroupLayout(panelDP2);
        panelDP2.setLayout(panelDP2Layout);
        panelDP2Layout.setHorizontalGroup(
            panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDP2Layout.createSequentialGroup()
                .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelDP2Layout.createSequentialGroup()
                        .addGap(627, 627, 627)
                        .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDP2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel16)
                            .addComponent(jLabel8))
                        .addGap(58, 58, 58)
                        .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDP2Layout.createSequentialGroup()
                                .addComponent(txtFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFechaDevolucion, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDP2Layout.createSequentialGroup()
                                .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelDP2Layout.createSequentialGroup()
                                        .addComponent(txtCodLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnBuscarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelDP2Layout.createSequentialGroup()
                                        .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtLibro))))))
                .addGap(30, 30, 30))
        );
        panelDP2Layout.setVerticalGroup(
            panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDP2Layout.createSequentialGroup()
                .addComponent(btnCalcular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtFechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarLibro))
                .addContainerGap(22, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane1)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(btnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDP2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(panelDP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        obtenerDatos();
    }//GEN-LAST:event_tablaListadoMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarDatos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        limpiarAll();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        insertarRegistro();
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        // TODO add your handling code here:

        BuscarClienteFrm bCliente = new BuscarClienteFrm(this);
        bCliente.setAlwaysOnTop(true);
        bCliente.setVisible(true);

    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnBuscarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarLibroActionPerformed
        // TODO add your handling code here:

        BuscarLibroFrm bLibro = new BuscarLibroFrm(this);
        bLibro.setAlwaysOnTop(true);
        bLibro.setVisible(true);
    }//GEN-LAST:event_btnBuscarLibroActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed

    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        try {

            if (txtFechaDevolucion.getText().length() == 0) {
                Message.showMessageError("Ingrese una fecha de devolucion");
            } else {
                diasEntreFechas();
            }

// TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(PrestamoFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void txtFechaDevolucionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaDevolucionKeyReleased

    }//GEN-LAST:event_txtFechaDevolucionKeyReleased

    private void tablaListadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaListadoKeyReleased
        // TODO add your handling code here:


    }//GEN-LAST:event_tablaListadoKeyReleased

    private void txtInputSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputSearchKeyReleased
        // TODO add your handling code here:
        String searchNombre = txtInputSearch.getText();
        gestionaPrestamo.searchPrestamos(tablaListado, modelotablaPrestamo, searchNombre);
    }//GEN-LAST:event_txtInputSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscarCliente;
    public javax.swing.JButton btnBuscarLibro;
    public javax.swing.JButton btnCalcular;
    public javax.swing.JButton btnCerrar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGrabar;
    public javax.swing.JButton btnReportes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JPanel panel2;
    public javax.swing.JPanel panelDP2;
    public javax.swing.JTable tablaListado;
    public javax.swing.JTextField txtCliente;
    public javax.swing.JTextField txtCodCliente;
    public javax.swing.JTextField txtCodLibro;
    public javax.swing.JTextField txtDias;
    private javax.swing.JFormattedTextField txtFechaDevolucion;
    private javax.swing.JFormattedTextField txtFechaEntrega;
    public javax.swing.JTextField txtInputSearch;
    public javax.swing.JTextField txtLibro;
    // End of variables declaration//GEN-END:variables
}
