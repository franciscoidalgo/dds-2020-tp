package APIMercadoLibre.services;

import APIMercadoLibre.modelos.*;
import DireccionPostal.Direccion;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServicioMercadoLibre implements ServicioEstandarizacion {

    private static ServicioMercadoLibre instancia = null;
    private Retrofit retrofit;

    private ServicioMercadoLibre(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioMercadoLibre instancia(){
        if (instancia == null) {
            instancia = new ServicioMercadoLibre();
        }
        return instancia;
    }

    public CodigoPostalAPI informacionCodigoPostal (String idPais, String codigoPostal) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<CodigoPostalAPI> requestCodigoPostal = mercadoLibreService.codigoPostalApi(idPais, codigoPostal);
        Response<CodigoPostalAPI> responesCodigoPostal = requestCodigoPostal.execute();
        CodigoPostalAPI informacionCodigoPostal = responesCodigoPostal.body();

        return informacionCodigoPostal;

    }

    public ListaIdentificables listaDePaises () throws IOException {

        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<PaisSimplificado[]> requestListaDePaises = mercadoLibreService.paises();
        Response<PaisSimplificado[]> responseListaDePaises = requestListaDePaises.execute();
        ListaIdentificables listaDePaises = new ListaIdentificables(responseListaDePaises.body());

        return listaDePaises;

    }

    public ListaIdentificables listaDeProvincias (Pais deQuePais){
        return new ListaIdentificables(deQuePais.states);
    }

    public ListaIdentificables listaDeCiudades (Provincia deQueProvincia){
        return new ListaIdentificables(deQueProvincia.cities);
    }

    public Pais paisDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Pais> requestPaisDeId = mercadoLibreService.pais(cualId);
        Response<Pais> responsePaisDeId = requestPaisDeId.execute();
        Pais paisDeNombre = responsePaisDeId.body();

        return paisDeNombre;
    }

    public Pais paisDeNombre (String cualNombre) throws Exception {
        String idDelPais = this.listaDePaises().idDeIdentificador(cualNombre);
        return paisDeId(idDelPais);
    }

    public Provincia provinciaDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Provincia> requestProvinciaDeId = mercadoLibreService.provincia(cualId);
        Response<Provincia> responseProvinciaDeId = requestProvinciaDeId.execute();
        Provincia provinciaDeId = responseProvinciaDeId.body();
        return provinciaDeId;
    }

    public Ciudad ciudadDeId (String cualId) throws IOException{
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Ciudad> requestCiudad = mercadoLibreService.ciudad(cualId);
        Response<Ciudad> responseCiudad = requestCiudad.execute();
        Ciudad ciudadDeId = responseCiudad.body();

        return ciudadDeId;

    }
    @Override
    public ListaDeMonedas listaDeMonedas () throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Moneda[]> requestListaDeMonedas = mercadoLibreService.monedas();
        Response<Moneda[]> responseListaDeMonedas = requestListaDeMonedas.execute();
        ListaDeMonedas listaDeMonedas = new ListaDeMonedas(responseListaDeMonedas.body());

        return listaDeMonedas;

    }

    public Moneda monedaDeId (String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Moneda> requestMonedaDeId = mercadoLibreService.moneda();
        Response<Moneda> reponseMonedaDeId = requestMonedaDeId.execute();
        Moneda monedaDeId = reponseMonedaDeId.body();

        return monedaDeId;

    }

    public ConversionDeMonedas conversionDeMonedas (String idFrom, String idTo) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<ConversionDeMonedas> requestConversionDeMonedas = mercadoLibreService.conversionDeMonedas(idFrom, idTo);
        Response<ConversionDeMonedas> responseConversionDeMonedas = requestConversionDeMonedas.execute();
        ConversionDeMonedas conversionDeMonedas = responseConversionDeMonedas.body();

        return conversionDeMonedas;

    }

    @Override
    public List<Pais> generaPaises() throws IOException {
        List<Pais> paises= new ArrayList<Pais>();
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<PaisSimplificado[]> requestListaDePaises = mercadoLibreService.paises();
        Response<PaisSimplificado[]> responseListaDePaises = requestListaDePaises.execute();
        ListaIdentificables listaDePaises = new ListaIdentificables(responseListaDePaises.body());
        listaDePaises.mostraId().forEach(s -> {
            try {
                Pais pais = this.generaPais(s);
                paises.add(pais);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return paises;
    }

    private Pais generaPais(String cualId) throws IOException {
        MercadoLibreService mercadoLibreService = this.retrofit.create(MercadoLibreService.class);
        Call<Pais> requestPaisDeId = mercadoLibreService.pais(cualId);
        Response<Pais> responsePaisDeId = requestPaisDeId.execute();
        Pais paisDeNombre = responsePaisDeId.body();

        return paisDeNombre;
    }
    public Direccion generaDireccion(String idPais, String idProv, String idCiudad)throws IOException{
        return new Direccion(this.generaPais(idPais), this.ciudadDeId(idCiudad), this.provinciaDeId(idProv));
    };

    public void cargarInfo(){

    }

}
