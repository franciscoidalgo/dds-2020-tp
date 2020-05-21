package Egreso;

public class Comprobante {
    private long nroComprobante;
    private TipoComprobante tipoComprobante;

    public long getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(long nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public TipoComprobante getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }
}
