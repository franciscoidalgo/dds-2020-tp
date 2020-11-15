/*package Tests;


import DTOs.DTOOperacionEgreso;
import DTOs.DTOOperacionIngreso;
import Vinculador.VinculadorAutomatico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        //INGRESOS
        DTOOperacionIngreso i1 = new
        DTOOperacionIngreso(1, 1278923.65, LocalDate.now().minusDays(13), LocalDate.now().minusDays(5));

        DTOOperacionIngreso i2 = new
        DTOOperacionIngreso(2, 1923798123.98, LocalDate.now().minusDays(12), LocalDate.now().minusDays(8));

        DTOOperacionIngreso i3 = new
        DTOOperacionIngreso(3, 193247198.65, LocalDate.now().minusDays(19), LocalDate.now().minusDays(1));

        DTOOperacionIngreso i4 = new
        DTOOperacionIngreso(4, 100.67, LocalDate.now().minusDays(65), LocalDate.now().minusDays(43));

        DTOOperacionIngreso i5 = new
        DTOOperacionIngreso(5, 124671891.41, LocalDate.now().minusDays(87), LocalDate.now().minusDays(70));

        DTOOperacionIngreso i6 = new
        DTOOperacionIngreso(6, 12378132.4, LocalDate.now().minusDays(1), LocalDate.now());

        DTOOperacionIngreso i7 = new
        DTOOperacionIngreso(7, 13489.09, LocalDate.now().minusDays(4), LocalDate.now().minusDays(1));

        DTOOperacionIngreso i8 = new
        DTOOperacionIngreso(8, 124.7, LocalDate.now().minusDays(0), LocalDate.now().plusDays(7));

        DTOOperacionIngreso i9 = new
        DTOOperacionIngreso(9, 4355.87, LocalDate.now().minusDays(17), LocalDate.now().plusDays(14));

        DTOOperacionIngreso i10 = new
        DTOOperacionIngreso(10, 456673849.75, LocalDate.now().minusDays(10), LocalDate.now().plusDays(88));

        DTOOperacionIngreso i11 = new
        DTOOperacionIngreso(11, 68568.65, LocalDate.now().minusDays(25), LocalDate.now().plusDays(41));

        DTOOperacionIngreso i12 = new
        DTOOperacionIngreso(12, 125.87, LocalDate.now().minusDays(22), LocalDate.now().plusDays(25));

        DTOOperacionIngreso i13 = new
        DTOOperacionIngreso(13, 999.55, LocalDate.now().minusDays(73), LocalDate.now().plusDays(10));

        DTOOperacionIngreso i14 = new
        DTOOperacionIngreso(14, 12431455.89, LocalDate.now().minusDays(55), LocalDate.now().plusDays(36));

        DTOOperacionIngreso i15 = new
        DTOOperacionIngreso(15, 78.76, LocalDate.now().minusDays(8), LocalDate.now().plusDays(44));

        DTOOperacionIngreso i16 = new
        DTOOperacionIngreso(16, 12455.98, LocalDate.now().minusDays(19), LocalDate.now().plusDays(47));

        DTOOperacionIngreso i17 = new
        DTOOperacionIngreso(17, 899.44, LocalDate.now().minusDays(5), LocalDate.now().plusDays(51));


        //EGRESOS
        DTOOperacionEgreso e1 = new
        DTOOperacionEgreso(1, 127489, LocalDate.now().minusDays(2), false, null);

        DTOOperacionEgreso e2 = new
        DTOOperacionEgreso(2, 8759, LocalDate.now().minusDays(4), false, null);

        DTOOperacionEgreso e3 = new
        DTOOperacionEgreso(3, 38690, LocalDate.now().plusDays(7), false, null);

        DTOOperacionEgreso e4 = new
        DTOOperacionEgreso(4, 398456, LocalDate.now().plusDays(10), false, null);

        DTOOperacionEgreso e5 = new
        DTOOperacionEgreso(5, 9805, LocalDate.now().plusDays(15), false, null);

        DTOOperacionEgreso e6 = new
        DTOOperacionEgreso(6, 374.98, LocalDate.now().plusDays(23), false, null);

        DTOOperacionEgreso e7 = new
        DTOOperacionEgreso(7, 293584.65, LocalDate.now().plusDays(9), false, null);

        DTOOperacionEgreso e8 = new
        DTOOperacionEgreso(8, 1684.69, LocalDate.now().plusDays(4), true, i9);

        DTOOperacionEgreso e9 = new
        DTOOperacionEgreso(9, 127894.1, LocalDate.now().plusDays(89), false, null);

        DTOOperacionEgreso e10 = new
        DTOOperacionEgreso(10, 3985.98, LocalDate.now().minusDays(12), false, null);

        DTOOperacionEgreso e11 = new
        DTOOperacionEgreso(11, 12738.45, LocalDate.now().minusDays(36), false, null);

        DTOOperacionEgreso e12 = new
        DTOOperacionEgreso(12, 127894, LocalDate.now().minusDays(7), true, i1);

        DTOOperacionEgreso e13 = new
        DTOOperacionEgreso(13, 1264789.76, LocalDate.now().minusDays(19), false, null);

        DTOOperacionEgreso e14 = new
        DTOOperacionEgreso(14, 172389132.97, LocalDate.now().minusDays(3), false, null);

        DTOOperacionEgreso e15 = new
        DTOOperacionEgreso(15, 172839.21, LocalDate.now().minusDays(3), false, null);

        DTOOperacionEgreso e16 = new
        DTOOperacionEgreso(16, 12387.34, LocalDate.now().minusDays(76), true, i5);

        DTOOperacionEgreso e17 = new
        DTOOperacionEgreso(17, 2873, LocalDate.now().minusDays(31), false, null);

        DTOOperacionEgreso e18 = new
        DTOOperacionEgreso(18, 123.7, LocalDate.now().minusDays(6), false, null);

        DTOOperacionEgreso e19 = new
        DTOOperacionEgreso(19, 1345.76, LocalDate.now().minusDays(2), false, null);

        DTOOperacionEgreso e20 = new
        DTOOperacionEgreso(20, 1233.45, LocalDate.now().minusDays(8), true, null);

        DTOOperacionEgreso e21 = new
        DTOOperacionEgreso(21, 24.65, LocalDate.now().minusDays(11), false, i17);



        //LISTAS DE PRUEBA

        //EGRESOS
        List<DTOOperacionEgreso> egresos1 = new ArrayList<>();
        egresos1.add(e2);
        egresos1.add(e3);
        egresos1.add(e18);

        List<DTOOperacionEgreso> egresos2 = new ArrayList<>();
        egresos2.add(e19);
        egresos2.add(e13);
        egresos2.add(e11);

        List<DTOOperacionEgreso> egresos3 = new ArrayList<>();
        egresos3.add(e5);
        egresos3.add(e7);
        egresos3.add(e17);

        List<DTOOperacionEgreso> egresos4 = new ArrayList<>();
        egresos4.add(e9);
        egresos4.add(e19);
        egresos4.add(e21);

        List<DTOOperacionEgreso> egresos5 = new ArrayList<>();
        egresos5.add(e1);
        egresos5.add(e9);
        egresos5.add(e8);

        List<DTOOperacionEgreso> egresos6 = new ArrayList<>();
        egresos6.add(e6);
        egresos6.add(e10);
        egresos6.add(e12);

        List<DTOOperacionEgreso> egresos7 = new ArrayList<>();
        egresos7.add(e20);
        egresos7.add(e14);
        egresos7.add(e4);

        List<DTOOperacionEgreso> egresos8 = new ArrayList<>();
        egresos8.add(e6);
        egresos8.add(e9);
        egresos8.add(e16);

        List<DTOOperacionEgreso> egresos9 = new ArrayList<>();
        egresos9.add(e7);
        egresos9.add(e5);
        egresos9.add(e19);

        List<DTOOperacionEgreso> egresos10 = new ArrayList<>();
        egresos10.add(e2);
        egresos10.add(e15);
        egresos10.add(e10);



        //INGRESOS
        List<DTOOperacionIngreso> ingresos1 = new ArrayList<>();
        ingresos1.add(i1);
        ingresos1.add(i11);
        ingresos1.add(i12);

        List<DTOOperacionIngreso> ingresos2 = new ArrayList<>();
        ingresos1.add(i2);
        ingresos1.add(i13);
        ingresos1.add(i5);

        List<DTOOperacionIngreso> ingresos3 = new ArrayList<>();
        ingresos3.add(i3);
        ingresos3.add(i14);
        ingresos3.add(i6);

        List<DTOOperacionIngreso> ingresos4 = new ArrayList<>();
        ingresos4.add(i4);
        ingresos4.add(i15);
        ingresos4.add(i7);

        List<DTOOperacionIngreso> ingresos5 = new ArrayList<>();
        ingresos5.add(i5);
        ingresos5.add(i16);
        ingresos5.add(i8);

        List<DTOOperacionIngreso> ingresos6 = new ArrayList<>();
        ingresos6.add(i6);
        ingresos6.add(i17);
        ingresos6.add(i9);

        List<DTOOperacionIngreso> ingresos7 = new ArrayList<>();
        ingresos7.add(i7);
        ingresos7.add(i1);
        ingresos7.add(i10);

        List<DTOOperacionIngreso> ingresos8 = new ArrayList<>();
        ingresos8.add(i8);
        ingresos8.add(i2);
        ingresos8.add(i11);

        List<DTOOperacionIngreso> ingresos9 = new ArrayList<>();
        ingresos9.add(i9);
        ingresos9.add(i3);
        ingresos9.add(i12);

        List<DTOOperacionIngreso> ingresos10 = new ArrayList<>();
        ingresos10.add(i10);
        ingresos10.add(i4);
        ingresos10.add(i13);

        //PRUEBA DE ORDENAMIENTOS
        /*
        DTOOperacionEgreso.ordenarPorMonto(egresos);
        DTOOperacionEgreso.ordenarPorFecha(egresos);

        for(DTOOperacionEgreso egreso: egresos){
            System.out.println(egreso.getId());
        }

         */
/*

        //PRUEBAS DE VINCULACIONES
        System.out.println("VINCULACIONES");

        System.out.println("---VINCULACION 1---");
        VinculadorAutomatico.vincular("ORDEN_VALOR_PRIMERO_EGRESO", egresos1, ingresos2);
        for(DTOOperacionEgreso egreso: egresos1){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 2---");
        VinculadorAutomatico.vincular("ORDEN_VALOR_PRIMERO_INGRESO", egresos3, ingresos10);
        for(DTOOperacionEgreso egreso: egresos3){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 3---");
        VinculadorAutomatico.vincular("FECHA", egresos3, ingresos1);
        for(DTOOperacionEgreso egreso: egresos3){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 4---");
        List<String> listaMix1 = new ArrayList<>();
        listaMix1.add("ORDEN_VALOR_PRIMERO_EGRESO");
        listaMix1.add("ORDEN_VALOR_PRIMERO_INGRESO");
        VinculadorAutomatico.vincular("MIX", egresos10, ingresos3, listaMix1);
        for(DTOOperacionEgreso egreso: egresos10){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 5---");
        VinculadorAutomatico.vincular("ORDEN_VALOR_PRIMERO_EGRESO", egresos4, ingresos2);
        for(DTOOperacionEgreso egreso: egresos4){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 6---");
        VinculadorAutomatico.vincular("ORDEN_VALOR_PRIMERO_INGRESO", egresos5, ingresos2);
        for(DTOOperacionEgreso egreso: egresos5){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 7---");
        VinculadorAutomatico.vincular("FECHA", egresos6, ingresos6);
        for(DTOOperacionEgreso egreso: egresos6){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 8---");
        List<String> listaMix2 = new ArrayList<>();
        listaMix2.add("FECHA");
        listaMix2.add("ORDEN_VALOR_PRIMERO_EGRESO");
        VinculadorAutomatico.vincular("MIX", egresos7, ingresos7, listaMix2);
        for(DTOOperacionEgreso egreso: egresos7){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 9---");
        VinculadorAutomatico.vincular("ORDEN_VALOR_PRIMERO_EGRESO", egresos8, ingresos8);
        for(DTOOperacionEgreso egreso: egresos8){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }

        System.out.println("---VINCULACION 10---");
        VinculadorAutomatico.vincular("ORDEN_VALOR_PRIMERO_INGRESO", egresos9, ingresos9);
        for(DTOOperacionEgreso egreso: egresos9){
            System.out.println("ID: " + egreso.getId() + " MONTO: " + egreso.getMontoTotal() + " FECHA: " + egreso.getFecha() + " ASOCIADO: " + egreso.getEstaAsociado() + " ASOCIACION: " + (egreso.getIngreso() == null ? "NINGUNA" : egreso.getIngreso().getId()));
        }
    }
}*/