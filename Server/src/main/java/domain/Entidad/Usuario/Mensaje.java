package domain.Entidad.Usuario;

import domain.Entidad.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "mensaje")
public class Mensaje extends EntidadPersistente {

    @Column(name = "fecha_envio",columnDefinition = "DATE")
    private LocalDate fechaEnvio;

    @Column(columnDefinition = "TIME")
    private LocalTime horaEnvio;

    @Column(name = "fecha_leido",columnDefinition = "DATE")
    private LocalDate fechaLeido;

    @Column(columnDefinition = "TIME")
    private LocalTime horaLeido;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "mensaje")
    private String mensaje;

    public Mensaje(String asunto, String mensaje) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalDate.now();
        this.horaEnvio = LocalTime.now();
        this.fechaLeido = null;
        this.horaLeido = null;
    }

    public Mensaje() {}

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }
    public LocalTime getHoraEnvio(){
        return this.horaEnvio;
    }
    public LocalDate getFechaLeido() {
        return fechaLeido;
    }
    public LocalTime getHoraLeido(){
        return this.horaLeido;
    }

    public String getAsunto() {
        return asunto;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void actualizateLeido(){
        this.fechaLeido = LocalDate.now();
    }
}
