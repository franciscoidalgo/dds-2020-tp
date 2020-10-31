package domain.Entidad;


import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.CategorizacionOperacion.Criterio;
import domain.Operacion.Operacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@DiscriminatorValue("juridica")
public abstract class EntidadJuridica extends Entidad {

    @Column(name = "razon_social")
    protected String razonSocial;

    @Column(name = "nombre")
    protected String nombre;

    @Column(name = "cuit")
    protected long CUIT;

    @Column(name = "descripcion")
    protected String descripcion;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
    protected DireccionPostal direccionPostal;


    public EntidadJuridica(String razonSocial, String nombre, long CUIT, String descripcion, DireccionPostal direccionPostal) {
        this.razonSocial = razonSocial;
        this.nombre = nombre;
        this.CUIT = CUIT;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCUIT() {
        return CUIT;
    }

    public void setCUIT(long CUIT) {
        this.CUIT = CUIT;
    }

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
