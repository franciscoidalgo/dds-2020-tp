package Operacion.Egreso.MediosDePago;

import java.sql.Timestamp;

public class Tarjeta {
    private Integer numeroTarjeta;
    private Timestamp fechaVencimiento;
    private Integer codSeguridad;
    private String tipoTarjeta;

    //Getter Setter
    public Integer getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(Integer numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public Timestamp getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Timestamp fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public Integer getCodSeguridad() { return codSeguridad; }
    public void setCodSeguridad(Integer codSeguridad) { this.codSeguridad = codSeguridad; }

    public String getTipoTarjeta() { return tipoTarjeta; }
    public void setTipoTarjeta(String tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }

    //Funcionalidad
    public void verificarValidezTarjeta(){

    }
}
