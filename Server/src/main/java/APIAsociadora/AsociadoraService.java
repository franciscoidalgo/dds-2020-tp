package APIAsociadora;

import controllers.DTO.DTOOperacionIngreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AsociadoraService {

    @GET("/api/vinculacion")
    Call<DTOOperacionIngreso> ingresoVinculado(@Query("ingreso") String ingreso,
                                               @Query("listaEgresos") String listaEgresos,
                                               @Query("fechaDesde") String fechaDesde,
                                               @Query("fechaHasta") String fechaHasta);

}
