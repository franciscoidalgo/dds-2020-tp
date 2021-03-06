package domain.Operacion.Ingreso;


import domain.Entidad.Entidad;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Operacion;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operacion_ingreso")
@PrimaryKeyJoinColumn(name = "operacion_id", referencedColumnName = "id")
public class OperacionIngreso extends Operacion {
    //Atributos
    @Column(name = "descripcion")
    private String descripcion;

    @Column(columnDefinition = "DATE")
    private LocalDate fechaAceptabilidad;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "tipo_ingreso")
    private TipoIngreso tipoIngreso;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "ingreso")
    private List<OperacionEgreso> egresosVinculados;


    //Constructor
    public OperacionIngreso(LocalDate fecha, double montoTotal, Entidad entidad, String descripcion, LocalDate fechaAceptabilidad) {
        super(fecha, montoTotal, entidad);
        this.descripcion = descripcion;
        this.fechaAceptabilidad = fechaAceptabilidad;
    }

    public OperacionIngreso() {

        this.egresosVinculados = new ArrayList<>();
    }

    //Getter Setter
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaAceptabilidad() {
        return fechaAceptabilidad;
    }

    public void setFechaAceptabilidad(LocalDate fechaAceptabilidad) {
        this.fechaAceptabilidad = fechaAceptabilidad;
    }

    public TipoIngreso getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(TipoIngreso tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public List<OperacionEgreso> getEgresosVinculados() {
        return egresosVinculados;
    }

    public void setEgresosVinculados(List<OperacionEgreso> egresosVinculados) {
        this.egresosVinculados = egresosVinculados;
    }
//Funcionalidad

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    @Override
    public double montoTotal() {
        return montoTotal;
    }

    public void agregarEgreso(OperacionEgreso operacionEgreso) throws Exception {

        if (!puedeAgregarEgreso(operacionEgreso)) {
            throw new Exception("No se puede cargar un egreso. Supera al saldo");
        }
        if (!this.egresosVinculados.contains(operacionEgreso)) {
            this.egresosVinculados.add(operacionEgreso);
            operacionEgreso.setIngreso(this);
        }

    }

    private Boolean puedeAgregarEgreso(OperacionEgreso operacionEgreso) {
        return this.saldo() - operacionEgreso.montoTotal() >= 0 && !operacionEgreso.estaAsociado();
    }

    public double costo() {
        return this.egresosVinculados.stream().mapToDouble(OperacionEgreso::montoTotal).sum();
    }

    public Double saldo() {
        return this.montoTotal - this.costo();
    }

}
