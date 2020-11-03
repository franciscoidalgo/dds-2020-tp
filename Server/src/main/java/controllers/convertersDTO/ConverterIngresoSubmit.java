package controllers.convertersDTO;

import controllers.DTO.IngresoSubmitDTO;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Ingreso.TipoIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;

import java.time.LocalDate;
import java.util.ArrayList;

public class ConverterIngresoSubmit{

    public static IngresoSubmitDTO toDTO(OperacionIngreso operacionIngreso) {
        return null;
    }

    public static OperacionIngreso toModel(IngresoSubmitDTO ingresoSubmitDTO) throws Exception {
        Repositorio<TipoIngreso> tipoIngresoRepositorio = FactoryRepo.get(TipoIngreso.class);
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        OperacionIngreso operacionIngreso = new OperacionIngreso();

        LocalDate fecha = LocalDate.parse(ingresoSubmitDTO.getFechaRealizada());
        LocalDate fechaAceptabilidad = LocalDate.parse(ingresoSubmitDTO.getFechaAceptacion());

            TipoIngreso tipoIngreso = tipoIngresoRepositorio.buscar(ingresoSubmitDTO.getIdTipoIngreso());
            operacionIngreso.setTipoIngreso(tipoIngreso);
            operacionIngreso.setFecha(fecha);
            operacionIngreso.setFechaAceptabilidad(fechaAceptabilidad);
            operacionIngreso.setMontoTotal(ingresoSubmitDTO.getMonto());

        if(ingresoSubmitDTO.getListaEgresos().length == 0){
            return operacionIngreso;
        }

        for (int i = 0; i<ingresoSubmitDTO.getListaEgresos().length;i++) {
            OperacionEgreso operacionEgreso = operacionEgresoRepositorio.buscar(ingresoSubmitDTO.getListaEgresos()[i]);
            if(null == operacionEgreso){
                throw new Exception("No se pudo cargar el egreso");
            }
            operacionIngreso.agregarEgreso(operacionEgreso);
        }


        return operacionIngreso;
    }

}
