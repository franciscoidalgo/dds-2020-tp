package Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BandejaMensaje {

    private ArrayList<Mensaje> mensajes;

    public BandejaMensaje() {
        this.mensajes = new ArrayList<>();
    }

    public void filtraPorLeido(){
        this.mensajes.stream()
                .sorted(Comparator.comparing(unMensaje  -> unMensaje.getFechaLeido()))
                .collect(Collectors.toList());
    }

    public void filtraPorEnvio(){
        this.mensajes.stream()
                .sorted((Comparator.comparing(unMensaje-> unMensaje.getFechaEnvio())))
                .collect(Collectors.toList());
    }

    public void agregateMensaje(Mensaje unMensaje){
        this.mensajes.add(unMensaje);
        this.mensajes.stream().collect(Collectors.toSet());
    }

    public void borrarMensaje(Mensaje unMensaje){
        try{
            this.mensajes.remove(unMensaje);
        }catch (Exception e){
            System.out.println("No se pudo eliminar mensaje. No existe");
        }
    }

    public Mensaje mostraMensaje(int pos){
        try{
            mensajes.get(pos).actualizateLeido();
            return mensajes.get(pos);
        }catch (Exception e){
            System.out.println("No se pudo encontrar mensaje. No existe");
            return null;
        }
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }
}
