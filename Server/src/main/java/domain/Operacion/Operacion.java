package domain.Operacion;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED )
public abstract class Operacion extends EntidadPersistente {

    @Column(columnDefinition = "DATE")
    protected LocalDate fecha;


    @Column(name = "monto_total")
    protected double montoTotal;

    public Operacion() {
        this.fecha = LocalDate.now();
    }

    public Operacion(double montoTotal) {
        this.fecha = LocalDate.now();
        this.montoTotal =montoTotal;
    }

    //Getter Setter
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMontoTotal(){
        return montoTotal;
    }

    //Funcionalidad
    protected abstract double montoTotal();
}
