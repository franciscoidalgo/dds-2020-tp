package APIAsociadora;

import controllers.DTO.DTOOperacionIngreso;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AsociadoraService {

    @GET("/api/vinculacion")
    Call<DTOOperacionIngreso> ingresoVinculado(@Query("ingreso") String ingreso,
                                               @Query("listaEgresos") String listaEgresos,
                                               @Query("criterio") String criterio
                                               );


}
