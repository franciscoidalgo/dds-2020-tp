package APIMercadoPago.services;

import APIMercadoPago.modelos.Pais;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Collection;

public interface MercadoPagoService {

    @GET("countries/")
    Call<Pais[]> countries ();

    @GET("/classified_locations/countries/{Country_id}")
    Call<Pais> country (@Path("Country_id") String id);

}
