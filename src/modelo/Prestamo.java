
package modelo;

public class Prestamo {
   
    private int codPrestamo;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private int dias;
    
    private int codLibro;
    private int codCliente;
    private int codUsuario;
    
    private String Libro;
    private String Cliente;
    private String Usuario;
    private String estado;

    public Prestamo(int codPrestamo, String fechaPrestamo, String fechaDevolucion, int dias, int codLibro, int codCliente, int codUsuario) {
        this.codPrestamo = codPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.dias = dias;
        this.codLibro = codLibro;
        this.codCliente = codCliente;
        this.codUsuario = codUsuario;
    }

    public Prestamo(int codPrestamo, String fechaPrestamo, String fechaDevolucion, int dias, int codLibro, String Libro, int codCliente, String Cliente, String Usuario, String estado) {
        this.codPrestamo = codPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.dias = dias;
        this.codLibro = codLibro;
        this.codCliente = codCliente;
        this.Usuario = Usuario;
        this.Libro = Libro;
        this.Cliente = Cliente;
        this.estado = estado;
    }
    
    

    public Prestamo(String fechaPrestamo, String fechaDevolucion, int dias, int codLibro, int codCliente, int codUsuario) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.dias = dias;
        this.codLibro = codLibro;
        this.codCliente = codCliente;
        this.codUsuario = codUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }
    
    
    public String getLibro() {
        return Libro;
    }

    public void setLibro(String Libro) {
        this.Libro = Libro;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }
    
    public Prestamo(int codPrestamo) {
        this.codPrestamo = codPrestamo;
    }
    

    public int getCodPrestamo() {
        return codPrestamo;
    }

    public void setCodPrestamo(int codPrestamo) {
        this.codPrestamo = codPrestamo;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getCodLibro() {
        return codLibro;
    }

    public void setCodLibro(int codLibro) {
        this.codLibro = codLibro;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }
    
    
    
    
    
    
}
