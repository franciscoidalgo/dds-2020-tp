package APIMercadoPago.modelos;

public class ProvinciaExtendida extends Provincia{
    public PaisReducido country;
    public GeoInformation geo_information;
    public String time_zone;
    public Ciudad[] cities;



    private class PaisReducido {
        public String Id;
        public String name;
    }
}
