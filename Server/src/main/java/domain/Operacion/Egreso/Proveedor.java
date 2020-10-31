package domain.Operacion.Egreso;

import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "proveedor")
public class Proveedor extends EntidadPersistente {

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "cuit")
    private long CUIT;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
    private DireccionPostal dirPostal;

    //Constructors
    public Proveedor(String razonSocial, long CUIT, DireccionPostal dirPostal){

        this.razonSocial = razonSocial;
        this.CUIT = CUIT;
        this.dirPostal = dirPostal;
    }

    public Proveedor() {}

    //Getter and Setter
    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public long getCUIT() {
        return CUIT;
    }
    public void setCUIT(long CUIT) {
        this.CUIT = CUIT;
    }

    public DireccionPostal getDirPostal() {
        return dirPostal;
    }
    public void setDirPostal(DireccionPostal dirPostal) {
        this.dirPostal = dirPostal;
    }


}
