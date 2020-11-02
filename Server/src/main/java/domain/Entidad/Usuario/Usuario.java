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

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "rol",columnDefinition = "CHAR")
    private Rol rol;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Entidad entidadPertenece;

    @Embedded
    private BandejaMensaje bandejaDeMensajes;

    //Constructors
    public Usuario (){
        this.bandejaDeMensajes = new BandejaMensaje();
    }

    public Usuario(String nombre, String password, Rol rol) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.bandejaDeMensajes = new BandejaMensaje();
    }

    public Usuario(String nombre, String password, Rol rol, Entidad entidadPertenece) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
        this.entidadPertenece = entidadPertenece;
        this.bandejaDeMensajes = new BandejaMensaje();
    }

    public Usuario(String apellido, String nombre, String username, String password, Rol rol, Entidad entidadPertenece) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.entidadPertenece = entidadPertenece;
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

    public BandejaMensaje getBandejaDeMensajes() {
        return bandejaDeMensajes;
    }

    public void setBandejaDeMensajes(BandejaMensaje bandejaDeMensajes) {
        this.bandejaDeMensajes = bandejaDeMensajes;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Entidad getEntidadPertenece() {
        return entidadPertenece;
    }

    public void setEntidadPertenece(Entidad entidadPertenece) {
        this.entidadPertenece = entidadPertenece;
    }
    //Funcionalidades

    public void darseDeAltaEn(OperacionEgreso unEgreso){
        unEgreso.agregateRevisor(this);
    }

    public void darseDeBajaEn(OperacionEgreso unEgreso){
        unEgreso.sacaRevisor(this);
    }

    public void recibiMensaje(Mensaje unMensaje){
        this.bandejaDeMensajes.agregateMensaje(unMensaje);
    }

}
