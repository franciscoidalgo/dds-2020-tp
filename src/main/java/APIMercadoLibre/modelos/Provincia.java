package APIMercadoLibre.modelos;

import java.util.Arrays;
import java.util.List;

public class Provincia extends Identificable {
    public Identificable country;
    public GeoInformation geo_information;
    public String time_zone;
    public Ciudad[] cities;


    public List<Ciudad> mostrarCiudades(){
        return Arrays.asList(this.cities);
    }
}
