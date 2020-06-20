package java.Operacion.Ingreso;

import Egreso.OperacionEgreso;

import java.Operacion.Operacion;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OperacionIngreso extends Operacion {
    private String descripcion;
    private ArrayList<Egreso.OperacionEgreso> egresos;

    //Constructor
    public OperacionIngreso(long montoTotal, long nroOperacion, Timestamp fecha,
                           String descripcion, ArrayList<OperacionEgreso> egresos){
        this.montoTotal = montoTotal;
        this.nroOperacion = nroOperacion;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.egresos = egresos;
    }

    //Getter Setter
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public ArrayList<OperacionEgreso> getEgresos() { return egresos; }
    public void setEgresos(ArrayList<OperacionEgreso> egresos) { this.egresos = egresos; }

    //Funcionalidad
    public void agregateEgreso(OperacionEgreso operacionEgreso){ this.egresos.add(operacionEgreso); }

    public float saldoOperacion(){
        float operacionesEgreso = 0;
        for(OperacionEgreso e : egresos){
            Detalle d = e.getDetalle();
            operacionesEgreso += d.calcularSubtotal();
        }
        return this.montoTotal - operacionesEgreso;
    }
}
