package domain.Factories;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.DireccionPostal.DireccionPostal;
import domain.Operacion.Egreso.Proveedor;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;
import spark.Request;

public class FactoryProveedor {
    public static Proveedor get(Request request){
        DireccionPostal direccionPostal;
        Repositorio<Proveedor> proveedorRepositorio = FactoryRepo.get(Proveedor.class);


        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(request.body());//Parseando la request

        JsonObject rootObject = jsonElement.getAsJsonObject();// pasando a objeto
        JsonObject jProveedorAux = rootObject.getAsJsonObject("proveedor");//busco en propiedad Proveedor

        int idProveedor = jProveedorAux.get("id").getAsInt();
        Proveedor proveedor = proveedorRepositorio.buscar(idProveedor);

        if(proveedor == null || idProveedor<0){
            JsonObject jProveedor = jProveedorAux.getAsJsonObject("proveedor");
            proveedor = new Proveedor();
            direccionPostal = new DireccionPostal();
            proveedor.setCUIT(jProveedor.get("cuit").getAsInt());
            proveedor.setRazonSocial(jProveedor.get("razonSocial").getAsString());
            proveedor.setRazonSocial(jProveedor.get("razonSocial").getAsString());

            proveedor.setDirPostal(direccionPostal);

            direccionPostal.setPais(jProveedor.get("pais").getAsString());
            direccionPostal.setProvincia(jProveedor.get("provincia").getAsString());
            direccionPostal.setCiudad(jProveedor.get("ciudad").getAsString());
            direccionPostal.setAltura(jProveedor.get("altura").getAsInt());
            direccionPostal.setCalle(jProveedor.get("calle").getAsString());
            direccionPostal.setPiso(jProveedor.get("piso").getAsString());
            direccionPostal.setDpto(jProveedor.get("dpto").getAsString());

            proveedorRepositorio.agregar(proveedor);
        }
        return  proveedor;
    }
}
