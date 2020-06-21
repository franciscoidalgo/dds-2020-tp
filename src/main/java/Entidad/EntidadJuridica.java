package Entidad;


import Entidad.CategorizacionOperacion.CategoriaOperacion;
import Entidad.CategorizacionOperacion.Criterio;
import Operacion.Operacion;

import java.util.ArrayList;

public abstract class  EntidadJuridica implements Entidad {
    protected String razonSocial;
    protected String nombre;
    protected long CUIT;
    protected String descripcion;
    protected String direccionPostal;
    protected long codIGJ;
    protected ArrayList<EntidadBase> entidadesBases;
    protected ArrayList<Operacion> operaciones;
    protected ArrayList<Criterio> criterios;


    public EntidadJuridica(String razonSocial, String nombre, long CUIT, String descripcion, String direccionPostal, long codIGJ) {
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

    public String getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(String direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public long getCodIGJ() {
        return codIGJ;
    }

    public void setCodIGJ(long codIGJ) {
        this.codIGJ = codIGJ;
    }

    public ArrayList<EntidadBase> getEntidadesBases() {
        return entidadesBases;
    }

    public void setEntidadesBases(ArrayList<EntidadBase> entidadesBases) {
        this.entidadesBases = entidadesBases;
    }

    public ArrayList<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public void  realizaOperacion(Operacion unaOperacion){

    }

    public Criterio creaCriterio(String unNombre,Criterio criterio,int nivelJerarquia){
        return new Criterio(unNombre,criterio,nivelJerarquia);
    }

    public void creaCategoria(Criterio unCriterio,String unNombre){
        unCriterio.agregateCategoria(new CategoriaOperacion(unNombre));
    }
}
