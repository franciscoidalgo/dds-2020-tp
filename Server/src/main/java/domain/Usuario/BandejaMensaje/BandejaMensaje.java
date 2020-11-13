package domain.Usuario.BandejaMensaje;

import domain.Operacion.Egreso.OperacionEgreso;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class BandejaMensaje {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mensaje> mensajes;


    public class BandejaMensajeDTO {
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

    public List<Mensaje> filtraPorLeido() {
        return this.mensajes.stream()
                .sorted(Comparator.comparing(Mensaje::getFechaLeido))
                .collect(Collectors.toList());
    }

    public List<Mensaje> filtraPorEnvio() {
        return this.mensajes.stream()
                .sorted((Comparator.comparing(Mensaje::getFechaEnvio)))
                .collect(Collectors.toList());
    }

    public void agregateMensaje(Mensaje unMensaje) {
        this.mensajes.add(unMensaje);
    }

    public void borrarMensaje(Mensaje unMensaje) {
        this.mensajes.remove(unMensaje);
    }

    public Mensaje mostraMensaje(int pos) {
        mensajes.get(pos).actualizateLeido();
        return mensajes.get(pos);
    }

    public List<Mensaje> getMensajes() {
        return this.filtraPorEnvio();

    }

    public List<Mensaje> sacarMensajeRelacionadosA(OperacionEgreso unEgreso) {
        List<Mensaje> auxList = this.mensajes.stream()
                .filter(mensaje -> mensaje.getIdEgreso() == unEgreso.getId())
                .collect(Collectors.toList());
        this.mensajes = this.mensajes.stream()
                .filter(mensaje -> mensaje.getIdEgreso() != unEgreso.getId())
                .collect(Collectors.toList());
        return auxList;
    }

    public BandejaMensajeDTO toDTO() {
        BandejaMensajeDTO mensajesDTO = new BandejaMensajeDTO();
        mensajesDTO.setCantMensajes(this.mensajes.size());
        mensajesDTO.setMensajes(this.getMensajes());
        return mensajesDTO;
    }


}
