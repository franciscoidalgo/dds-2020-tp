package domain.Entidad;


import domain.DireccionPostal.DireccionPostal;

import javax.persistence.*;

@Entity
@DiscriminatorValue("juridica")
public abstract class EntidadJuridica extends Entidad {

    @Column(name = "razon_social")
    protected String razonSocial;

    @Column(name = "nombre")
    protected String nombre;

    @Column(name = "cuit")
    protected long cuit;

    @Column(name = "descripcion")
    protected String descripcion;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
    protected DireccionPostal direccionPostal;

    public EntidadJuridica(String razonSocial, String nombre, long cuit, String descripcion, DireccionPostal direccionPostal) {
        this.razonSocial = razonSocial;
        this.nombre = nombre;
        this.cuit = cuit;
        this.descripcion = descripcion;
        this.direccionPostal = direccionPostal;
    }


    public EntidadJuridica() {
        super();
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCuit() {
        return cuit;
    }

    public void setCuit(long CUIT) {
        this.cuit = CUIT;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(DireccionPostal direccionPostal) {
        this.direccionPostal = direccionPostal;
    }


}
