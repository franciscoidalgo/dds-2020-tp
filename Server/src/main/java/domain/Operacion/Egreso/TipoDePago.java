package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_de_pago")
public class TipoDePago extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    //Constructor
    public TipoDePago(String nombre) {
        this.nombre = nombre;
    }
    public TipoDePago() {}

    //Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
