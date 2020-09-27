
package requiredModels.operacion.egreso;



import requiredModels.operacion.Operacion;
import requiredModels.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class OperacionEgreso extends Operacion {
    //Atributos
    private DetalleCompra detalle;
    private MedioDePago medioDePago;
    private List<Usuario> revisores;
    private List<Presupuesto> presupuestos;


}
