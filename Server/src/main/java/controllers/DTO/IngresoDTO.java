package controllers.DTO;

import domain.Operacion.Ingreso.TipoIngreso;

import java.util.List;

public class IngresoDTO {


    private int id;
    private TipoIngreso tipoIngreso;
    private String fechaRealizada;
    private String fechaAceptacion;
    private String descripcion;
    private Double monto;
    private Double saldo;
    private Double costo;
    private List<EgresoDTO> listaEgresos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public TipoIngreso getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(TipoIngreso idTipoIngreso) {
        this.tipoIngreso = idTipoIngreso;
    }

    public String getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(String fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public String getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(String fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public List<EgresoDTO> getListaEgresos() {
        return listaEgresos;
    }

    public void setListaEgresos(List<EgresoDTO> listaEgresos) {
        this.listaEgresos = listaEgresos;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
}
