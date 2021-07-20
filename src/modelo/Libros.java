
package modelo;


public class Libros {
    
    private int codLibro;
    private String ISBN;
    private String tituloLibro;
    private String autorLibro;
    private String fecha;
    private String editorialLibro;
    private boolean estado;
    
    private int codCategoria;
    private String nombCategoria;

    public String getNombCategoria() {
        return nombCategoria;
    }

    public void setNombCategoria(String nombCategoria) {
        this.nombCategoria = nombCategoria;
    }
    
    

    public Libros(int codLibro, String ISBN, String tituloLibro, String autorLibro, String fecha, String editorialLibro, int codCategoria, boolean estado) {
        this.codLibro = codLibro;
        this.ISBN = ISBN;
        this.tituloLibro = tituloLibro;
        this.autorLibro = autorLibro;
        this.fecha = fecha;
        this.editorialLibro = editorialLibro;
        this.estado = estado;
        this.codCategoria = codCategoria;
    }

    public Libros(int codLibro, String ISBN, String tituloLibro, String autorLibro, String fecha, String editorialLibro, String nombCategoria, boolean estado) {
        this.codLibro = codLibro;
        this.ISBN = ISBN;
        this.tituloLibro = tituloLibro;
        this.autorLibro = autorLibro;
        this.fecha = fecha;
        this.editorialLibro = editorialLibro;
        this.estado = estado;
        this.nombCategoria = nombCategoria;
    }
    
    

    public Libros(String ISBN, String tituloLibro, String autorLibro, String fecha, String editorialLibro, int codCategoria) {
        this.ISBN = ISBN;
        this.tituloLibro = tituloLibro;
        this.autorLibro = autorLibro;
        this.fecha = fecha;
        this.editorialLibro = editorialLibro;
        this.codCategoria = codCategoria;
    }

    public Libros(int codLibro) {
        this.codLibro = codLibro;
    }
    

    public int getCodLibro() {
        return codLibro;
    }

    public void setCodLibro(int codLibro) {
        this.codLibro = codLibro;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public String getAutorLibro() {
        return autorLibro;
    }

    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEditorialLibro() {
        return editorialLibro;
    }

    public void setEditorialLibro(String editorialLibro) {
        this.editorialLibro = editorialLibro;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }
    
    
}
