package domain.Entidad.Usuario;

import domain.Entidad.Entidad;
import domain.Entidad.EntidadJuridica;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "usuario")
public class Usuario extends Entidad{
    //Atributo
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "password")
    private String password;

    @Transient
    private Rol rol;

    @Transient
    private EntidadJuridica entidadPertenece;//domain.Entidad Juridica a la que pertenece el usuario

    @Transient
    private Entidad entidadSeleccionada;

    @Transient
    private BandejaMensaje bandejaDeMensajes;

    public Usuario (){
    }

    public Usuario(String nombre, String password, Rol rol, EntidadJuridica entidadPertenece) {
        this.nombre = nombre;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public  void asociaEgresoAIngreso(OperacionEgreso unEgreso, OperacionIngreso unIngreso){
        unIngreso.agregateEgreso(unEgreso);
    }

    public void darseDeAltaEn(OperacionEgreso unEgreso){
        unEgreso.agregateRevisor(this);
    }

    public void darseDeBajaEn(OperacionEgreso unEgreso){
        unEgreso.sacaRevisor(this);
    }

    public void entidadSeleccionada(Entidad seleccionada){
        this.entidadSeleccionada = seleccionada;
    }

    public void recibiMensaje(Mensaje unMensaje){
        this.bandejaDeMensajes.agregateMensaje(unMensaje);
    }
    /*
    public void entidadSeleccionada(domain.Entidad seleccionada) throws Exception {
        if (this.entidadPertenece == seleccionada || this.entidadPertenece.tieneEntidadBase(seleccionada)) {
            this.entidadSeleccionada = seleccionada;
        } else {
            throw new Exception("No puede seleccionar esta entidad");
        }
    }*/
    //Los if y el throw van en el test
}
