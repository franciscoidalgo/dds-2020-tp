package Usuario;

import Entidad.CategorizacionOperacion.Criterio;
import Entidad.Entidad;
import Entidad.EntidadJuridica;
import Operacion.Egreso.OperacionEgreso;
import Operacion.Ingreso.OperacionIngreso;
import Operacion.Operacion;


public class Usuario {
    //Atributo
    private String nombre;
    private String contraseña;
    private Rol rol;
    private EntidadJuridica entidadPertenece;//Entidad Juridica a la que pertenece el usuario
    private Entidad entidadSeleccionada;
    private BandejaMensaje bandejaDeMensajes;

    public Usuario(String nombre, String contraseña, Rol rol, EntidadJuridica entidadPertenece) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
        this.entidadPertenece = entidadPertenece;
        this.entidadSeleccionada = entidadPertenece;
        this.bandejaDeMensajes = new BandejaMensaje();
    }
//Getters-Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public EntidadJuridica getEntidadPertenece() {
        return entidadPertenece;
    }

    public void setEntidadPertenece(EntidadJuridica entidadPertenece) {
        this.entidadPertenece = entidadPertenece;
    }

    public Entidad getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(Entidad entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    public BandejaMensaje getBandejaDeMensajes() {
        return bandejaDeMensajes;
    }

    public void setBandejaDeMensajes(BandejaMensaje bandejaDeMensajes) {
        this.bandejaDeMensajes = bandejaDeMensajes;
    }

    //Funcionalidades

   public void realizaOperacion(Operacion unaOperacion){

        this.entidadPertenece.realizaOperacion(unaOperacion);
    }

    public  void asociaEgresoAIngreso(OperacionEgreso unEgreso, OperacionIngreso unIngreso){
        unIngreso.agregateEgreso(unEgreso);
    }

    public void darseDeAltaEn(OperacionEgreso unEgreso){
        unEgreso.agregateRevisor(this);
    }

    public void darseDeBajaEn(OperacionEgreso unEgreso){
        unEgreso.sacaRevisor(this);
    }

    public void daleJerarquiA(Criterio unCriterio,int nivelJerarquia) throws Exception {

        if (this.rol.criterioCredenciales()) {
            unCriterio.setNivelJerarquia(nivelJerarquia);
        }else {
            throw new Exception("No tiene permiso para hacer esto");
        }
    }

    public void entidadSeleccionada(Entidad seleccionada) throws Exception {

        if (this.entidadPertenece == seleccionada || this.entidadPertenece.tieneEntidadBase(seleccionada)) {
            this.entidadSeleccionada = seleccionada;
        } else {
            throw new Exception("No puede seleccionar esta entidad");
        }


    }

}
