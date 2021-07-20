
package utils;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class Validate {
    
     public static void limitCaracter(int lengt, KeyEvent evt, JTextField input){
        
        int lengtInput = input.getText().length();
        if (lengtInput>=lengt) {
            evt.consume();
        }
    } 
    public static void writeLetters(KeyEvent evt) {
        
        char caracter = evt.getKeyChar();
        if ((caracter < 'a' || caracter > 'z') && (caracter < 'A' || caracter > 'Z') && caracter != evt.VK_SPACE) {
            evt.consume();
            //Toolkit.getDefaultToolkit().beep();
        }
        
    }
    
    public static void writeNumbers(KeyEvent evt) {
        
        char caracter = evt.getKeyChar();
        if (caracter < '0' || caracter > '9') {
            evt.consume();
            //Toolkit.getDefaultToolkit().beep();
        }
        
    }
    
}
