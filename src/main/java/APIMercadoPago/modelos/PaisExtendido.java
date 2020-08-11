package APIMercadoPago.modelos;

import java.util.List;

public class PaisExtendido extends Pais {
    public char decimal_separator;
    public char thousand_separator;
    public String time_zone;
    public GeoInformation geo_information;
    public List<Provincia> states;
}
