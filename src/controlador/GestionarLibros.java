package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Categoria;
import modelo.Libros;

public class GestionarLibros {

    private CallableStatement callsta = null;
    private ResultSet res = null;
    private Statement sta = null;
    private Connection connBD = null;
    private Config conectarBD = null;

    public GestionarLibros() {
    }

    public void showAllCategoria(JTable tableLibros,
            DefaultTableModel modelTableLibros) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Libros> listaLibros = new ArrayList<Libros>();

        try {

            String queryAllCategoria = " { CALL pa_LibrosLoadAll() }";
            callsta = connBD.prepareCall(queryAllCategoria);
            res = callsta.executeQuery();

            while (res.next()) {
                listaLibros.add(new Libros(res.getInt(1), res.getString(2),
                        res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getBoolean(8)));
            }

            setRegistersTable(tableLibros, modelTableLibros, listaLibros);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connBD.close();
                callsta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void searchLibros(JTable tablaLibros, DefaultTableModel modelDefaultTable, String searchNombre) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Libros> listaLibros = new ArrayList<Libros>();

        try {

            String querySearch = "select l.CodLibro, l.ISBN, l.TituloLibro, l.AutorLibro, l.Fecha, l.EditorialLibro, c.NomBCategoria, l.Estado from libros l "
                    + "join categoria c on c.CodCategoria = l.CodCategoria "
                    + "WHERE l.TituloLibro LIKE '%" + searchNombre + "%' or l.ISBN LIKE '%" + searchNombre + "%' ";

            sta = connBD.createStatement();
            res = sta.executeQuery(querySearch);

            while (res.next()) {
                listaLibros.add(new Libros(res.getInt(1), res.getString(2),
                        res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getBoolean(8)));
            }

            setRegistersTable(tablaLibros, modelDefaultTable, listaLibros);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connBD.close();
                callsta.close();
//                res.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String insertLibros(Libros miLibros) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryInsert = " {CALL pa_LibrosGrabar(?, ?, ?, ?, ?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryInsert);
            callsta.setString(1, miLibros.getISBN());
            callsta.setString(2, miLibros.getTituloLibro());
            callsta.setString(3, miLibros.getAutorLibro());
            callsta.setString(4, miLibros.getFecha());
            callsta.setString(5, miLibros.getEditorialLibro());
            callsta.setInt(6, miLibros.getCodCategoria());

            int rpta = callsta.executeUpdate();
            if (rpta > 0) {
                mensaje = "exito";
            } else {
                mensaje = "error";
            }

        } catch (SQLException ex) {
            mensaje = ex.getMessage();
        } finally {
            try {
                connBD.close();
                callsta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return mensaje;
    }

    public String updateLibros(Libros miLibros) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryUpdate = " {CALL pa_LibrosModificar(?, ?, ?, ?, ?, ?, ?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryUpdate);
            callsta.setInt(1, miLibros.getCodLibro());
            callsta.setString(2, miLibros.getISBN());
            callsta.setString(3, miLibros.getTituloLibro());
            callsta.setString(4, miLibros.getAutorLibro());
            callsta.setString(5, miLibros.getFecha());
            callsta.setString(6, miLibros.getEditorialLibro());
            callsta.setInt(7, miLibros.getCodCategoria());
            callsta.setBoolean(8, miLibros.getEstado());

            int rpta = callsta.executeUpdate();
            if (rpta > 0) {
                mensaje = "exito";
            } else {
                mensaje = "error";
            }

        } catch (SQLException ex) {
            mensaje = ex.getMessage();
        } finally {
            try {
                connBD.close();
                callsta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return mensaje;
    }

    public String deleteLibros(Libros miLibros) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryEliminar = " {CALL pa_LibrosEliminar(?)} ";

        try {

            callsta = connBD.prepareCall(queryEliminar);
            callsta.setInt(1, miLibros.getCodLibro());

            int rpta = callsta.executeUpdate();
            if (rpta > 0) {
                mensaje = "exito";
            } else {
                mensaje = "error";
            }

        } catch (SQLException ex) {
            mensaje = ex.getMessage();
        } finally {
            try {
                connBD.close();
                callsta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return mensaje;
    }

    public void setRegistersTable(JTable tableLibros,
            DefaultTableModel modelTableLibros,
            ArrayList<Libros> listaLibros) {

        int countRows = tableLibros.getRowCount();
        for (int i = 0; i < countRows; i++) {
            modelTableLibros.removeRow(0);
        }

        Object registers[] = new Object[8];
        for (Libros miLibros : listaLibros) {

            registers[0] = miLibros.getCodLibro();
            registers[1] = miLibros.getISBN();
            registers[2] = miLibros.getTituloLibro();
            registers[3] = miLibros.getAutorLibro();
            registers[4] = miLibros.getFecha();
            registers[5] = miLibros.getEditorialLibro();
            registers[6] = miLibros.getNombCategoria();
            registers[7] = (miLibros.getEstado() == true) ? "Activo" : "Inactivo";

            modelTableLibros.addRow(registers);
        }

        tableLibros.setModel(modelTableLibros);

    }

    public void loadComboCategorias(JComboBox cboCategoria) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        try {

            String queryLoadCategoria = " { CALL pa_CategoriaLoadAllEstado() }";
            callsta = connBD.prepareCall(queryLoadCategoria);
            res = callsta.executeQuery();

            while (res.next()) {
                cboCategoria.addItem(res.getString(2));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connBD.close();
                callsta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public int getCodigoNombreCategoria(String nombreCategoria) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        int codigoCategoria = 0;

        try {

            String queryLoadCodigoCategoria = " { CALL pa_CategoriaGetCodigo(?) }";
            callsta = connBD.prepareCall(queryLoadCodigoCategoria);
            callsta.setString(1, nombreCategoria);
            res = callsta.executeQuery();

            while (res.next()) {
                codigoCategoria = res.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connBD.close();
                callsta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return codigoCategoria;
    }

    private void closeRecursos() {
        try {
            connBD.close();
            callsta.close();
            res.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("[!]> Ocurrio la siguiente excepcion : \n" + ex.getMessage());
        }

    }

    public int getCodigoLibro(String codLibro) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        int codigoLibro = 0;

        try {

            String queryLoadCodigoLIbro = " { CALL pa_LibroObtenerCodigo(?) }";
            callsta = connBD.prepareCall(queryLoadCodigoLIbro);
            callsta.setString(1, codLibro);
            res = callsta.executeQuery();

            while (res.next()) {
                codigoLibro = res.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connBD.close();
                callsta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return codigoLibro;
    }

}
