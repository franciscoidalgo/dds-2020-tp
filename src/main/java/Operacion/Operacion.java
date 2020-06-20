package java.Operacion;

import java.sql.Timestamp;

public abstract class Operacion {
    protected long montoTotal;
    protected long nroOperacion;
    protected Timestamp fecha;

    //Getter Setter
    public long getMontoTotal() { return montoTotal; }
    public void setMontoTotal(long montoTotal) { this.montoTotal = montoTotal; }

    public long getNroOperacion() { return nroOperacion; }
    public void setNroOperacion(long nroOperacion) { this.nroOperacion = nroOperacion; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    //Funcionalidad
    public void registrar(){
        //TODO: Agregar logica de registro(No definiada).
    }

}
