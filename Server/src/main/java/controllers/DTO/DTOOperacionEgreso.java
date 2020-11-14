package controllers.DTO;

import java.time.LocalDate;

public class DTOOperacionEgreso {
    private int id;
    private double montoTotal;
    private LocalDate fecha;
    private Boolean estaAsociado = false;
    private DTOOperacionIngreso ingreso;

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

    public Boolean getEstaAsociado() {
        return estaAsociado;
    }

    public void setEstaAsociado(Boolean estaAsociado) {
        this.estaAsociado = estaAsociado;
    }

    public DTOOperacionIngreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(DTOOperacionIngreso ingreso) {
        this.ingreso = ingreso;
    }
}
