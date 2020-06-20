package Egreso;

import java.util.List;

public class Detalle {
    private List<Item> listaItems;

    //Constructor
    public Detalle (List<Item> listaItems){
        this.listaItems = listaItems;
    }

    //Funcionalidad
    public void agregarItem (Item nuevoItem){
        listaItems.add(nuevoItem);
    }

    public float calcularSubtotal(){
        float total = 0;
        for (Item item : listaItems) { total += item.getPrecio(); }
        return total;
    }
}