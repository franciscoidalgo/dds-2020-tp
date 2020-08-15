package APIMercadoPago.services;

import APIMercadoPago.modelos.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MercadoLibreService {

    @GET("/countries/{Country_id}/zip_codes/{Zip_code}")
    Call<CodigoPostalAPI> codigoPostalApi (@Path("Country_id") String countryId, @Path("Zip_code") String zipCode);

    @GET("/classified_locations/countries")
    Call<PaisSimplificado[]> paises();

    @GET("/classified_locations/countries/{Country_id}")
    Call<Pais> pais(@Path("Country_id") String id);

    @GET("/classified_locations/states/{State_id}")
    Call<Provincia> provincia (@Path("State_id") String id);

    @GET("/classified_locations/cities/{City_id}")
    Call<Ciudad> ciudad (@Path("City_id") String id);

    @GET("currencies")
    Call<Moneda[]> monedas();

    @GET("/currencies/{Currency_id}")
    Call<Moneda> moneda();

    @GET("/currency_conversions/search")
    Call<ConversionDeMonedas> conversionDeMonedas(@Query("from") String fromId, @Query("to") String toId);



}
