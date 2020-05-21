package src.main.java.Egreso;

public class MedioDePago {
    private TipoDePago tipoDePago;
    private long nroPago;

    public TipoDePago getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(TipoDePago tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public long getNroPago() {
        return nroPago;
    }

    public void setNroPago(long nroPago) {
        this.nroPago = nroPago;
    }
}
