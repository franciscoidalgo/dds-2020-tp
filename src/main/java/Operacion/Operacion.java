package Operacion;

import java.time.LocalTime;

public abstract class Operacion {

    protected long nroOperacion;
    protected LocalTime fecha;

    //Getter Setter

    public long getNroOperacion() { return nroOperacion; }
    public void setNroOperacion(long nroOperacion) { this.nroOperacion = nroOperacion; }

    public LocalTime getFecha() { return fecha; }
    public void setFecha(LocalTime fecha) { this.fecha = fecha; }

    //Funcionalidad
    public void registrate(){
        //TODO: Agregar logica de registro(No definiada).
    }

    public abstract double montoTotal();


}
