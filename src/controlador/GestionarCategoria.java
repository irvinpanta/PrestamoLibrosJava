package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Categoria;

public class GestionarCategoria {

    private CallableStatement callsta = null;
    private ResultSet res = null;
    private Statement sta = null;
    private Connection connBD = null;
    private Config conectarBD = null;

    public GestionarCategoria() {
    }

    public void showAllCategoria(JTable tableCategoria,
            DefaultTableModel modelTableCategoria) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();

        try {

            String queryAllCategoria = " { CALL pa_CategoriaLoadAll() }";
            callsta = connBD.prepareCall(queryAllCategoria);
            res = callsta.executeQuery();

            while (res.next()) {
                listaCategoria.add(new Categoria(res.getInt(1), res.getString(2),
                        res.getString(3), res.getBoolean(4)));
            }

            setRegistersTable(tableCategoria, modelTableCategoria, listaCategoria);

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

    public void searchCategoria(JTable tablaCategoria, DefaultTableModel modelDefaultTable, String searchNombre) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();

        try {

            String querySearch = "SELECT * FROM Categoria "
                    + "WHERE NomBCategoria LIKE '%" + searchNombre + "%'";

            sta = connBD.createStatement();
            res = sta.executeQuery(querySearch);

            while (res.next()) {
                listaCategoria.add(new Categoria(res.getInt(1), res.getString(2),
                        res.getString(3), res.getBoolean(4)));
            }

            setRegistersTable(tablaCategoria, modelDefaultTable, listaCategoria);

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

    public String insertCategoria(Categoria miCategoria) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryInsert = " {CALL pa_CategoriaGrabar(?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryInsert);
            callsta.setString(1, miCategoria.getNombCategoria());
            callsta.setString(2, miCategoria.getDescripCategoria());

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

    public String updateCategoria(Categoria miCategoria) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryUpdate = " {CALL pa_CategoriaModificar(?, ?, ?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryUpdate);
            callsta.setInt(1, miCategoria.getCodCategoria());
            callsta.setString(2, miCategoria.getNombCategoria());
            callsta.setString(3, miCategoria.getDescripCategoria());
            callsta.setBoolean(4, miCategoria.getEstado());

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

    public String deleteEmpleado(Categoria miCategoria) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryEliminar = " {CALL pa_CategoriaEliminar(?)} ";

        try {

            callsta = connBD.prepareCall(queryEliminar);
            callsta.setInt(1, miCategoria.getCodCategoria());

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

    public void setRegistersTable(JTable tableCategoria,
            DefaultTableModel modelTableCategoria,
            ArrayList<Categoria> listaCategoria) {

        int countRows = tableCategoria.getRowCount();
        for (int i = 0; i < countRows; i++) {
            modelTableCategoria.removeRow(0);
        }

        Object registers[] = new Object[4];
        for (Categoria miCategoria : listaCategoria) {

            registers[0] = miCategoria.getCodCategoria();
            registers[1] = miCategoria.getNombCategoria();
            registers[2] = miCategoria.getDescripCategoria();
            registers[3] = (miCategoria.getEstado() == true) ? "Activo" : "Inactivo";

            modelTableCategoria.addRow(registers);
        }

        tableCategoria.setModel(modelTableCategoria);

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
}
