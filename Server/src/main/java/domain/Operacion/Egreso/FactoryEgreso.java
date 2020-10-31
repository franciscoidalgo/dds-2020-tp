package domain.Operacion.Egreso;

import com.google.gson.Gson;
import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryEgreso {
    public static OperacionEgreso get(Request request){
        Gson gson = new Gson();
        List<Item> items = new ArrayList<>();
        List<CategoriaOperacion> categorias = new ArrayList<>();
        List<String> descripcionesItems = Arrays.asList(gson.fromJson(request.queryParams("items"), String[].class));
        List<String> descripcionesCategorias = Arrays.asList(gson.fromJson(request.queryParams("items"), String[].class));
        for(String descripcion : descripcionesItems){
            items.add(new Item(descripcion,null, null));//todo hardcode
        }
        for(String descripcion : descripcionesCategorias){
            categorias.add(new CategoriaOperacion(descripcion));
        }
        OperacionEgreso operacionEgreso = new OperacionEgreso(null,2000,null,null,null,null,null);
                /*new MedioDePago(request.queryParams("medioDePago"), request.queryParams("moneda")),
                new DetalleOperacion(
                        new Proveedor(
                                request.queryParams("razonSocial"),
                                Long.parseLong(request.queryParams("CUIT")),
                                new DireccionPostal(
                                        request.queryParams("pais"),
                                        request.queryParams("provincia"),
                                        request.queryParams("ciudad"),
                                        request.queryParams("calle"),
                                        request.queryParams("altura"),
                                        request.queryParams("piso"),
                                        request.queryParams("dpto")
                                )),
                        items,
                        categorias,
                        new Comprobante()
                ),
                Double.parseDouble(request.queryParams("montoTotal")),
                null
                );
        if(request.queryParams("tipoComprobante").equals("Niguno")){
            operacionEgreso.getDetalle().getComprobante().setTipoComprobante(null);
        }else{
            operacionEgreso.getDetalle().getComprobante().setTipoComprobante(new TipoComprobante(request.queryParams("tipoComprobante"), null));
        }

                 */
        return operacionEgreso;

    }
}
