package APIMercadoPago.services;

import APIMercadoPago.modelos.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MercadoLibreService {

    @GET("/countries")
    Call<Pais[]> paises();

    @GET("/classified_locations/countries/{Country_id}")
    Call<PaisExtendido> pais(@Path("Country_id") String id);

    @GET("/classified_locations/states/{State_id}")
    Call<ProvinciaExtendida> provincia (@Path("State_id") String id);

    @GET("currencies")
    Call<Moneda[]> monedas();

    @GET("/currencies/{Currency_id}")
    Call<Moneda> moneda();

    @GET("/currency_conversions/search")
    Call<ConversionDeMonedas> conversionDeMonedas(@Query("from") String fromId, @Query("to") String toId);



}
