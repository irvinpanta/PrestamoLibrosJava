package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class GestionarClientes {

    private CallableStatement callsta = null;
    private ResultSet res = null;
    private Statement sta = null;
    private Connection connBD = null;
    private Config conectarBD = null;

    public GestionarClientes() {
    }

    public void showAllCliente(JTable tableCliente,
            DefaultTableModel modelTableCliente) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();

        try {

            String queryAllCliente = " { CALL pa_ClientesLoadAll() }";
            callsta = connBD.prepareCall(queryAllCliente);
            res = callsta.executeQuery();

            while (res.next()) {
                listaCliente.add(new Cliente(res.getInt(1), res.getString(2),
                        res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getBoolean(9)));
            }

            setRegistersTable(tableCliente, modelTableCliente, listaCliente);

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

    public void searchCliente(JTable tablaCliente, DefaultTableModel modelDefaultTable, String searchNombre) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();

        try {

            String querySearch = "select C.CodCliente, C.Dni, C.Nombres, C.Apellidos, C.Direccion, C.FechaNac, T.Numero, E.Email, C.Estado from clientes C "
                    + "JOIN telefonos T ON T.CodCliente = C.CodCliente "
                    + "JOIN email E ON E.CodCliente = C.CodCliente "
                    + "WHERE concat(C.Nombres, ' ' , C.Apellidos) LIKE '%" + searchNombre + "%' or C.Dni LIKE '%" + searchNombre + "%' "
                    + "group by C.CodCliente";

            sta = connBD.createStatement();
            res = sta.executeQuery(querySearch);

            while (res.next()) {
                listaCliente.add(new Cliente(res.getInt(1), res.getString(2),
                        res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getBoolean(9)));
            }

            setRegistersTable(tablaCliente, modelDefaultTable, listaCliente);

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

    public String insertCliente(Cliente miCliente) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryInsert = " {CALL pa_ClientesGrabar(?, ?, ?, ?, ?, ?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryInsert);
            callsta.setString(1, miCliente.getDni());
            callsta.setString(2, miCliente.getNombres());
            callsta.setString(3, miCliente.getApellidos());
            callsta.setString(4, miCliente.getDireccion());
            callsta.setString(5, miCliente.getFechaNacimiento());
            callsta.setString(6, miCliente.getNumero());
            callsta.setString(7, miCliente.getEmail());

            int rpta = callsta.executeUpdate();
            if (rpta > 0) {
                mensaje = "error";
            } else {
                mensaje = "exito";
            }

        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public String updateCliente(Cliente miCliente) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryUpdate = " {CALL pa_ClientesModificar(?, ?, ?, ?, ?, ?, ?, ?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryUpdate);
            callsta.setInt(1, miCliente.getCodCliente());
            callsta.setString(2, miCliente.getDni());
            callsta.setString(3, miCliente.getNombres());
            callsta.setString(4, miCliente.getApellidos());
            callsta.setString(5, miCliente.getDireccion());
            callsta.setString(6, miCliente.getFechaNacimiento());
            callsta.setString(7, miCliente.getNumero());
            callsta.setString(8, miCliente.getEmail());
            callsta.setBoolean(9, miCliente.getEstado());

            int rpta = callsta.executeUpdate();
            if (rpta > 0) {
                mensaje = "error";
            } else {
                mensaje = "exito";
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

    public String deleteCliente(Cliente miCliente) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryEliminar = " {CALL pa_ClientesEliminar(?)} ";

        try {

            callsta = connBD.prepareCall(queryEliminar);
            callsta.setInt(1, miCliente.getCodCliente());

            int rpta = callsta.executeUpdate();
            if (rpta > 0) {
                mensaje = "error";
            } else {
                mensaje = "exito";
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

    public void setRegistersTable(JTable tableCliente,
            DefaultTableModel modelTableCliente,
            ArrayList<Cliente> listaCliente) {

        int countRows = tableCliente.getRowCount();
        for (int i = 0; i < countRows; i++) {
            modelTableCliente.removeRow(0);
        }

        Object registers[] = new Object[9];
        for (Cliente miCliente : listaCliente) {

            registers[0] = miCliente.getCodCliente();
            registers[1] = miCliente.getDni();
            registers[2] = miCliente.getNombres();
            registers[3] = miCliente.getApellidos();
            registers[4] = miCliente.getDireccion();
            registers[5] = miCliente.getFechaNacimiento();
            registers[6] = miCliente.getNumero();
            registers[7] = miCliente.getEmail();
            registers[8] = (miCliente.getEstado() == true) ? "Activo" : "Inactivo";

            modelTableCliente.addRow(registers);
        }

        tableCliente.setModel(modelTableCliente);

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
