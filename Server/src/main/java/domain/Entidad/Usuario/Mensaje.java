package domain.Entidad.Usuario;

import domain.Entidad.Entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "mensaje")
public class Mensaje extends Entidad {

    @Column(name = "fecha_envio",columnDefinition = "DATE")
    private LocalTime fechaEnvio;

    @Column(name = "fecha_leido",columnDefinition = "DATE")
    private LocalTime fechaLeido;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "mensaje")
    private String mensaje;


    public Mensaje(String asunto, String mensaje) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalTime.now();
        this.fechaLeido = null;
    }

    public Mensaje() {}

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
