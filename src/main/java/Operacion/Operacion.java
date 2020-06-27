package Operacion;

import Usuario.Usuario;

import java.time.LocalTime;
public abstract class Operacion {

    protected long nroOperacion;
    protected LocalTime fecha;
    protected Usuario creadoPor;

    public Operacion(){}

    //Getter Setter
    public long getNroOperacion() { return nroOperacion; }
    public void setNroOperacion(long nroOperacion) { this.nroOperacion = nroOperacion; }

    public LocalTime getFecha() { return fecha; }
    public void setFecha(LocalTime fecha) { this.fecha = fecha; }

    public Usuario getCreadoPor() {
        return creadoPor;
    }
    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    //Funcionalidad
    public void registrate(){
        //TODO: Agregar logica de registro(No definiada).
        //El registrate puede ser que sea dentro de la logica para definir una nueva operacion (constructor)
    }

    public abstract double montoTotal();


}
