package domain.Usuario.BandejaMensaje;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Embeddable
public class BandejaMensaje {

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Mensaje> mensajes;


    public class BandejaMensajeDTO{
        private List<Mensaje> mensajes;
        private int cantMensajes;

        public List<Mensaje> getMensajes() {
            return mensajes;
        }

        public void setMensajes(List<Mensaje> mensajes) {
            this.mensajes = mensajes;
        }

        public int getCantMensajes() {
            return cantMensajes;
        }

        public void setCantMensajes(int cantMensajes) {
            this.cantMensajes = cantMensajes;
        }
    }
    //Constructors
    public BandejaMensaje() {
        this.mensajes = new ArrayList<>();
        this.mensajes.add(new Mensaje("Bienvenida", "Bienvenido al sistema de gestion GeSoc."));
    }

    //Funcionalidades

    public List<Mensaje> filtraPorLeido(){
        return this.mensajes.stream()
                .sorted(Comparator.comparing(Mensaje::getFechaLeido))
                .collect(Collectors.toList());
    }

    public List<Mensaje> filtraPorEnvio(){
        return this.mensajes.stream()
                .sorted((Comparator.comparing(Mensaje::getFechaEnvio)))
                .collect(Collectors.toList());
    }

    public void agregateMensaje(Mensaje unMensaje){
        this.mensajes.add(unMensaje);
    }

    public void borrarMensaje(Mensaje unMensaje){
        this.mensajes.remove(unMensaje);
    }

    public Mensaje mostraMensaje(int pos){
        mensajes.get(pos).actualizateLeido();
        return mensajes.get(pos);
    }

    public List<Mensaje> getMensajes() {
        List<Mensaje> mensajesMostrar = mensajes;
        Collections.reverse(mensajesMostrar);
        return mensajesMostrar;

    }

    public BandejaMensajeDTO toDTO(){
            BandejaMensajeDTO mensajesDTO = new BandejaMensajeDTO();
            mensajesDTO.setCantMensajes(this.mensajes.size());
            mensajesDTO.setMensajes(this.getMensajes());
            return mensajesDTO;
    }

}
