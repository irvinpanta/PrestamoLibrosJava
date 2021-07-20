package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import modelo.Login;

public class GestionarLogin {

    private CallableStatement callsta = null;
    private ResultSet res = null;
    private Connection connBD = null;
    private Config conectarBD = null;

    public boolean ingresar(Login miLogin) {

        conectarBD = new Config();
        connBD = conectarBD.getConectionBD();

        boolean autorizado = false;
        int contadorResultados = 0;

        String queryIngresar = "{Call pa_Login(?, ?)} ";

        try {

            callsta = connBD.prepareCall(queryIngresar);
            callsta.setString(1, miLogin.getUser());
            callsta.setString(2, miLogin.getPasswordUser());
            //System.out.println("si llega hasta qui");
            res = callsta.executeQuery();
         
            while (res.next()) {
                contadorResultados++;
                
            }
            
            if (contadorResultados > 0) {
                autorizado = true;
            } else {
                autorizado = false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Upps ocurrio la sgte excepcin -> \n" + ex.getMessage());
        }

        return autorizado;
    }

}
