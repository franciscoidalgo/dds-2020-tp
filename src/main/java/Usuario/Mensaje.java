package Usuario;

public class Mensaje {

    private long fechaEnvio;
    private long fechaLeido;
    private String asunto;
    private String mensaje;


    public Mensaje(String asunto, String mensaje) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = System.currentTimeMillis();
        this.fechaLeido = System.currentTimeMillis();
    }

    public long getFechaEnvio() {
        return fechaEnvio;
    }

    public long getFechaLeido() {
        return fechaLeido;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void actualizateLeido(){
        this.fechaLeido = System.currentTimeMillis();
    }
}
