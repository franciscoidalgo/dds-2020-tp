package domain.Usuario.BandejaMensaje;

import domain.Entidad.EntidadPersistente;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Egreso.Proveedor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
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

    @Column(name = "idEgreso")
    private int idEgreso;

    public Mensaje(String asunto, String mensaje) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalDate.now();
        this.horaEnvio = LocalTime.now();
        this.fechaLeido = LocalDate.now();
        this.horaLeido = LocalTime.now();
    }
    public Mensaje( OperacionEgreso egreso,String resultadoValidacion,String detalleResultado) {
        this.fechaEnvio = LocalDate.now();
        this.horaEnvio = LocalTime.now();
        this.fechaLeido = LocalDate.now();
        this.horaLeido = LocalTime.now();
        this.idEgreso = egreso.getId();
        setSegunEgreso(egreso, resultadoValidacion,detalleResultado);

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

    public int getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(int idEgreso) {
        this.idEgreso = idEgreso;
    }

    public void actualizateLeido(){
        this.fechaLeido = LocalDate.now();
        this.horaLeido = LocalTime.now();
    }

    public void setSegunEgreso(OperacionEgreso egreso,String resultadoValidacion,String detalleResultado){
        DetalleOperacion detalle = egreso.getDetalle();
        Proveedor proveedor = detalle.getProveedor();

        this.asunto = " Egreso#"+ egreso.getId() +
                      "--" + proveedor.getRazonSocial() +
                      "--$" + BigDecimal.valueOf(egreso.montoTotal()).setScale(2) +
                      "--" + fechaEnvio.toString()+
                      "--" + resultadoValidacion ;
        this.mensaje = detalleResultado;
    }


}
