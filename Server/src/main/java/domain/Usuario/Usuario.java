package domain.Usuario;

import domain.Entidad.Entidad;
import domain.Entidad.EntidadPersistente;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Operacion;
import domain.Usuario.BandejaMensaje.BandejaMensaje;
import domain.Usuario.BandejaMensaje.Mensaje;

import javax.persistence.*;
import java.util.List;

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

    public List<Mensaje> darseDeBajaEn(OperacionEgreso unEgreso){
        unEgreso.sacaRevisor(this);
        List<Mensaje>  mensajesBorrar=  bandejaDeMensajes.sacarMensajeRelacionadosA(unEgreso);
       return mensajesBorrar;
    }

    public void recibiMensaje(Mensaje unMensaje){
        this.bandejaDeMensajes.agregateMensaje(unMensaje);
    }

    public void realizaOperacion(Operacion operacion) {
        this.entidadPertenece.realizaOperacion(operacion);
    }
}
