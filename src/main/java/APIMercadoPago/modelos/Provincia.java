package APIMercadoPago.modelos;

public class Provincia extends Identificador{
    public Identificador country;
    public GeoInformation geo_information;
    public String time_zone;
    public Ciudad[] cities;
}
