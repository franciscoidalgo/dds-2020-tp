package APIMercadoLibre.modelos;

public class Moneda  {
    public String id;
    public String description;
    public String symbol;
    public Integer decimal_places;


    public String mostraSimbolo() {
        return this.symbol;
    }
}
