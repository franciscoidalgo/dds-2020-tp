package domain.Entidad;

import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.CategorizacionOperacion.Criterio;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Operacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidad")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Entidad extends EntidadPersistente{

    @ManyToOne
    @JoinColumn( referencedColumnName = "id")
    protected Organizacion organizacion;

    @OneToMany(mappedBy = "entidad",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    protected List<Operacion> operaciones;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_juridica_id")
    protected List<Criterio> criterios;

    public Entidad() {
        this.operaciones = new ArrayList<>();
        this.criterios = new ArrayList<>();
    }

    public Entidad(Organizacion organizacion, List<Operacion> operaciones, List<Criterio> criterios) {
        this.organizacion = organizacion;
        this.operaciones = operaciones;
        this.criterios = criterios;
    }

    abstract String nombre();
    abstract String descripcion();

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(ArrayList<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public List<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(ArrayList<Criterio> criterios) {
        this.criterios = criterios;
    }
    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public void setCriterios(List<Criterio> criterios) {
        this.criterios = criterios;
    }
    //Funcionalidades!
    public void realizaOperacion(Operacion unaOperacion) {
        this.operaciones.add(unaOperacion);
    }

    public void agregaCriterio(Criterio unCriterio) {
        this.criterios.add(unCriterio);
    }

    public List<CategoriaOperacion> mostraCategoriasDe(Criterio unCriterio){
        return  unCriterio.getCategorias();
    }


    public List<OperacionEgreso> getOperacionesEgreso(){
        return (List<OperacionEgreso>) this.operaciones.stream().filter(operacion -> operacion instanceof OperacionEgreso);
    }

    public List<OperacionEgreso> getOperacionesIngreso(){
        return (List<OperacionEgreso>) this.operaciones.stream().filter(operacion -> operacion instanceof OperacionIngreso);
    }

}
