package Operacion.Ingreso;


import Operacion.Egreso.OperacionEgreso;
import Operacion.Operacion;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class OperacionIngreso extends Operacion {

    private double montoTotal;
    private String descripcion;
    private List<OperacionEgreso> egresos;

    //Constructor
    public OperacionIngreso(double montoTotal, String descripcion, Usuario creadoPor){
        super(creadoPor);
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

    public double saldoOperacion(){
        return this.montoTotal - this.montoTotalEgresos();
    }

    @Override
    public double montoTotal() {
        return montoTotal;
    }

    private double montoTotalEgresos(){
        return this.egresos.stream().mapToDouble(OperacionEgreso::montoTotal).sum();
    }
}
