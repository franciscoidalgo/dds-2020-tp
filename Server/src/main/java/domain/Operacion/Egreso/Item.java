package domain.Operacion.Egreso;

import domain.Entidad.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item extends Entidad {

    @Column(name = "descripcion")
    private String descripcion;

    //Constructor
    public Item(String descripcion) {
        this.descripcion = descripcion;
    }

    public Item() {}

    //Setters+Getters
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDescripcion() { return descripcion; }

    //Funcionalidad
    public Boolean coincidenItems(Item otroItem) {
        return this.getDescripcion().contentEquals(otroItem.getDescripcion());
    }
}
