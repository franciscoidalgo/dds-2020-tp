package APIMercadoPago.services;

import APIMercadoPago.modelos.*;
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
        Call<Pais[]> requestListaDePaises = mercadoPagoService.paises();
        Response<Pais[]> responseListaDePaises = requestListaDePaises.execute();
        ListaDePaises listaDePaises = new ListaDePaises(responseListaDePaises.body());

        return listaDePaises;

    }

    public PaisExtendido paisDeId (String cualId) throws IOException {
        MercadoPagoService mercadoPagoService = this.retrofit.create(MercadoPagoService.class);
        Call<PaisExtendido> requestPaisDeId = mercadoPagoService.pais(cualId);
        Response<PaisExtendido> responsePaisDeId = requestPaisDeId.execute();
        PaisExtendido paisDeNombre = responsePaisDeId.body();

        return paisDeNombre;
    }

    public PaisExtendido paisDeNombre (String cualNombre) throws Exception {
        String idDelPais = this.listaDePaises().idDePais(cualNombre);
        return paisDeId(idDelPais);
    }

    public ProvinciaExtendida provinciaDeId (String cualId) throws IOException {
        MercadoPagoService mercadoPagoService = this.retrofit.create(MercadoPagoService.class);
        Call<ProvinciaExtendida> requestProvinciaDeId = mercadoPagoService.provincia(cualId);
        Response<ProvinciaExtendida> responseProvinciaDeId = requestProvinciaDeId.execute();
        ProvinciaExtendida provinciaDeId = responseProvinciaDeId.body();
        return provinciaDeId;
    }

    public ListaDeMonedas listaDeMonedas () throws IOException {
        MercadoPagoService mercadoPagoService = this.retrofit.create(MercadoPagoService.class);
        Call<Moneda[]> requestListaDeMonedas = mercadoPagoService.monedas();
        Response<Moneda[]> responseListaDeMonedas = requestListaDeMonedas.execute();
        ListaDeMonedas listaDeMonedas = new ListaDeMonedas(responseListaDeMonedas.body());

        return listaDeMonedas;

    }

}
