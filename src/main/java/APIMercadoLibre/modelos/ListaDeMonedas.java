package APIMercadoLibre.modelos;

import java.util.Arrays;
import java.util.List;

public class ListaDeMonedas {

    private List<Moneda> monedas;

    public ListaDeMonedas(Moneda[] monedas){
        this.monedas = Arrays.asList(monedas);
    }

    public List<Moneda> monedas(){
        return monedas;
    }

}
