package domain.Entidad;

import javax.persistence.*;

@MappedSuperclass
public class Entidad {

    @Id
    @GeneratedValue
    private int id;

    @Transient
    private String nombre;

    @Column
    private String descripcion;

    public void setId (int id){
        this.id = id;
    }

    public int getId (){
        return id;
    }

    public String getNombre(){
        return nombre;
    };
    public String getDescripcion(){
        return descripcion;
    };
}
