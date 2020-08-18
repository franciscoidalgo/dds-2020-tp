package APIMercadoPago.modelos;

import java.util.Arrays;
import java.util.List;

public class Pais extends Identificador{
    public String locale;
    public String currency_id;
    public char decimal_separator;
    public char thousand_separator;
    public String time_zone;
    public GeoInformation geo_information;
    public Provincia[] states;

    public List<Provincia> mostrarProvincias(){
        return Arrays.asList(this.states);
    }

}
