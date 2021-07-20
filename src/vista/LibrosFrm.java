package vista;

import controlador.GestionarLibros;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Categoria;
import modelo.Libros;
import utils.Message;
import utils.Validate;

public class LibrosFrm extends javax.swing.JFrame {

    private Libros miLibros;
    private GestionarLibros gestionaLibros = new GestionarLibros();
    DefaultTableModel modelotablaLibros = new DefaultTableModel();
    
    private Validate valida = new Validate();

    private String accion = "Guardar";
    private int codCategoria;

    public LibrosFrm() {
        initComponents();
        this.setLocationRelativeTo(null);

        headersTable();
        showRegistros();
        txtFecha.setText("2000-01-01");

        loadCategorias();
    }

    private void limpiarAll() {
        txtAutorLibro.setText("");
        txtEditorial.setText("");
        txtISBN.setText("");
        txtTituloLibro.setText("");
        txtFecha.setText("2000-01-01");
        checkEstado.isSelected();
        checkEstado.setEnabled(false);

        accion = "Guardar";
        codCategoria = 0;
        //System.out.println("Guardar");
    }

    private void obtenerDatos() {

        int rowSelect = tablaListado.getSelectedRow();

        String nombCategoria;
        String isbn, tituloLibro, autorLibro, editorialLibro;
        String fecha;
        boolean estado;

        isbn = tablaListado.getValueAt(rowSelect, 1).toString();
        tituloLibro = tablaListado.getValueAt(rowSelect, 2).toString();
        autorLibro = tablaListado.getValueAt(rowSelect, 3).toString();
        fecha = tablaListado.getValueAt(rowSelect, 4).toString();
        editorialLibro = tablaListado.getValueAt(rowSelect, 5).toString();
        nombCategoria = tablaListado.getValueAt(rowSelect, 6).toString();

        String formatoEstado = tablaListado.getValueAt(rowSelect, 7).toString();
        estado = (formatoEstado.equals("Activo")) ? true : false;

        if (estado == true) {
            checkEstado.setSelected(true);
            checkEstado.setEnabled(false);
        } else {
            checkEstado.setSelected(false);
            checkEstado.setEnabled(true);
        }

        txtISBN.setText(isbn);
        txtTituloLibro.setText(tituloLibro);
        txtAutorLibro.setText(autorLibro);
        txtFecha.setText(fecha);
        txtEditorial.setText(editorialLibro);
        cboCategoria.setSelectedItem(nombCategoria);

        //codLibros = (int) tablaListado.getValueAt(rowSelect, 0);
        accion = "Modificar";
        //System.out.println("Modificar");
        //System.out.println("codigo libro: "+codLibros);
    }

    private void grabarDatos() {
        try {

            String isbn, tituloLibro, autorLibro, editorialLibro;
            String fecha;

            isbn = txtISBN.getText().trim();
            tituloLibro = txtTituloLibro.getText().trim();
            autorLibro = txtAutorLibro.getText().trim();
            fecha = txtFecha.getText().trim();
            editorialLibro = txtEditorial.getText().trim();

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de registrar libro?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miLibros = new Libros(isbn, tituloLibro, autorLibro, fecha, editorialLibro, codCategoria);
                String mensaje = gestionaLibros.insertLibros(miLibros);

                if (mensaje.equals("exito")) {
                    Message.showMessageExito("!! Libro registrado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtISBN.requestFocus();

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

            int codLibros;
            String isbn, tituloLibro, autorLibro, editorialLibro;
            String fecha;
            boolean estado;

            int rowSelect = tablaListado.getSelectedRow();
            codLibros = (int) tablaListado.getValueAt(rowSelect, 0);

            isbn = txtISBN.getText().trim();
            tituloLibro = txtTituloLibro.getText().trim();
            autorLibro = txtAutorLibro.getText().trim();
            fecha = txtFecha.getText().trim();
            editorialLibro = txtEditorial.getText().trim();

            if (checkEstado.isSelected()) {
                estado = true;
            } else {
                estado = false;
            }

            int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de modificar libro?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
            if (rspta == JOptionPane.YES_OPTION) {
                miLibros = new Libros(codLibros, isbn, tituloLibro, autorLibro, fecha, editorialLibro, codCategoria, estado);
                String mensaje = gestionaLibros.updateLibros(miLibros);

                if (mensaje.equals("exito")) {
                    Message.showMessageExito("!! Libro modificado correctamente !!");
                    showRegistros();
                    limpiarAll();
                    txtISBN.requestFocus();

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

            int codLibros;

            int rowSelect = tablaListado.getSelectedRow();
            codLibros = (int) tablaListado.getValueAt(rowSelect, 0);

            int obtenerCodLibro;
            obtenerCodLibro = gestionaLibros.getCodigoLibro(String.valueOf(codLibros));

            if (obtenerCodLibro == codLibros) {
                Message.showMessageError("Libro no se puede eliminar no se encuentra en biblioteca");

            } else {
                int rspta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar libro?", "CONFIRMAR", JOptionPane.YES_NO_OPTION);
                if (rspta == JOptionPane.YES_OPTION) {
                    miLibros = new Libros(codLibros);
                    String mensaje = gestionaLibros.deleteLibros(miLibros);

                    if (mensaje.equals("exito")) {
                        Message.showMessageExito("!! Libro eliminado correctamente !!");
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

            valCajas = txtISBN.getText().length() == 0 || txtTituloLibro.getText().length() == 0 || txtAutorLibro.getText().length() == 0 || txtFecha.getText().length() == 0 || txtEditorial.getText().length() == 0 || cboCategoria.getSelectedIndex() == -1;

            if (valCajas == true) {
                Message.showMessageError("Complete los campos");
            } else if (accion.equals("Guardar")) {
                grabarDatos();
            } else if (accion.equals("Modificar")) {
                modificarDatos();
            }

        } catch (Exception e) {
        }
    }

    private void headersTable() {
        String[] headerTable = {"Codigo", "ISBN", "Titulo Libro", "Autor Libro", "Fecha", "Editorial", "Categora", "Estado"};
        modelotablaLibros.setColumnIdentifiers(headerTable);
        tablaListado.setModel(modelotablaLibros);
    }

    private void showRegistros() {
        gestionaLibros.showAllCategoria(tablaListado, modelotablaLibros);
    }

    private void loadCategorias() {
        gestionaLibros.loadComboCategorias(cboCategoria);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        panelDP = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        checkEstado = new javax.swing.JCheckBox();
        txtTituloLibro = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAutorLibro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEditorial = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox();
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
        jLabel1.setText("GESTION DE LIBROS");

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));
        panel.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        panelDP.setBackground(new java.awt.Color(255, 255, 255));
        panelDP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Datos personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria", 0, 12))); // NOI18N
        panelDP.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel2.setText("ISBN:");

        txtISBN.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtISBN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtISBN.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtISBNKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel3.setText("Titulo de Libro:");

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

        txtTituloLibro.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtTituloLibro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTituloLibro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtTituloLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloLibroActionPerformed(evt);
            }
        });
        txtTituloLibro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTituloLibroKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel4.setText("Autor de Libro:");

        txtAutorLibro.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtAutorLibro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAutorLibro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtAutorLibro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutorLibroKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel5.setText("Editorial:");

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel6.setText("Fecha:");

        txtFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaKeyTyped(evt);
            }
        });

        txtEditorial.setColumns(20);
        txtEditorial.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        txtEditorial.setRows(5);
        jScrollPane2.setViewportView(txtEditorial);

        jLabel7.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel7.setText("Categoria:");

        cboCategoria.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<Seleccionar>" }));
        cboCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cboCategoriaMousePressed(evt);
            }
        });
        cboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDPLayout = new javax.swing.GroupLayout(panelDP);
        panelDP.setLayout(panelDPLayout);
        panelDPLayout.setHorizontalGroup(
            panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(59, 59, 59)
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAutorLibro)
                            .addComponent(jScrollPane2)))
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel13)
                            .addComponent(jLabel7))
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDPLayout.createSequentialGroup()
                                .addGap(295, 295, 295)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDPLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDPLayout.createSequentialGroup()
                                        .addComponent(checkEstado)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cboCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(59, 59, 59)
                        .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDPLayout.createSequentialGroup()
                                .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtTituloLibro))))
                .addGap(50, 50, 50))
        );
        panelDPLayout.setVerticalGroup(
            panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDPLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTituloLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAutorLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDPLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(panelDPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jLabel11.setText("Nomb. Libro:");

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
                .addComponent(panelDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
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

    private void txtTituloLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloLibroActionPerformed

    private void tablaListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaListadoMouseClicked
        obtenerDatos();
    }//GEN-LAST:event_tablaListadoMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarDatos();                // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        limpiarAll();                // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        insertarRegistros();
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void cboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaActionPerformed
        codCategoria = gestionaLibros.getCodigoNombreCategoria(cboCategoria.getSelectedItem().toString());
        System.out.println("cdig " + codCategoria);

    }//GEN-LAST:event_cboCategoriaActionPerformed

    private void cboCategoriaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboCategoriaMousePressed

    }//GEN-LAST:event_cboCategoriaMousePressed

    private void txtInputSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputSearchKeyReleased

        String searchNombre = txtInputSearch.getText();
        gestionaLibros.searchLibros(tablaListado, modelotablaLibros, searchNombre);

    }//GEN-LAST:event_txtInputSearchKeyReleased

    private void txtISBNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtISBNKeyTyped
        valida.writeNumbers(evt);
    }//GEN-LAST:event_txtISBNKeyTyped

    private void txtTituloLibroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloLibroKeyTyped
        
        
    }//GEN-LAST:event_txtTituloLibroKeyTyped

    private void txtAutorLibroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutorLibroKeyTyped
        valida.writeLetters(evt);
        
    }//GEN-LAST:event_txtAutorLibroKeyTyped

    private void txtFechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyTyped
        // TODO add your handling code here:
        valida.limitCaracter(10, evt, txtFecha);
    }//GEN-LAST:event_txtFechaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCerrar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnGrabar;
    public javax.swing.JButton btnReportes;
    private javax.swing.JComboBox cboCategoria;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JPanel panel;
    public javax.swing.JPanel panelDP;
    public javax.swing.JTable tablaListado;
    public javax.swing.JTextField txtAutorLibro;
    private javax.swing.JTextArea txtEditorial;
    private javax.swing.JFormattedTextField txtFecha;
    public javax.swing.JTextField txtISBN;
    public javax.swing.JTextField txtInputSearch;
    public javax.swing.JTextField txtTituloLibro;
    // End of variables declaration//GEN-END:variables
}
