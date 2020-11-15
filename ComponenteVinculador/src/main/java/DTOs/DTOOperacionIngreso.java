package DTOs;

import Criterio.CriterioAsociacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOOperacionIngreso implements Serializable{
    //Atributos
    protected int id;
    protected double montoTotal;
    protected LocalDate fecha;
    private LocalDate fechaAceptabilidad;

    //Egresos
    private List<DTOOperacionEgreso> egresos;


    //Constructor
    public DTOOperacionIngreso(){
        this.egresos = new ArrayList<>();
    }

    public DTOOperacionIngreso(int id, double montoTotal,
                               LocalDate fecha, LocalDate fechaAceptabilidad){
        this.id = id;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
        this.fechaAceptabilidad = fechaAceptabilidad;
        this.egresos = new ArrayList<>();
    }

    public DTOOperacionIngreso(DTOOperacionIngreso oi){
        this.setId(oi.getId());
        this.setMontoTotal(oi.getMontoTotal());
        this.setFecha(oi.getFecha());
        this.setFechaAceptabilidad(oi.getFechaAceptabilidad());
    }


    //Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalDate getFechaAceptabilidad() { return fechaAceptabilidad; }
    public void setFechaAceptabilidad(LocalDate fechaAceptabilidad) { this.fechaAceptabilidad = fechaAceptabilidad; }

    public List<DTOOperacionEgreso> getEgresos() { return egresos; }
    public void setEgresos(List<DTOOperacionEgreso> egresos) { this.egresos = egresos; }


    //Funcionalidades
    public static void reemplazar(DTOOperacionIngreso i1, DTOOperacionIngreso i2){
        i1.setId(i2.getId());
        i1.setMontoTotal(i2.getMontoTotal());
        i1.setFecha(i2.getFecha());
        i1.setFechaAceptabilidad(i2.getFechaAceptabilidad());
    }

    public static void ordenarPorMonto(List<DTOOperacionIngreso> ingresos) {
        for(int i=0; i<ingresos.size() - 1; i++){
            for(int j=0; j<ingresos.size() - i - 1; j++){
                if(ingresos.get(j+1).getMontoTotal() > ingresos.get(j).getMontoTotal()){
                    DTOOperacionIngreso auxIngreso = new DTOOperacionIngreso(ingresos.get(j+1));
                    DTOOperacionIngreso.reemplazar(ingresos.get(j+1), ingresos.get(j));
                    DTOOperacionIngreso.reemplazar(ingresos.get(j), auxIngreso);
                }
            }
        }
    }

    public Boolean tenesFechaSuperior(LocalDate fecha){
        return this.fecha.isAfter(fecha);
    }

    public static void ordenarPorFecha(List<DTOOperacionIngreso> ingresos){
        for(int i=0; i<ingresos.size() - 1; i++){
            for(int j=0; j<ingresos.size() - i - 1; j++){
                if(ingresos.get(j+1).tenesFechaSuperior(ingresos.get(j).getFecha())){
                    DTOOperacionIngreso auxEgreso = new DTOOperacionIngreso(ingresos.get(j+1));
                    DTOOperacionIngreso.reemplazar(ingresos.get(j+1), ingresos.get(j));
                    DTOOperacionIngreso.reemplazar(ingresos.get(j), auxEgreso);
                }
            }
        }
    }



    public void agregateEgreso(DTOOperacionEgreso operacionEgreso){ this.egresos.add(operacionEgreso); }

    public void agregarListaDeEgresos(List<DTOOperacionEgreso> nuevosEgresos, CriterioAsociacion criterioAsociacion){
        List<DTOOperacionEgreso> egresosValidos = nuevosEgresos.stream()
                .filter(criterioAsociacion.getCriterio())
                .collect(Collectors.toList());
        System.out.println(egresosValidos);
        System.out.println(this.egresos.addAll(egresosValidos));
    }
}
