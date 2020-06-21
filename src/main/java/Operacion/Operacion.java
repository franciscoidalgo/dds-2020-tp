package Operacion;

import Usuario.Usuario;

import java.time.LocalTime;
public abstract class Operacion {

    protected long nroOperacion;
    protected LocalTime fecha;
    protected Usuario creadoPor;

    public Operacion() {
        this.nroOperacion = nroOperacion;
        this.fecha = fecha;
        this.creadoPor = creadoPor;
    }

    //Getter Setter
    public long getNroOperacion() { return nroOperacion; }
    public void setNroOperacion(long nroOperacion) { this.nroOperacion = nroOperacion; }

    public LocalTime getFecha() { return fecha; }
    public void setFecha(LocalTime fecha) { this.fecha = fecha; }

    //Funcionalidad
    public void registrate(){
        //TODO: Agregar logica de registro(No definiada).
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public abstract double montoTotal();


}
