package controllers.convertersDTO;

import controllers.DTO.DTOOperacionIngreso;
import controllers.DTO.EgresoDTO;
import controllers.DTO.IngresoDTO;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Ingreso.TipoIngreso;
import repositorios.Repositorio;
import repositorios.factories.FactoryRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterIngreso {

    public static IngresoDTO toDTO(OperacionIngreso operacionIngreso) {
        IngresoDTO ingresoDTO = new IngresoDTO();

        List<EgresoDTO> operacionesEgresos = operacionIngreso.getEgresosVinculados().stream().map(operacionEgreso -> ConverterEgreso.generarEgresoDTO(operacionEgreso)).collect(Collectors.toList());

        ingresoDTO.setId(operacionIngreso.getId());
        ingresoDTO.setDescripcion(operacionIngreso.getDescripcion());
        ingresoDTO.setFechaAceptacion(operacionIngreso.getFechaAceptabilidad().toString());
        ingresoDTO.setFechaRealizada(operacionIngreso.getFecha().toString());
        ingresoDTO.setMonto(operacionIngreso.montoTotal());
        ingresoDTO.setTipoIngreso(operacionIngreso.getTipoIngreso());
        ingresoDTO.setListaEgresos(operacionesEgresos);
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
        System.out.println("***************************************");
        for (int i = 0; i < ingresoDTO.getListaEgresos().size(); i++) {
            System.out.println("***************************************");
            Integer idEgreso = ingresoDTO.getListaEgresos().get(i).getId();
            System.out.println("-------------------------------->idEGRESO : " + idEgreso);
            OperacionEgreso operacionEgreso = operacionEgresoRepositorio.buscar(idEgreso);
            if (null == operacionEgreso) {
                throw new Exception("No se pudo cargar el egreso");
            }
            operacionIngreso.agregarEgreso(operacionEgreso);
            operacionEgreso.setIngreso(operacionIngreso);
        }


        return operacionIngreso;
    }

    public static DTOOperacionIngreso generarIngresoVinculadorDTO(OperacionIngreso operacionIngreso) {
        DTOOperacionIngreso dtoOperacionIngreso = new DTOOperacionIngreso();

        dtoOperacionIngreso.setFecha(operacionIngreso.getFecha());
        dtoOperacionIngreso.setFechaAceptabilidad(operacionIngreso.getFechaAceptabilidad());

        dtoOperacionIngreso.setId(operacionIngreso.getId());
        dtoOperacionIngreso.setMontoTotal(operacionIngreso.getMontoTotal());
        dtoOperacionIngreso.setEgresos(new ArrayList<>());

        return dtoOperacionIngreso;

    }

    public static OperacionIngreso generarIngresoVinculadorModel(DTOOperacionIngreso dtoOperacionIngreso) {
        OperacionIngreso operacionIngreso = FactoryRepo.get(OperacionIngreso.class).buscar(dtoOperacionIngreso.getId());

        dtoOperacionIngreso.getEgresos().forEach(dtoOperacionEgreso -> {
                    OperacionEgreso egresoModel = FactoryRepo.get(OperacionEgreso.class).buscar(dtoOperacionEgreso.getId());
                    try {
                        operacionIngreso.agregarEgreso(egresoModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        return operacionIngreso;

    }


}
