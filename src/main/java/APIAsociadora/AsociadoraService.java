package APIAsociadora;

import domain.Operacion.Ingreso.OperacionIngreso;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AsociadoraService {

    @GET("/api/vinculacion")
    Call<OperacionIngreso> ingresoVinculado(@Query("ingreso") String ingreso, @Query("listaEgresos") String listaEgresos);

}
