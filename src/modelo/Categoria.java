package modelo;

public class Categoria {

    private int codCategoria;
    private String nombCategoria;
    private String descripCategoria;
    private boolean estado;

    public Categoria(){
        
    }
    public Categoria(int codCategoria, String nombCategoria, String descripCategoria, boolean estado) {
        this.codCategoria = codCategoria;
        this.nombCategoria = nombCategoria;
        this.descripCategoria = descripCategoria;
        this.estado = estado;
    }

    public Categoria(String nombCategoria, String descripCategoria) {

        this.nombCategoria = nombCategoria;
        this.descripCategoria = descripCategoria;
    }

    public Categoria(int codCategoria, String nombCategoria) {

    }

    public Categoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNombCategoria() {
        return nombCategoria;
    }

    public void setNombCategoria(String nombCategoria) {
        this.nombCategoria = nombCategoria;
    }

    public String getDescripCategoria() {
        return descripCategoria;
    }

    public void setDescripCategoria(String descripCategoria) {
        this.descripCategoria = descripCategoria;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
