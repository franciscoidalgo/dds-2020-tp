package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_de_item")
public class TipoDeItem extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    //Constructor
    public TipoDeItem(String nombre) {
        this.nombre = nombre;
    }
    public TipoDeItem() {}

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}