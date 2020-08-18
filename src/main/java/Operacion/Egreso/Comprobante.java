package Operacion.Egreso;

public class Comprobante {
    private long nroComprobante;
    private ComprobanteDePago tipoComprobante;
    private String path;

    //Constructor
    public Comprobante(long nroComprobante, ComprobanteDePago tipoComprobante, String path) {
        this.nroComprobante = nroComprobante;
        this.tipoComprobante = tipoComprobante;
        this.path = path;
    }


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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
