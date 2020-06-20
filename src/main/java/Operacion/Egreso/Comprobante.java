package Egreso;

public class Comprobante {
    private long nroComprobante;
    private Egreso.ComprobanteDePago tipoComprobante;

    //Getter Setter
    public long getNroComprobante() {
        return nroComprobante;
    }
    public void setNroComprobante(long nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public Egreso.ComprobanteDePago getTipoComprobante() {
        return tipoComprobante;
    }
    public void setTipoComprobante(Egreso.ComprobanteDePago tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    //Constructor
    public Comprobante(long nroComprobante, Egreso.ComprobanteDePago comprobanteDePago){
        this.nroComprobante = nroComprobante;
        this.tipoComprobante = comprobanteDePago;
    }
}
