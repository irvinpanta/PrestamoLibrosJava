
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utils.Message;


public class Config {
    
    public final String driverSGBD = "com.mysql.jdbc.Driver";
    private final String database = "dbprestamoslibros";
    private final String username = "root";
    private final String password = "";
    private final String port = "3306";
    private final String hostname = "jdbc:mysql://localhost/" + database;

    private Connection conn = null;

    public Connection getConectionBD() {

        try {
        
            Class.forName(driverSGBD);
            conn = DriverManager.getConnection(hostname, username, password);
        
        } catch (ClassNotFoundException ex) {
            System.out.println("" + ex.getMessage());
            Message.showMessageWarning("Debe cargar el driver MYSQL, para establecer la conexion..!!");

        } catch (SQLException ex) {
            Message.showMessageWarning("Ocurrio la siguiente excepcion ->\n" + ex.getMessage());
        }

        return conn;
    }

    public void closeConexion() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Ocurrio la siguiente excepcion al cerrar la conexion con la databse");
                ex.printStackTrace();
            }
        }
    }
}
