package Operacion;

import Usuario.Usuario;

import java.time.LocalTime;
public abstract class Operacion {

    protected long nroOperacion;
    protected LocalTime fecha;
    protected Usuario creadoPor;

    //Getter Setter
    protected long getNroOperacion() { return nroOperacion; }
    protected void setNroOperacion(long nroOperacion) { this.nroOperacion = nroOperacion; }

    protected LocalTime getFecha() { return fecha; }
    protected void setFecha(LocalTime fecha) { this.fecha = fecha; }

    protected Usuario getCreadoPor() {
        return creadoPor;
    }
    protected void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    //Funcionalidad
    protected abstract double montoTotal();
}
