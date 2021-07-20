
package modelo;


public class Usuarios {
    
    private int codUsuario;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String pass;
    private String estado;

    public Usuarios(int codUsuario, String nombre, String apellidos, String usuario, String pass, String estado) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.pass = pass;
        this.estado = estado;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
