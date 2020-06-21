package Operacion.Egreso;

import java.util.ArrayList;
import java.util.List;

public class Detalle {
    private List<Item> listaItems;

    //Constructor
    public Detalle (){
        this.listaItems = new ArrayList<>();
    }

    //Funcionalidad
    public void agregaItem(Item nuevoItem){
       this.listaItems.add(nuevoItem);
    }

    public double calcularSubtotal(){
       return this.listaItems.isEmpty()? 0: this.listaItems.stream().mapToDouble(Item::getPrecio)
                    .sum();
        }
    }
