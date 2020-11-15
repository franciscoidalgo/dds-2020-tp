package controllers.DTO;

import java.time.LocalDate;
import java.util.List;

public class DTOOperacionIngreso {
    private int id;
    private double montoTotal;
    private LocalDate fecha;
    private LocalDate fechaAceptabilidad;
    private List<DTOOperacionEgreso> egresos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaAceptabilidad() {
        return fechaAceptabilidad;
    }

    public void setFechaAceptabilidad(LocalDate fechaAceptabilidad) {
        this.fechaAceptabilidad = fechaAceptabilidad;
    }

    public List<DTOOperacionEgreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<DTOOperacionEgreso> egresos) {
        this.egresos = egresos;
    }
}
