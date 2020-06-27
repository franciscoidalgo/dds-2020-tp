package Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BandejaMensaje {

    private List<Mensaje> mensajes;

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
        this.mensajes.remove(unMensaje);
    }
    /*
    Despues haces
    try{ borrarMensaje(mensaje) }
    catch(Exception e){
            System.out.println("No se pudo encontrar mensaje. No existe"); }
     */

    public Mensaje mostraMensaje(int pos){
        mensajes.get(pos).actualizateLeido();
        return mensajes.get(pos);
    }
    /*
    Despues haces
    try{ mostraMensaje(pos) }
    catch(Exception e){
       System.out.println("No se pudo encontrar mensaje. No existe");
    }
     */

    //Estas dos pruebas en un main o un test

    public List<Mensaje> getMensajes() {
        return mensajes;
    }
}
