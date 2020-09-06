package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;
import domain.Usuario.Mensaje;

public class NotificadorResultadoValidacion {


    private static NotificadorResultadoValidacion instancia=null;


    public static NotificadorResultadoValidacion instancia(){
        if (instancia == null) {
            instancia = new NotificadorResultadoValidacion();
        }
        return instancia;
    }

    public void enviaResultados(ValidadorDeTransparencia validadorDeTransparencia,OperacionEgreso unEgreso){
        unEgreso.notificaRevisores(this.generaMensaje(validadorDeTransparencia,unEgreso));
    }

    private Mensaje generaMensaje(ValidadorDeTransparencia validador, OperacionEgreso unEgreso) {
       return new Mensaje(this.asuntoMensaje(unEgreso), this.cuerpoMensaje(validador,unEgreso));
    }

    private String asuntoMensaje(OperacionEgreso unEgreso) {
        return "domain.Operacion Egreso #" + Long.toString(unEgreso.getNroOperacion());
    }

    private String detalleResultado(ValidadorDeTransparencia validador,OperacionEgreso unEgreso) {
        return validador.getCriteriosValidadores().stream().
                map(criterioValidacion -> criterioValidacion.resultado(unEgreso))
                .reduce((s, s2) -> s + '\n' + s2).get();

    }

    private String cuerpoMensaje(ValidadorDeTransparencia validador,OperacionEgreso unEgreso) {
        return validador.validaEgreso(unEgreso) ? "domain.Operacion Valida" : "domain.Operacion invalida"+//Titulo
                "\n En detalle: \n"+
                this.detalleResultado(validador,unEgreso);//Todos los detalles
    }


}
