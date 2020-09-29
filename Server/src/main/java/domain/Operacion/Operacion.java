package domain.Operacion;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "operacion")
@Inheritance(strategy = InheritanceType.JOINED )
public abstract class Operacion extends EntidadPersistente {

    @Column(columnDefinition = "DATE")
    protected LocalTime fecha;


    public Operacion() {
        this.fecha = LocalTime.now();
    }

    //Getter Setter
    public LocalTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalTime fecha) {
        this.fecha = fecha;
    }

    //Funcionalidad
    protected abstract double montoTotal();
}
