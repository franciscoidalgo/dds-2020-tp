package domain.Entidad.Usuario;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "bandeja_mensajes")
public class BandejaMensaje extends EntidadPersistente {

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private final List<Mensaje> mensajes;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private  Usuario usuario;

    //Constructors
    public BandejaMensaje() {
        this.mensajes = new ArrayList<>();
    }

    //Getters + Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //Funcionalidades

    public void filtraPorLeido(){
        this.mensajes.stream()
                .sorted(Comparator.comparing(Mensaje::getFechaLeido))
                .collect(Collectors.toList());
    }

    public void filtraPorEnvio(){
        this.mensajes.stream()
                .sorted((Comparator.comparing(Mensaje::getFechaEnvio)))
                .collect(Collectors.toList());
    }

    public void agregateMensaje(Mensaje unMensaje){
        this.mensajes.add(unMensaje);
        this.mensajes.stream().collect(Collectors.toSet());
    }

    public void borrarMensaje(Mensaje unMensaje){
        this.mensajes.remove(unMensaje);
    }

    public Mensaje mostraMensaje(int pos){
        mensajes.get(pos).actualizateLeido();
        return mensajes.get(pos);
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }
}
