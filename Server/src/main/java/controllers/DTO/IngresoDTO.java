package controllers.DTO;

public class IngresoDTO {


    private int id;
    private int idTipoIngreso;
    private String fechaRealizada;
    private String fechaAceptacion;
    private String descripcion;
    private Double monto;
    private int[] listaEgresos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Integer getIdTipoIngreso() {
        return idTipoIngreso;
    }

    public void setIdTipoIngreso(int idTipoIngreso) {
        this.idTipoIngreso = idTipoIngreso;
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

    public int[] getListaEgresos() {
        return listaEgresos;
    }

    public void setListaEgresos(int[] listaEgresos) {
        this.listaEgresos = listaEgresos;
    }
}
