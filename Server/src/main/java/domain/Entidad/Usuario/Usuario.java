package domain.Entidad.Usuario;

import domain.Entidad.Entidad;
import domain.Entidad.EntidadPersistente;
import domain.Entidad.EntidadJuridica;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadPersistente {
    //Atributo
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "password")
    private String password;

    @Column(name = "rol",columnDefinition = "CHAR")
    private Rol rol;

    @Column(name = "username")
    private String username;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private EntidadJuridica entidadPertenece;

    @Transient
    private Entidad entidadSeleccionada;

    @Embedded
    private BandejaMensaje bandejaDeMensajes;

    public Usuario (){
        this.bandejaDeMensajes = new BandejaMensaje();
    }

    public Usuario(String nombre, String password, Rol rol) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.bandejaDeMensajes = new BandejaMensaje();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void entidadSeleccionada(EntidadJuridica seleccionada){
        this.entidadSeleccionada = seleccionada;
    }

    public void recibiMensaje(Mensaje unMensaje){
        this.bandejaDeMensajes.agregateMensaje(unMensaje);
    }

}
