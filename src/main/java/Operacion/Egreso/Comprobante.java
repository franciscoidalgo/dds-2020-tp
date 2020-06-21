package Operacion.Egreso;

public class Comprobante {
    private long nroComprobante;
    private ComprobanteDePago tipoComprobante;

    //Getter Setter
    public long getNroComprobante() {
        return nroComprobante;
    }
    public void setNroComprobante(long nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public ComprobanteDePago getTipoComprobante() {
        return tipoComprobante;
    }
    public void setTipoComprobante(ComprobanteDePago tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    //Constructor
    public Comprobante(long nroComprobante, ComprobanteDePago comprobanteDePago){
        this.nroComprobante = nroComprobante;
        this.tipoComprobante = comprobanteDePago;
    }
}
