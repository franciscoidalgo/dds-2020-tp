package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "comprobante")
public class Comprobante extends EntidadPersistente {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "tipo_comprobante")
    private TipoComprobante tipoComprobante;

    @Column(name = "path")
    private String path;

    //Constructor
    public Comprobante(TipoComprobante tipoComprobante, String path) {

        this.tipoComprobante = tipoComprobante;
        this.path = path;
    }

    public Comprobante() {}


    //Getter Setter

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
