package controllers.convertersDTO;

import controllers.DTO.EgresoDTO;
import domain.Operacion.Egreso.OperacionEgreso;

public class ConverterEgreso {

    public static EgresoDTO generarEgresoDTO(OperacionEgreso egreso) {
        EgresoDTO egresoDTO = new EgresoDTO();
        egresoDTO.setId(egreso.getId());
        egresoDTO.setPedido(egreso.getDetalle().getPedidos());
        egresoDTO.setCantPresupuestos(egreso.getCantPresupuestos());
        egresoDTO.setDetalle(egreso.getDetalle());
        egresoDTO.setMedioDePago(egreso.getMedioDePago());
        egresoDTO.setMontoTotal(egreso.montoTotal());
        egresoDTO.setFecha(egreso.getFecha().toString());
        egresoDTO.setCantPresupuestosFaltantes(egreso.cantPresupuestosFaltantes());
        egresoDTO.setPresupuestos(egreso.getPresupuestos());
        if (null != egreso.getIngreso()) {
            egresoDTO.setIngreso(egreso.getIngreso().getId());
        }else{
            egresoDTO.setIngreso(-1);
        }
        return egresoDTO;
    }
}
