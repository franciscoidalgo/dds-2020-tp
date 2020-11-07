package controllers.convertersDTO;

import controllers.DTO.IngresoDTO;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Ingreso.TipoIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterIngreso {

    public static IngresoDTO toDTO(OperacionIngreso operacionIngreso) {
        IngresoDTO ingresoDTO = new IngresoDTO();

        List<Integer> idOperacionesEgresos = operacionIngreso.getEgresosVinculados().stream().map(operacionEgreso -> operacionEgreso.getId()).collect(Collectors.toList());

        ingresoDTO.setId(operacionIngreso.getId());
        ingresoDTO.setDescripcion(operacionIngreso.getDescripcion());
        ingresoDTO.setFechaAceptacion(operacionIngreso.getFechaAceptabilidad().toString());
        ingresoDTO.setFechaRealizada(operacionIngreso.getFecha().toString());
        ingresoDTO.setMonto(operacionIngreso.montoTotal());
        ingresoDTO.setTipoIngreso(operacionIngreso.getTipoIngreso());
        ingresoDTO.setListaEgresos(idOperacionesEgresos);
        ingresoDTO.setSaldo(operacionIngreso.saldo());
        ingresoDTO.setCosto(operacionIngreso.costo());

        return ingresoDTO;
    }

    public static OperacionIngreso fromModel(IngresoDTO ingresoDTO) throws Exception {
        Repositorio<TipoIngreso> tipoIngresoRepositorio = FactoryRepo.get(TipoIngreso.class);
        Repositorio<OperacionEgreso> operacionEgresoRepositorio = FactoryRepo.get(OperacionEgreso.class);
        OperacionIngreso operacionIngreso = new OperacionIngreso();

        LocalDate fecha = LocalDate.parse(ingresoDTO.getFechaRealizada());
        LocalDate fechaAceptabilidad = LocalDate.parse(ingresoDTO.getFechaAceptacion());

        TipoIngreso tipoIngreso = tipoIngresoRepositorio.buscar(ingresoDTO.getTipoIngreso().getId());

        operacionIngreso.setTipoIngreso(tipoIngreso);
        operacionIngreso.setFecha(fecha);
        operacionIngreso.setFechaAceptabilidad(fechaAceptabilidad);
        operacionIngreso.setMontoTotal(ingresoDTO.getMonto());
        operacionIngreso.setDescripcion(ingresoDTO.getDescripcion());

        if (ingresoDTO.getListaEgresos().size() == 0) {
            return operacionIngreso;
        }

        for (int i = 0; i < ingresoDTO.getListaEgresos().size(); i++) {
            OperacionEgreso operacionEgreso = operacionEgresoRepositorio.buscar(ingresoDTO.getListaEgresos().get(i));
            if (null == operacionEgreso) {
                throw new Exception("No se pudo cargar el egreso");
            }
            operacionIngreso.agregarEgreso(operacionEgreso);
            operacionEgreso.setIngreso(operacionIngreso);
        }


        return operacionIngreso;
    }

}
