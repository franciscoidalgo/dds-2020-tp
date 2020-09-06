package domain.Operacion;

import domain.Usuario.Usuario;

import java.time.LocalTime;

public abstract class Operacion {


    protected long nroOperacion;
    protected LocalTime fecha;
    protected Usuario creadoPor;

    public Operacion(Usuario creadoPor) {
        this.creadoPor = creadoPor;
        this.fecha = LocalTime.now();
        this.nroOperacion = 1; //Autogenerado
    }

    //Getter Setter
    public long getNroOperacion() {
        return nroOperacion;
    }

    public void setNroOperacion(long nroOperacion) {
        this.nroOperacion = nroOperacion;
    }

    public LocalTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalTime fecha) {
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
