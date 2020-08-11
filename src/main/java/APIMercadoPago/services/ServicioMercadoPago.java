package APIMercadoPago.services;

import APIMercadoPago.modelos.ListaDePaises;
import APIMercadoPago.modelos.Pais;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

public class ServicioMercadoPago {

    private static ServicioMercadoPago instancia = null;
    private Retrofit retrofit;

    private ServicioMercadoPago(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioMercadoPago instancia(){
        if (instancia == null) {
            instancia = new ServicioMercadoPago();
        }
        return instancia;
    }

    public ListaDePaises listaDePaises () throws IOException {

        MercadoPagoService mercadoPagoService = this.retrofit.create(MercadoPagoService.class);
        Call<Pais[]> requestListaDePaises = mercadoPagoService.countries();
        Response<Pais[]> responseListaDePaises = requestListaDePaises.execute();
        ListaDePaises listaDePaises = new ListaDePaises(responseListaDePaises.body());

        return listaDePaises;

    }

    public Pais paisDeNombre (String cualNombre) throws Exception {
        MercadoPagoService mercadoPagoService = this.retrofit.create(MercadoPagoService.class);
        String idDelPais = this.listaDePaises().idDePais(cualNombre);
        Call<Pais> requestPaisDeNombre = mercadoPagoService.country(idDelPais);
        Response<Pais> responsePaisDeNombre = requestPaisDeNombre.execute();
        Pais paisDeNombre = responsePaisDeNombre.body();

        return paisDeNombre;
    }

}
