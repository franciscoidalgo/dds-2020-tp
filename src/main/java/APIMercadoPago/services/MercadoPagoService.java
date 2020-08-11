package APIMercadoPago.services;

import APIMercadoPago.modelos.Moneda;
import APIMercadoPago.modelos.Pais;
import APIMercadoPago.modelos.PaisExtendido;
import APIMercadoPago.modelos.ProvinciaExtendida;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MercadoPagoService {

    @GET("/countries")
    Call<Pais[]> paises();

    @GET("/classified_locations/countries/{Country_id}")
    Call<PaisExtendido> pais(@Path("Country_id") String id);

    @GET("/classified_locations/states/{State_id}")
    Call<ProvinciaExtendida> provincia (@Path("State_id") String id);

    @GET("currencies")
    Call<Moneda[]> monedas();



}
