package domain.Validadores;

import domain.Operacion.Egreso.OperacionEgreso;
import domain.Usuario.BandejaMensaje.Mensaje;

public class NotificadorResultadoValidacion {


    private static NotificadorResultadoValidacion instancia=null;


    public static NotificadorResultadoValidacion instancia(){
        if (instancia == null) {
            instancia = new NotificadorResultadoValidacion();
        }
        return instancia;
    }

    public void enviaResultados(ValidadorDeTransparencia validadorDeTransparencia,OperacionEgreso unEgreso) throws Exception {
        unEgreso.notificaRevisores(this.generaMensaje(validadorDeTransparencia,unEgreso));
    }

    private Mensaje generaMensaje(ValidadorDeTransparencia validador, OperacionEgreso unEgreso) throws Exception {
        String detalle = "En detalle: " + this.detalleResultado(validador,unEgreso);
       return new Mensaje(unEgreso, this.cuerpoMensaje(validador,unEgreso),detalle);
    }
    private String detalleResultado(ValidadorDeTransparencia validador,OperacionEgreso unEgreso) {
        return validador.getCriteriosValidadores().stream().
                map(criterioValidacion -> criterioValidacion.resultado(unEgreso))
                .reduce((s, s2) -> s + '\n' + s2).get();

    }

    private String cuerpoMensaje(ValidadorDeTransparencia validador,OperacionEgreso unEgreso) throws Exception {
        return validador.validaEgreso(unEgreso) ? "Valida" : "invalida";
    }


}
