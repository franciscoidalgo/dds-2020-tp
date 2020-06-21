package Operacion.Ingreso;


import Operacion.Egreso.Detalle;
import Operacion.Egreso.OperacionEgreso;
import Operacion.Operacion;

import java.time.LocalTime;
import java.util.ArrayList;

public class OperacionIngreso extends Operacion {

    private long montoTotal;
    private String descripcion;
    private ArrayList<OperacionEgreso> egresos;

    //Constructor
    public OperacionIngreso(long montoTotal, long nroOperacion, LocalTime fecha,
                           String descripcion){
        this.montoTotal = montoTotal;
        this.nroOperacion = nroOperacion;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.egresos = new ArrayList<>();
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

    @Override
    public double montoTotal() {
        return montoTotal;
    }
}
