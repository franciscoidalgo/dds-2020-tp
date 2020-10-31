
package domain.Operacion.Egreso;


import domain.Entidad.EntidadJuridica;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Operacion;
import domain.Entidad.Usuario.Mensaje;
import domain.Entidad.Usuario.Usuario;
import domain.Validadores.ValidadorDeTransparencia;
import repositorios.RepositorioDeUsuarios;
import repositorios.factories.FactoryRepoUsuario;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operacion_egreso")
@PrimaryKeyJoinColumn(name="operacion_id",referencedColumnName = "id")
public class OperacionEgreso extends Operacion {
    //Atributos
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "detalle_id")
    private DetalleOperacion detalle;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "medio_de_pago_id")
    private MedioDePago medioDePago;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_revisa",
            joinColumns = @JoinColumn(name = "revisores_id"),
            inverseJoinColumns = @JoinColumn(name = "operacion_egreso_id")
    )
    private List<Usuario> revisores;

    @Column(name = "esta_asociado", columnDefinition = "CHAR")
    private Boolean estaAsociado = false;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Presupuesto> presupuestos;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private OperacionIngreso ingreso;

    public OperacionEgreso(LocalDate fecha, double montoTotal, EntidadJuridica entidadJuridica, DetalleOperacion detalle, MedioDePago medioDePago, OperacionIngreso ingreso, Integer cantPresupuestos) {
        super(fecha, montoTotal, entidadJuridica);
        this.detalle = detalle;
        this.medioDePago = medioDePago;
        this.revisores = new ArrayList<>();
        this.estaAsociado = false;
        this.presupuestos = new ArrayList<>();
        this.ingreso = ingreso;
        this.cantPresupuestos = cantPresupuestos;
    }

    @Column(name = "cant_presupuestos")
    private Integer cantPresupuestos;




    //Constructor
    public OperacionEgreso(DetalleOperacion detalle, MedioDePago medioDePago, OperacionIngreso ingreso, Integer cantPresupuestos) {
        this.detalle = detalle;
        this.medioDePago = medioDePago;
        this.revisores = new ArrayList<>();
        this.estaAsociado = false;
        this.presupuestos = new ArrayList<>();
        this.ingreso = ingreso;
        this.cantPresupuestos = cantPresupuestos;
    }

    public OperacionEgreso() {
        super();
        this.revisores = new ArrayList<>();
        this.presupuestos = new ArrayList<>();
    }
    //Getter Setter

    public Integer getCantPresupuestos() {
        return cantPresupuestos;
    }

    public void setCantPresupuestos(Integer cantPresupuestos) {
        this.cantPresupuestos = cantPresupuestos;
    }
    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public DetalleOperacion getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleOperacion detalle) {
        this.detalle = detalle;
    }

    public List<Usuario> getRevisores() {
        return revisores;
    }

    public void setRevisores(List<Usuario> revisores) {
        this.revisores = revisores;
    }

    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(ArrayList<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public void setMontoTotal(double monto){
        this.montoTotal = monto;
    }

    public Boolean getEstaAsociado() {
        return estaAsociado;
    }

    public void setEstaAsociado(Boolean estaAsociado) {
        this.estaAsociado = estaAsociado;
    }

    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public OperacionIngreso getIngreso() {
        return ingreso;
    }

    public void setIngreso(OperacionIngreso ingreso) {
        this.ingreso = ingreso;
    }

    /*Funcionales*/
    @Override
    public double montoTotal() {

        return this.detalle.calcularMontoTotal();
    }

    public void agregaPresupuesto(Presupuesto unPresupuesto) throws Exception {
        if (this.puedeAgregarPresupuesto()) {
            this.presupuestos.add(unPresupuesto);
        } else {
            throw new Exception("No es posible cargar presupuesto");
        }

    }

    public void sacaPresupuesto(Presupuesto unPresupuesto) {
        presupuestos.remove(unPresupuesto);
    }

    public void agregateRevisor(Usuario unRevisor) {
        this.revisores.add(unRevisor);
    }

    public void sacaRevisor(Usuario unRevisor) {
        this.revisores.remove(unRevisor);
    }


    public void notificaRevisores(Mensaje unMensaje) {
        RepositorioDeUsuarios repo = FactoryRepoUsuario.get();
        this.revisores.forEach(usuario ->{
            usuario.recibiMensaje(unMensaje);
            repo.modificar(usuario);
        });

    }

    public void validaOperacion() {
        /*Ver esto*/
        ValidadorDeTransparencia validadorDeTransparencia = ValidadorDeTransparencia.instancia();
        validadorDeTransparencia.realizaValidacionAutomatica(this);
    }


    private Boolean puedeAgregarPresupuesto() {
        return this.detalle.tieneItems();
    }

    public void marcateComoAsociado (){
        this.estaAsociado = true;
    }

    public Boolean estaAsociado (){
        return this.estaAsociado;
    }

}
