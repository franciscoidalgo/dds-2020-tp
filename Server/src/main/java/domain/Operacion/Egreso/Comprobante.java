package domain.Operacion.Egreso;

import domain.Entidad.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "comprobante")
public class Comprobante extends Entidad {
    @Column(name = "nro_comprobante")
    private long nroComprobante;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private TipoComprobante tipoComprobante;

    @Column(name = "path")
    private String path;

    //Constructor
    public Comprobante(long nroComprobante, TipoComprobante tipoComprobante, String path) {
        this.nroComprobante = nroComprobante;
        this.tipoComprobante = tipoComprobante;
        this.path = path;
    }

    public Comprobante() {}


    //Getter Setter
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
