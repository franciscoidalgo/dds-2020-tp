package Entidad.CategorizacionOperacion;

public class CategoriaOperacion {
    private String descripcion;

    //Constructor
    public CategoriaOperacion(String descripcion){
        this.descripcion = descripcion;
    }

    //Getter Setter
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
