package Operacion.Ingreso;


import Operacion.Egreso.Detalle;
import Operacion.Egreso.OperacionEgreso;
import Operacion.Operacion;
import Usuario.Usuario;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OperacionIngreso extends Operacion {

    private long montoTotal;
    private String descripcion;
    private List<OperacionEgreso> egresos;

    //Constructor
    public OperacionIngreso(long montoTotal, String descripcion, Usuario creadoPor){
        this.nroOperacion = 1; //Autogenerado
        this.fecha = LocalTime.now();
        this.creadoPor = creadoPor;

        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
        this.egresos = new ArrayList<>();
    }
    //Este es el proceso de registro de la operacion

    //Getter Setter
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<OperacionEgreso> getEgresos() { return egresos; }
    public void setEgresos(List<OperacionEgreso> egresos) { this.egresos = egresos; }

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
    protected double montoTotal() {
        return montoTotal;
    }
}
