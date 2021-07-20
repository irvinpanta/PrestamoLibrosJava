
package modelo;

public class Cliente {
    
    private int codCliente;
    private String dni;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String fechaNacimiento;
    private boolean estado;
    
    private String numero;
    private String email;

    public Cliente(int codCliente, String dni, String nombres, String apellidos, String direccion, String fechaNacimiento, String numero, String email, boolean estado) {
        this.codCliente = codCliente;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.numero = numero;
        this.email = email;
    }

    public Cliente(String dni, String nombres, String apellidos, String direccion, String fechaNacimiento, String numero, String email) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.numero = numero;
        this.email = email;
    }

    public Cliente(int codCliente) {
        this.codCliente = codCliente;
    }
    
    

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    
}
