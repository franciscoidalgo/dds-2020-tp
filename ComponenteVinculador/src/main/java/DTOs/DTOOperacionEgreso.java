package DTOs;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DTOOperacionEgreso{
    //Atributos
    protected int id;
    protected double montoTotal;
    protected LocalDate fecha;
    private Boolean estaAsociado = false;
    //private DTOOperacionIngreso ingreso;


    //Constructor
    public DTOOperacionEgreso(){

    }

    public DTOOperacionEgreso(int id, double montoTotal,
                              LocalDate fecha, Boolean estaAsociado/*,
                              DTOOperacionIngreso ingreso*/){
        this.id = id;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
        this.estaAsociado = estaAsociado;
        //this.ingreso = ingreso;
    }

    public DTOOperacionEgreso(DTOOperacionEgreso oe){
        this.setId(oe.getId());
        this.setMontoTotal(oe.getMontoTotal());
        this.setFecha(oe.getFecha());
        this.setEstaAsociado(oe.getEstaAsociado());
        //this.setIngreso(oe.getIngreso());
    }

    //Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Boolean getEstaAsociado() { return estaAsociado; }
    public void setEstaAsociado(Boolean estaAsociado) { this.estaAsociado = estaAsociado; }

   /* public DTOOperacionIngreso getIngreso() { return ingreso; }
    public void setIngreso(DTOOperacionIngreso ingreso) { this.ingreso = ingreso; }
*/

    //Funcionalidades
    public Boolean podesVincularteSegunFecha(LocalDate fechaMax) {
        return this.fecha.isBefore(fechaMax) && !this.estaAsociado;
    }

    public static void reemplazar(DTOOperacionEgreso e1, DTOOperacionEgreso e2){
        e1.setId(e2.getId());
        e1.setMontoTotal(e2.getMontoTotal());
        e1.setFecha(e2.getFecha());
        e1.setEstaAsociado(e2.getEstaAsociado());
       // e1.setIngreso(e2.getIngreso());
    }

    public static List<DTOOperacionEgreso> ordenarPorMonto(List<DTOOperacionEgreso> egresos){

        return egresos.stream()
                .sorted((o1, o2) -> (int) Math.max(o1.getMontoTotal(),o2.getMontoTotal()))
                .collect(Collectors.toList());
        /*for(int i=0; i<egresos.size() - 1; i++){
            for(int j=0; j<egresos.size() - i - 1; j++){
                if(egresos.get(j+1).getMontoTotal() > egresos.get(j).getMontoTotal()){
                    DTOOperacionEgreso auxEgreso = new DTOOperacionEgreso(egresos.get(j+1));
                    DTOOperacionEgreso.reemplazar(egresos.get(j+1), egresos.get(j));
                    DTOOperacionEgreso.reemplazar(egresos.get(j), auxEgreso);
                }
            }
        }*/
    }

    public Boolean tenesFechaSuperior(LocalDate fecha){
        return this.fecha.isAfter(fecha);
    }

    public static void ordenarPorFecha(List<DTOOperacionEgreso> egresos){
        for(int i=0; i<egresos.size() - 1; i++){
            for(int j=0; j<egresos.size() - i - 1; j++){
                if(egresos.get(j+1).tenesFechaSuperior(egresos.get(j).getFecha())){
                    DTOOperacionEgreso auxEgreso = new DTOOperacionEgreso(egresos.get(j+1));
                    DTOOperacionEgreso.reemplazar(egresos.get(j+1), egresos.get(j));
                    DTOOperacionEgreso.reemplazar(egresos.get(j), auxEgreso);
                }
            }
        }
    }

    public boolean puedeVincularse(DTOOperacionEgreso egreso, DTOOperacionIngreso ingreso) {
        return (egreso.getEstaAsociado() == false
               // && egreso.getIngreso() == null
                && egreso.getFecha().isBefore(ingreso.getFechaAceptabilidad())
                && egreso.getMontoTotal() < ingreso.getMontoTotal());
    }

}
