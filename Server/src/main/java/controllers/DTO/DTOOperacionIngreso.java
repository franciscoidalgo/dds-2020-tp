package controllers.DTO;

import java.time.LocalDate;

public class DTOOperacionIngreso {
    private int id;
    private double montoTotal;
    private LocalDate fecha;
    private LocalDate fechaAceptabilidad;

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
}
