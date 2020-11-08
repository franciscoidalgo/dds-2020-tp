package domain.Operacion.Ingreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_ingreso")
public class TipoIngreso extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    //Constructors
    public TipoIngreso(String nombre) {
        this.nombre = nombre;
    }

    public TipoIngreso(){}

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
