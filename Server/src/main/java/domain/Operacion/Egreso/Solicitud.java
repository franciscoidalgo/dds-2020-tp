package domain.Operacion.Egreso;

import domain.Entidad.Entidad;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Solicitud extends Entidad {

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private final List<Item> listaItems;

    //Constructor
    public Solicitud(){
        this.listaItems = new ArrayList<>();
    }

    //Funcionalidad
    public void agregaItem(Item nuevoItem){
       this.listaItems.add(nuevoItem);
    }

    public void borrarItem(Item nuevoItem){
        this.listaItems.remove(nuevoItem);
    }

    public List<Item> getListaItems() {
        return listaItems;
    }

    public Boolean tieneItems(){
        return !this.listaItems.isEmpty();
    }

    public Boolean coincidenSolicitud(Solicitud unaSolicitud){
        return unaSolicitud.getListaItems().stream().
                    map(this::tenesItem).
                    reduce(Boolean::logicalAnd).get();

    }

    private Boolean tenesItem(Item unItem) {
        return this.getListaItems().stream().
                anyMatch(item -> item.coincidenItems(unItem));
    }
}
