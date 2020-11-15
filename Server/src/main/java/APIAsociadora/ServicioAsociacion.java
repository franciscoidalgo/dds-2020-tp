package APIAsociadora;

import controllers.DTO.DTOOperacionIngreso;
import controllers.convertersDTO.ConverterIngreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

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


    public OperacionIngreso getIngresoAsociado (String operacionIngreso, String listaEgresos) throws IOException {
        AsociadoraService asociadoraService = this.retrofit.create(AsociadoraService.class);
        Call<DTOOperacionIngreso> requestIngresoVinculado = asociadoraService.ingresoVinculado(operacionIngreso, listaEgresos,"ORDEN_VALOR_PRIMERO_EGRESO");
        Response<DTOOperacionIngreso> responseIngresoVinculado = requestIngresoVinculado.execute();
        DTOOperacionIngreso ingresoAsociado = responseIngresoVinculado.body();

        System.out.println("INGRESO ESTA ASOCIANDOSE con id => "+ ingresoAsociado.getId());

        return ConverterIngreso.generarIngresoVinculadorModel(ingresoAsociado);

    }

}
