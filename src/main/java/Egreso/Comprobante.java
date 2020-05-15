package Egreso;

public class Comprobante {
    private long nroComprobante;
    private int tipo; //<---- TODO: Correccion Factura A, Factura a , factura A

    public long getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(long nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}


