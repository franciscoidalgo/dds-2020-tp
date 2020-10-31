package domain.Operacion.Ingreso;


import domain.Entidad.EntidadJuridica;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Operacion;
import domain.Entidad.Usuario.Usuario;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operacion_ingreso")
@PrimaryKeyJoinColumn(name="operacion_id",referencedColumnName = "id")
public class OperacionIngreso extends Operacion {

    @Column(name = "descripcion")
    private String descripcion;

    @Column(columnDefinition = "DATE")
    private LocalDate fechaAceptabilidad;


    //Constructor
    public OperacionIngreso(LocalDate fecha, double montoTotal, EntidadJuridica entidadJuridica, String descripcion, LocalDate fechaAceptabilidad) {
        super(fecha, montoTotal, entidadJuridica);
        this.descripcion = descripcion;
        this.fechaAceptabilidad = fechaAceptabilidad;
    }

    public OperacionIngreso() {}

    //Getter Setter
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    //Funcionalidad

    public void setMontoTotal(double montoTotal) {this.montoTotal = montoTotal;}

    @Override
    public double montoTotal() {
        return montoTotal;
    }

}
