package Egreso;

import java.util.List;

public class Detalle {
    private List<Item> listaItems;

    public Detalle (List<Item> listaItems){
        this.listaItems = listaItems;
    }

    public void agregarItem (Item nuevoItem){
        listaItems.add(nuevoItem);
    }

    public Integer total(){
        Integer total = 0;
        for (Item listaItem : listaItems) {
            total += listaItem.calcularSubtotal();
        }
        return total;
    }


}