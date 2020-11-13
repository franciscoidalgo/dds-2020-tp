package domain.Operacion;

import domain.Entidad.Entidad;
import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "operacion")
@Inheritance(strategy = InheritanceType.JOINED )
public abstract class Operacion extends EntidadPersistente {

    @Column(columnDefinition = "DATE")
    protected LocalDate fecha;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    protected Entidad entidad;

    @Column(name = "monto_total")
    protected double  montoTotal;

    public Operacion() {}

    public Operacion(LocalDate fecha,double montoTotal,Entidad entidad) {
        this.fecha = fecha;
        this.montoTotal =montoTotal;
        this.entidad = entidad;
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
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
    public Entidad getEntidad() {
        return entidad;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
    //Funcionalidad
    protected abstract double montoTotal();
}
