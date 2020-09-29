package domain.Operacion;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED )
public abstract class Operacion extends EntidadPersistente {

    @Column(columnDefinition = "DATE")
    protected LocalTime fecha;


    @Column(name = "monto_total")
    protected double montoTotal;

    public Operacion() {
        this.fecha = LocalTime.now();
    }

    public Operacion(double montoTotal) {
        this.fecha = LocalTime.now();
        this.montoTotal =montoTotal;
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
