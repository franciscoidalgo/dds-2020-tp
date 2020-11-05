package controllers.convertersDTO;

import controllers.DTO.IngresoDTO;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Ingreso.TipoIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;

import java.time.LocalDate;

public class ConverterIngresoSubmit {

    public static IngresoDTO toDTO(OperacionIngreso operacionIngreso) {
        IngresoDTO ingresoDTO = new IngresoDTO();

        ingresoDTO.setId(operacionIngreso.getId());
        ingresoDTO.setDescripcion(operacionIngreso.getDescripcion());
        ingresoDTO.setFechaAceptacion(operacionIngreso.getFechaAceptabilidad().toString());
        ingresoDTO.setFechaRealizada(operacionIngreso.getFecha().toString());
        ingresoDTO.setMonto(operacionIngreso.montoTotal());

        return ingresoDTO;
    }

    public static OperacionIngreso fromModel(IngresoDTO ingresoDTO) throws Exception {
        Repositorio<TipoIngreso> tipoIngresoRepositorio = FactoryRepo.get(TipoIngreso.class);
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        OperacionIngreso operacionIngreso = new OperacionIngreso();

        LocalDate fecha = LocalDate.parse(ingresoDTO.getFechaRealizada());
        LocalDate fechaAceptabilidad = LocalDate.parse(ingresoDTO.getFechaAceptacion());

        TipoIngreso tipoIngreso = tipoIngresoRepositorio.buscar(ingresoDTO.getIdTipoIngreso());
        operacionIngreso.setTipoIngreso(tipoIngreso);
        operacionIngreso.setFecha(fecha);
        operacionIngreso.setFechaAceptabilidad(fechaAceptabilidad);
        operacionIngreso.setMontoTotal(ingresoDTO.getMonto());
        operacionIngreso.setDescripcion(ingresoDTO.getDescripcion());

        if (ingresoDTO.getListaEgresos().length == 0) {
            return operacionIngreso;
        }

        for (int i = 0; i < ingresoDTO.getListaEgresos().length; i++) {
            OperacionEgreso operacionEgreso = operacionEgresoRepositorio.buscar(ingresoDTO.getListaEgresos()[i]);
            if (null == operacionEgreso) {
                throw new Exception("No se pudo cargar el egreso");
            }
            operacionIngreso.agregarEgreso(operacionEgreso);
            operacionEgreso.setIngreso(operacionIngreso);
        }


        return operacionIngreso;
    }

}
