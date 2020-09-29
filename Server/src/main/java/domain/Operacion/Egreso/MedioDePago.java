package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "medio_de_pago")
public class MedioDePago extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "moneda")
    private String moneda;

    //Constructors
    public MedioDePago() {}

    public MedioDePago(String nombre,String moneda) {
        this.nombre = nombre;
        this.moneda = moneda;
    }

    //Getter Setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
