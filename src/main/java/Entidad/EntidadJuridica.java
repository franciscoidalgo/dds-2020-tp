package Entidad;


import DireccionPostal.DireccionPostal;
import Entidad.CategorizacionOperacion.CategoriaOperacion;
import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Operacion;

import java.util.ArrayList;
import java.util.List;

public abstract class EntidadJuridica implements Entidad {
    protected String razonSocial;
    protected String nombre;
    protected long CUIT;
    protected String descripcion;
    protected DireccionPostal direccionPostal;
    protected long codIGJ;
    protected List<EntidadBase> entidadesBases;
    protected List<Operacion> operaciones;
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

    public String mostraDireccion(){
        return this.direccionPostal.mostraDireccion();
    }
}
