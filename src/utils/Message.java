
package utils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Message {
    
    /*Metodos para mostrar mensajes atraves de JOptionPane*/
    public static void showMessageError(String message) {
        JOptionPane.showMessageDialog(null, message,
                "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessageExito(String message) {
        JOptionPane.showMessageDialog(null, message,
                "EXITO", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showMessageWarning(String message) {
        JOptionPane.showMessageDialog(null, message,
                "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
    }

    /*Metodo para mostrar mensaje pidiendo confirmacion atraves de JOptionPane*/
    public static int requestMessage(String message) {

        int rpta = JOptionPane.showConfirmDialog(null, message,
                "CONFIRMAR", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        return rpta;
    }

    /*Metodo para mostrar mensaje con duracion atraves de JLabel*/
    public static void showMessageLabel(int second, String message, JLabel label) {

        try {
            label.setText(message);
            Thread.sleep(second * 1000);
            label.setText("");
        } catch (InterruptedException ex) {
            label.setText(ex.getMessage());
        }

    }
    
}
