package APIAsociadora;

import com.google.gson.Gson;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServicioAsociacion {

    private static ServicioAsociacion instancia = null;
    private final Retrofit retrofit;

    private ServicioAsociacion (){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:9001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioAsociacion getInstancia(){
        if(instancia == null){
            instancia = new ServicioAsociacion();
        }
        return instancia;
    }

    public String getIngresoAsociadoBeta (String ingreso, String listaEgresos) throws IOException {
        AsociadoraService asociadoraService = this.retrofit.create(AsociadoraService.class);
        Call<String> requestIngresoVinculadoBeta = asociadoraService.ingresoVinculadoBeta(ingreso, listaEgresos);
        Response<String> responseIngresoVinculadoBeta = requestIngresoVinculadoBeta.execute();
        String respuesta = responseIngresoVinculadoBeta.body();
        return respuesta;

    }

    public OperacionIngreso getIngresoAsociado (OperacionIngreso operacionIngreso, List<OperacionEgreso> listaEgresos) throws IOException {
        Gson gson = new Gson();
        AsociadoraService asociadoraService = this.retrofit.create(AsociadoraService.class);
        Call<OperacionIngreso> requestIngresoVinculado = asociadoraService.ingresoVinculado(gson.toJson(operacionIngreso), gson.toJson(listaEgresos));
        Response<OperacionIngreso> responseIngresoVinculado = requestIngresoVinculado.execute();
        OperacionIngreso ingresoAsociado = responseIngresoVinculado.body();
        return ingresoAsociado;

    }

}
