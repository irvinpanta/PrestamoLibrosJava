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
import modelo.Libros;
import modelo.Prestamo;

public class GestionarPrestamo {

    private CallableStatement callsta = null;
    private ResultSet res = null;
    private Statement sta = null;
    private Connection connBD = null;
    private Config conectarBD = null;

    public GestionarPrestamo() {
    }

    public void showAllPrestamos(JTable tablePrestamos,
            DefaultTableModel modelTablePrestamos) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Prestamo> listaPrestamos = new ArrayList<Prestamo>();

        try {

            String queryAllCategoria = " { CALL pa_PrestamoLoadAll() }";
            callsta = connBD.prepareCall(queryAllCategoria);
            res = callsta.executeQuery();

            while (res.next()) {
                listaPrestamos.add(new Prestamo(res.getInt(1), res.getString(2),
                        res.getString(3), res.getInt(4), res.getInt(5), res.getString(6), res.getInt(7), res.getString(8), res.getString(9), res.getString(10)));
            }

            setRegistersTable(tablePrestamos, modelTablePrestamos, listaPrestamos);

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


    public void searchPrestamos(JTable tablaPrestamos, DefaultTableModel modelDefaultTable, String searchNombre) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Prestamo> listaPrestamos = new ArrayList<Prestamo>();

        try {

            String querySearch = "select p.CodPrestamo ,p.FechaPrestamo, p.FechaDevolucion, p.Dias, l.CodLibro, l.TituloLibro, c.CodCliente, concat(c.Dni, ' - ', c.Nombres, ' ', c.Apellidos), concat(u.Nombre, ' ', u.Apellidos), p.Estado from prestamo p "
                    + "join libros l on l.CodLibro = p.CodLibro "
                    + "join clientes c on c.CodCliente = p.CodCliente "
                    + "join usuarios u on u.CodUsuario = p.CodUsuario "
                    + "WHERE l.TituloLibro LIKE '%" + searchNombre + "%' or concat(c.Nombres, ' ', c.Apellidos) LIKE '%" + searchNombre + "%'";

            sta = connBD.createStatement();
            res = sta.executeQuery(querySearch);

            while (res.next()) {
                listaPrestamos.add(new Prestamo(res.getInt(1), res.getString(2),
                        res.getString(3), res.getInt(4), res.getInt(5), res.getString(6), res.getInt(7), res.getString(8), res.getString(9), res.getString(10)));
            }

            setRegistersTable(tablaPrestamos, modelDefaultTable, listaPrestamos);

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

    public String insertPrestamos(Prestamo miPrestamos) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryInsert = " {CALL pa_PrestamoGrabar(?, ?, ?, ?, ?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryInsert);
            callsta.setString(1, miPrestamos.getFechaPrestamo());
            callsta.setString(2, miPrestamos.getFechaDevolucion());
            callsta.setInt(3, miPrestamos.getDias());
            callsta.setInt(4, miPrestamos.getCodLibro());
            callsta.setInt(5, miPrestamos.getCodCliente());
            callsta.setInt(6, miPrestamos.getCodUsuario());

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

    public String updatePrestamos(Prestamo miPrestamos) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryUpdate = " {CALL pa_PrestamoModificar(?, ?, ?, ?, ?, ?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryUpdate);
            callsta.setInt(1, miPrestamos.getCodPrestamo());
            callsta.setString(2, miPrestamos.getFechaPrestamo());
            callsta.setString(3, miPrestamos.getFechaDevolucion());
            callsta.setInt(4, miPrestamos.getDias());
            callsta.setInt(5, miPrestamos.getCodLibro());
            callsta.setInt(6, miPrestamos.getCodCliente());
            callsta.setInt(7, miPrestamos.getCodUsuario());

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

    public String deletePrestamos(Prestamo miPrestamos) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryEliminar = " {CALL pa_PrestamoEliminar(?)} ";

        try {

            callsta = connBD.prepareCall(queryEliminar);
            callsta.setInt(1, miPrestamos.getCodPrestamo());

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

    public void setRegistersTable(JTable tablePrestamos,
            DefaultTableModel modelTablePrestamos,
            ArrayList<Prestamo> listaPrestamos) {

        int countRows = tablePrestamos.getRowCount();
        for (int i = 0; i < countRows; i++) {
            modelTablePrestamos.removeRow(0);
        }

        Object registers[] = new Object[10];
        for (Prestamo miPrestamos : listaPrestamos) {

            registers[0] = miPrestamos.getCodPrestamo();
            registers[1] = miPrestamos.getFechaPrestamo();
            registers[2] = miPrestamos.getFechaDevolucion();
            registers[3] = miPrestamos.getDias();
            registers[4] = miPrestamos.getCodLibro();
            registers[5] = miPrestamos.getLibro();
            registers[6] = miPrestamos.getCodCliente();
            registers[7] = miPrestamos.getCliente();
            registers[8] = miPrestamos.getUsuario();
            registers[9] = miPrestamos.getEstado();
            /*registers[7] = (miPrestamos.getEstado() == true) ? "Activo" : "Inactivo";*/

            modelTablePrestamos.addRow(registers);
        }

        tablePrestamos.setModel(modelTablePrestamos);

    }

    public int getCodigoCliente(String codCliente) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        int codigoCliente = 0;

        try {

            String queryLoadCodigoCliente = " { CALL pa_PrestamoObtenerCodCliente(?) }";
            callsta = connBD.prepareCall(queryLoadCodigoCliente);
            callsta.setString(1, codCliente);
            res = callsta.executeQuery();

            while (res.next()) {
                codigoCliente = res.getInt(1);
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

        return codigoCliente;
    }
    
    public int getEstadoPrestamo(int codPrestamo) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        int estadoPrestamo = 0;

        try {

            String queryLoadEstadoPrestamo = " { CALL pa_PrestamoObtenerEstadoprestamo(?) }";
            callsta = connBD.prepareCall(queryLoadEstadoPrestamo);
            callsta.setInt(1, codPrestamo);
            res = callsta.executeQuery();

            while (res.next()) {
                estadoPrestamo = res.getInt(1);
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

        return estadoPrestamo;
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
    
       public void showAllPrestamosPendientes(JTable tablePrestamos,
            DefaultTableModel modelTablePrestamos) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Prestamo> listaPrestamos = new ArrayList<Prestamo>();

        try {

            String queryAllCategoria = " { CALL pa_PrestamoLoadAllPendiente() }";
            callsta = connBD.prepareCall(queryAllCategoria);
            res = callsta.executeQuery();

            while (res.next()) {
                listaPrestamos.add(new Prestamo(res.getInt(1), res.getString(2),
                        res.getString(3), res.getInt(4), res.getInt(5), res.getString(6), res.getInt(7), res.getString(8), res.getString(9), res.getString(10)));
            }

            setRegistersTable(tablePrestamos, modelTablePrestamos, listaPrestamos);

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

    public String PrestamosDevolucionGrabar(Prestamo miPrestamos) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        String mensaje = null;
        String queryEliminar = " {CALL pa_PrestamoDevolucionGrabar(?)} ";

        try {

            callsta = connBD.prepareCall(queryEliminar);
            callsta.setInt(1, miPrestamos.getCodPrestamo());

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
    
    public void searchPrestamosPendientes(JTable tablaPrestamos, DefaultTableModel modelDefaultTable, String searchNombre) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        ArrayList<Prestamo> listaPrestamos = new ArrayList<Prestamo>();

        try {

            String querySearch = "select p.CodPrestamo ,p.FechaPrestamo, p.FechaDevolucion, p.Dias, l.CodLibro, l.TituloLibro, c.CodCliente, concat(c.Dni, ' - ', c.Nombres, ' ', c.Apellidos), concat(u.Nombre, ' ', u.Apellidos), p.Estado from prestamo p "
                    + "join libros l on l.CodLibro = p.CodLibro "
                    + "join clientes c on c.CodCliente = p.CodCliente "
                    + "join usuarios u on u.CodUsuario = p.CodUsuario "
                    + "WHERE l.TituloLibro LIKE '%" + searchNombre + "%' or concat(c.Nombres, ' ', c.Apellidos) LIKE '%" + searchNombre + "%' and p.Estado = 'Prestado'";

            sta = connBD.createStatement();
            res = sta.executeQuery(querySearch);

            while (res.next()) {
                listaPrestamos.add(new Prestamo(res.getInt(1), res.getString(2),
                        res.getString(3), res.getInt(4), res.getInt(5), res.getString(6), res.getInt(7), res.getString(8), res.getString(9), res.getString(10)));
            }

            setRegistersTable(tablaPrestamos, modelDefaultTable, listaPrestamos);

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
}
