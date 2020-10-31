package APIMercadoLibre.modelos;

public class Moneda extends Identificable {
    public String description;
    public String symbol;
    public Integer decimal_places;


    public String mostraSimbolo() {
        return this.symbol;
    }
    @Override
    public String getNombre(){
        return description;
    }

}
