package APIMercadoPago.modelos;

import java.util.List;

public class ProvinciaExtendida extends Provincia{
    public PaisReducido country;
    public GeoInformation geo_information;
    public String time_zone;
    public List<Ciudad> cities;



    private class PaisReducido {
        public String Id;
        public String name;
    }
}
