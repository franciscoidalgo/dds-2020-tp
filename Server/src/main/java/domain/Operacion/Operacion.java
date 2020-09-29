package domain.Operacion;

import domain.Entidad.Usuario.Usuario;
import java.time.LocalDate;


public abstract class Operacion {


    protected long nroOperacion;
    protected LocalDate fecha;
    protected Usuario creadoPor;

    public Operacion(Usuario creadoPor) {
        this.creadoPor = creadoPor;
        this.fecha = LocalDate.now();
        this.nroOperacion = 1; //Autogenerado
    }

    //Getter Setter
    public long getNroOperacion() {
        return nroOperacion;
    }

    public void setNroOperacion(long nroOperacion) {
        this.nroOperacion = nroOperacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    //Funcionalidad
    protected abstract double montoTotal();
}
