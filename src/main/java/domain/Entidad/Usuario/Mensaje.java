package domain.Entidad.Usuario;

import java.time.LocalTime;

public class Mensaje {

    private final LocalTime fechaEnvio;
    private LocalTime fechaLeido;
    private final String asunto;
    private final String mensaje;


    public Mensaje(String asunto, String mensaje) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalTime.now();
        this.fechaLeido = null;
    }

    public LocalTime getFechaEnvio() {
        return fechaEnvio;
    }
    public LocalTime getFechaLeido() {
        return fechaLeido;
    }

    public String getAsunto() {
        return asunto;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void actualizateLeido(){
        this.fechaLeido = LocalTime.now();
    }
}
