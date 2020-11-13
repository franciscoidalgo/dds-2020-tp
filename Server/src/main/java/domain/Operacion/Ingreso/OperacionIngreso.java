package domain.Operacion.Ingreso;


import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Operacion;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operacion_ingreso")
@PrimaryKeyJoinColumn(name="operacion_id",referencedColumnName = "id")
public class OperacionIngreso extends Operacion {

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OperacionEgreso> egresos;

    //Constructor
    public OperacionIngreso(double montoTotal, String descripcion){
        super(montoTotal);
        this.descripcion = descripcion;
        this.egresos = new ArrayList<>();
    }

    public OperacionIngreso() {
        this.egresos = new ArrayList<>();
    }

    //Getter Setter
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<OperacionEgreso> getEgresos() { return egresos; }
    public void setEgresos(List<OperacionEgreso> egresos) { this.egresos = egresos; }

    //Funcionalidad
    public void agregateEgreso(OperacionEgreso operacionEgreso){
        if(!this.egresos.contains(operacionEgreso)){
            this.egresos.add(operacionEgreso);
            operacionEgreso.marcateComoAsociado();
        }
    }

    public void agregarListaDeEgresos(List<OperacionEgreso> egresos){
        for(OperacionEgreso operacionEgreso : egresos){
            agregateEgreso(operacionEgreso);
        }
    }

    public double saldoOperacion(){
        return this.montoTotal - this.montoTotalEgresos();
    }

    public void setMontoTotal(double montoTotal) {this.montoTotal = montoTotal;}

    @Override
    public double montoTotal() {
        return montoTotal;
    }

    private double montoTotalEgresos(){
        return this.egresos.stream().mapToDouble(OperacionEgreso::montoTotal).sum();
    }
}
