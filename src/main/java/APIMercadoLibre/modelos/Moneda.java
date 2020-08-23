package APIMercadoLibre.modelos;

import DireccionPostal.Estandarizable;

public class Moneda implements Estandarizable {
    public String id;
    public String description;
    public String symbol;
    public Integer decimal_places;


    @Override
    public String mostraNombre() {
        return this.description;
    }
    public String mostraSimbolo() {
        return this.symbol;
    }
}
