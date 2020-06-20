package java.Entidad;

public class CategoriaOperacion {
    private String descripcion;

    //Getter Setter
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    //Constructor
    public CategoriaOperacion(String descripcion){
        this.descripcion = descripcion;
    }
}
