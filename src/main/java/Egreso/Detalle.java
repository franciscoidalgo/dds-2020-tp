package Egreso;

import java.util.List;

public class Detalle {
    private List<Item> listaItems;

    public Detalle (List<Item> listaItems){
        this.listaItems = listaItems;
    }

    public Integer subtotal(){
        Integer total = 0;
        for (int i = 0; i<listaItems.size(); i++){
            total += listaItems.get(i).calcularPrecio();
        }
        return total;
    }


}