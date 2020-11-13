package domain.Entidad;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "entidad_base")
public class EntidadBase extends EntidadPersistente implements Entidad {
    //Atributos
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;
    //Constructor

    public EntidadBase(String nombre, String descripcion){
        this.nombre=nombre;
        this.descripcion= descripcion;
    }

    public EntidadBase() {}

    //Getters-Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public String descripcion() {
        return descripcion;
    }

}
