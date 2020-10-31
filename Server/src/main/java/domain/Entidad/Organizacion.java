package domain.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizacion")
public class Organizacion extends EntidadPersistente{

    @Column
    private String nombre;

    @OneToMany(mappedBy = "organizacion", cascade = {CascadeType.ALL})
    private List<Entidad> entidades;
    //Constructors
    public Organizacion(String nombre, List<Entidad> entidades) {
        this.nombre = nombre;
        this.entidades = entidades;
    }

    public Organizacion(){}

    //getter y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Entidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }
}
