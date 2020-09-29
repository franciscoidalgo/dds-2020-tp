package domain.Operacion.Egreso;

import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "proveedor")
public class Proveedor extends Entidad {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "dni")
    private int DNI;

    @Column(name = "cuit")
    private long CUIT;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
    private DireccionPostal dirPostal;

    //Constructors
    public Proveedor(String nombre, String razonSocial, int DNI, long CUIT, DireccionPostal dirPostal){
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.DNI = DNI;
        this.CUIT = CUIT;
        this.dirPostal = dirPostal;
    }

    public Proveedor() {}

    //Getter and Setter
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getDNI() {
        return DNI;
    }
    public void setDNI(int DNI) {
        this.DNI = DNI;
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
