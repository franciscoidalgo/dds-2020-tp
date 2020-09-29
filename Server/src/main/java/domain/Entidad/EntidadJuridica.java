package domain.Entidad;


import domain.DireccionPostal.DireccionPostal;
import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.CategorizacionOperacion.Criterio;
import domain.Operacion.Operacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "entidad_juridica")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
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

    @Column(name = "cod_igj")
    protected long codIGJ;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_base_id")
    protected List<EntidadBase> entidadesBases;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "operacion_id")
    protected List<Operacion> operaciones;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "criterio_id")
    protected List<Criterio> criterios;


    public EntidadJuridica(String razonSocial, String nombre, long CUIT, String descripcion, DireccionPostal direccionPostal, long codIGJ) {
        this.razonSocial = razonSocial;
        this.nombre = nombre;
        this.CUIT = CUIT;
        this.descripcion = descripcion;
        this.direccionPostal = direccionPostal;
        this.codIGJ = codIGJ;
        this.entidadesBases = new ArrayList<>();
        this.operaciones = new ArrayList<>();
        this.criterios = new ArrayList<>();
    }

    public EntidadJuridica() {
        this.entidadesBases = new ArrayList<>();
        this.operaciones = new ArrayList<>();
        this.criterios = new ArrayList<>();
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

    public long getCodIGJ() {
        return codIGJ;
    }

    public void setCodIGJ(long codIGJ) {
        this.codIGJ = codIGJ;
    }

    public List<EntidadBase> getEntidadesBases() {
        return entidadesBases;
    }

    public void setEntidadesBases(ArrayList<EntidadBase> entidadesBases) {
        this.entidadesBases = entidadesBases;
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public List<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(ArrayList<Criterio> criterios) {
        this.criterios = criterios;
    }

    //Funcionalidades!
    public void realizaOperacion(Operacion unaOperacion) {
        this.operaciones.add(unaOperacion);
    }

    public void creaCriterio(String unNombre) {
        this.criterios.add(new Criterio(unNombre));
    }

    public void creaCriterio(String unNombre, Criterio criterioPadre) {
        Criterio criterio = new Criterio(unNombre);
        this.criterios.add(criterio);
        criterioPadre.setCriterioHijo(criterio);
    }

    public void creaCategoria(Criterio unCriterio, String unNombre) {
        unCriterio.agregateCategoria(new CategoriaOperacion(unNombre));
    }

    public boolean tieneEntidadBase(Entidad base) {
        return this.entidadesBases.contains(base);
    }

}
