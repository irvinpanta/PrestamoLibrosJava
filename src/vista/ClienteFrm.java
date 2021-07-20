package vista;

import com.sun.org.apache.xerces.internal.util.XMLChar;
import controlador.GestionarClientes;
import controlador.GestionarPrestamo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Categoria;
import modelo.Cliente;
import utils.Message;
import utils.Validate;

public class ClienteFrm extends javax.swing.JFrame {

    private Cliente miCliente;
    private GestionarClientes gestionaCliente = new GestionarClientes();
    DefaultTableModel modelotablaCliente = new DefaultTableModel();
    
    private Validate valida = new Validate();
    
    
    
    
    private GestionarPrestamo gestionaPrestamo = new GestionarPrestamo();

    private String accion = "Guardar";

    public ClienteFrm() {
        initComponents();
        this.setLocationRelativeTo(null);

        headersTable();
        showRegistros();
        txtFechaNac.setText("2000-01-01");
    }

    private void limpiarAll() {
        txtApellidos.setText("");
        txtCelular.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtNombres.setText("");
        txtDireccion.setText("");
        txtFechaNac.setText("2000-01-01");
        checkEstado.isSelected();
        checkEstado.setEnabled(false);

        accion = "Guardar";
        System.out.println("Guardar");
    }

    private void obtenerDatos() {

        int rowSelect = tablaListado.getSelectedRow();

        String dni, nombres, apellidos, direccion, celular, email;
        String fechaNac;
        boolean estado;

        dni = tablaListado.getValueAt(rowSelect, 1).toString();
        nombres = tablaListado.getValueAt(rowSelect, 2).toString();
        apellidos = tablaListado.getValueAt(rowSelect, 3).toString();
        direccion = tablaListado.getValueAt(rowSelect, 4).toString();
        fechaNac = tablaListado.getValueAt(rowSelect, 5).toString();
        celular = tablaListado.getValueAt(rowSelect, 6).toString();
        email = tablaListado.getValueAt(rowSelect, 7).toString();

        String formatoEstado = tablaListado.getValueAt(rowSelect, 8).toString();
        estado = (formatoEstado.equals("Activo")) ? true : false;

        if (estado == true) {
            checkEstado.setSelected(true);
            checkEstado.setEnabled(false);
        } else {
            checkEstado.setSelected(false);
            checkEstado.setEnabled(true);
        }

        txtDni.setText(dni);
        txtNombres.setText(nombres);
        txtApellidos.setText(apellidos);
        txtDireccion.setText(direccion);
        txtCelular.setText(celular);
        txtEmail.setText(email);
        txtFechaNac.setText(fechaNac);

        accion = "Modificar";
        System.out.println("Modificar");
    }

    private void grabarDatos() {
        try {

            String dni, nombres, apellidos, direccion, celular, email;
            String fechaNac;

            dni = txtDni.getText().trim();
            nombres = txtNombres.getText().trim();
            apellidos = txtApellidos.getText().trim();
            direccion = txtDireccion.getText().trim();
            fechaNac = txtFechaNac.getText().trim();
            celular = txtCelular.getText().trim();
            email = txtEmail.getText().trim();

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar cliente?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miCliente = new Cliente(dni, nombres, apellidos, direccion, fechaNac, celular, email);
                String mensaje = gestionaCliente.insertCliente(miCliente);

                if (mensaje.equals("exito")) {
                    Message.showMessageExito("!! Cliente registrado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtDni.requestFocus();

                } else {
                    Message.showMessageExito("!! Ocurrio un error G -> " + mensaje);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void modificarDatos() {
        try {
            int codcliente;
            String dni, nombres, apellidos, direccion, celular, email;
            String fechaNac;
            boolean estado;

            int rowSelect = tablaListado.getSelectedRow();
            codcliente = (int) tablaListado.getValueAt(rowSelect, 0);

            dni = txtDni.getText().trim();
            nombres = txtNombres.getText().trim();
            apellidos = txtApellidos.getText().trim();
            direccion = txtDireccion.getText().trim();
            celular = txtCelular.getText().trim();
            email = txtEmail.getText().trim();

            fechaNac = txtFechaNac.getText().trim();

            if (checkEstado.isSelected()) {
                estado = true;
            } else {
                estado = false;
            }

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de modificar cliente?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miCliente = new Cliente(codcliente, dni, nombres, apellidos, direccion, fechaNac, celular, email, estado);
                String mensaje = gestionaCliente.updateCliente(miCliente);

                if (mensaje.equals("exito")) {
                    Message.showMessageExito("!! Cliente modificado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtDni.requestFocus();

                } else {
                    Message.showMessageExito("!! Ocurrio un M error -> " + mensaje);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void eliminarDatos() {
        try {

            int codcliente = 0;

            int rowSelect = tablaListado.getSelectedRow();
            codcliente = (int) tablaListado.getValueAt(rowSelect, 0);
            
            int obtenerCodClientePrestamo;
            obtenerCodClientePrestamo = gestionaPrestamo.getCodigoCliente(String.valueOf(codcliente));
            
            if (obtenerCodClientePrestamo == codcliente){
                Message.showMessageError("Clinete no se puede eliminar mantiene libro prestado");
                
            }else if (rowSelect < 0) {
                Message.showMessageError("No se ha seleccionado registro para eliminar");
            } else if (tablaListado.getRowCount() == 0) {
                Message.showMessageError("Tabla sin registros para eliminar");

            } else {

                int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar cliente?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
                if (rspta == JOptionPane.YES_OPTION) {
                    miCliente = new Cliente(codcliente);
                    String mensaje = gestionaCliente.deleteCliente(miCliente);

                    if (mensaje.equals("exito")) {
                        Message.showMessageExito("!! Cliente eliminado correctamente !!");
                        showRegistros();
                        limpiarAll();

                    } else {
                        Message.showMessageExito("!! Ocurrio un error -> " + mensaje);
                    }
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void insertarRegistros() {
        try {

            boolean valCajas;

            valCajas = txtDni.getText().length() == 0 || txtNombres.getText().length() == 0 || txtApellidos.getText().length() == 0 || txtDireccion.getText().length() == 0;

            if (valCajas == true) {
                Message.showMessageError("Complete los campos");

            } else if (txtDni.getText().length() < 8 || txtDni.getText().length() > 8) {
                Message.showMessageError("No es un dni valido");
            } else if (txtNombres.getText().length() < 3) {
                Message.showMessageError("No es un nombre valido");

            } else if (accion.equals("Guardar")) {
                grabarDatos();
            } else if (accion.equals("Modificar")) {
                modificarDatos();
            }

        } catch (Exception e) {
        }
    }

    private void headersTable() {
        String[] headerTable = {"Codigo", "Dni", "Nombres", "Apellidos", "Direccion", "Fecha Nac", "Celular", "Email", "Estado"};
        modelotablaCliente.setColumnIdentifiers(headerTable);
        tablaListado.setModel(modelotablaCliente);
    }

    private void showRegistros() {
        gestionaCliente.showAllCliente(tablaListado, modelotablaCliente);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        panelDP = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        checkEstado = new javax.swing.JCheckBox();
        txtNombres = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFechaNac = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
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
        jLabel1.setText("GESTION DE CLIENTES");

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        panel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        panelDP.setBackground(new java.awt.Color(255, 255, 255));
        panelDP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 0, 12))); // NOI18N
        panelDP.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel2.setText("Dni:");

        txtDni.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtDni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDni.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel3.setText("Nombres:");

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

        txtNombres.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtNombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombres.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombresActionPerformed(evt);
            }
        });
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombresKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel4.setText("Apellidos:");

        txtApellidos.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtApellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApellidos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel5.setText("Direccion");

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel6.setText("Fecha Nacimiento");

        txtFechaNac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        txtFechaNac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaNacKeyTyped(evt);
            }
        });

        txtDireccion.setColumns(20);
        txtDireccion.setRows(5);
        jScrollPane2.setViewportView(txtDireccion);

        jLabel7.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel7.setText("Celular:");

        txtCelular.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtCelular.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelular.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel8.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));

        javax.swing.GroupLayout panelDPLayout = new javax.swing.GroupLayout(panelDP);
        panelDP.setLayout(panelDPLayout);
        panelDPLayout.setHorizontalGroup(
            panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(59, 59, 59)
                        .addComponent(txtNombres))
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(59, 59, 59)
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellidos)
                            .addComponent(jScrollPane2)))
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel13)
                            .addComponent(jLabel7))
                        .addGap(68, 68, 68)
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDPLayout.createSequentialGroup()
                                .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtFechaNac, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                            .addGroup(panelDPLayout.createSequentialGroup()
                                .addComponent(checkEstado)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelDPLayout.createSequentialGroup()
                                .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtEmail)))))
                .addGap(50, 50, 50))
        );
        panelDPLayout.setVerticalGroup(
            panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDPLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap())
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
        jLabel11.setText("Filtrar:");

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
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(txtInputSearch)
                .addContainerGap())
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
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(btnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelDP, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarDatos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        limpiarAll();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        insertarRegistros();
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresActionPerformed

    private void txtInputSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputSearchKeyReleased
        // TODO add your handling code here:
        String searchNombre = txtInputSearch.getText();
        gestionaCliente.searchCliente(tablaListado, modelotablaCliente, searchNombre);
    }//GEN-LAST:event_txtInputSearchKeyReleased

    private void txtDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyTyped
        
        valida.writeNumbers(evt);
        valida.limitCaracter(8, evt, txtDni);
                
    }//GEN-LAST:event_txtDniKeyTyped

    private void txtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyTyped
        valida.writeLetters(evt);
        
    }//GEN-LAST:event_txtNombresKeyTyped

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        
        valida.writeLetters(evt);
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        valida.writeNumbers(evt);
        valida.limitCaracter(9, evt, txtCelular);
    }//GEN-LAST:event_txtCelularKeyTyped

    private void txtFechaNacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaNacKeyTyped
        // TODO add your handling code here:
        valida.limitCaracter(10, evt, txtFechaNac);
    }//GEN-LAST:event_txtFechaNacKeyTyped


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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JPanel panel;
    public javax.swing.JPanel panelDP;
    public javax.swing.JTable tablaListado;
    public javax.swing.JTextField txtApellidos;
    public javax.swing.JTextField txtCelular;
    private javax.swing.JTextArea txtDireccion;
    public javax.swing.JTextField txtDni;
    public javax.swing.JTextField txtEmail;
    private javax.swing.JFormattedTextField txtFechaNac;
    public javax.swing.JTextField txtInputSearch;
    public javax.swing.JTextField txtNombres;
    // End of variables declaration//GEN-END:variables
}
