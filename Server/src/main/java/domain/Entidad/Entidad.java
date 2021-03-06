package domain.Entidad;

import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.CategorizacionOperacion.Criterio;
import domain.Operacion.Egreso.OperacionEgreso;
import domain.Operacion.Ingreso.OperacionIngreso;
import domain.Operacion.Operacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "entidad")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Entidad extends EntidadPersistente {

    @ManyToOne
    protected Organizacion organizacion;

    @OneToMany(mappedBy = "entidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Operacion> operaciones;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public String getNombre() {
        return "";
    }

    public String getDescripcion() {
        return "";
    }


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
        unaOperacion.setEntidad(this);
    }

    public void agregaCriterio(Criterio unCriterio) {
        this.criterios.add(unCriterio);
    }

    public void quitarCriterio(Criterio unCriterio) {
        this.criterios.remove(unCriterio);
    }

    public List<CategoriaOperacion> mostraCategoriasDe(Criterio unCriterio) {
        return unCriterio.getCategorias();
    }


    public List<OperacionEgreso> getOperacionesEgreso() {
        return this.operaciones.stream().filter(operacion -> operacion instanceof OperacionEgreso).map(operacion -> (OperacionEgreso) operacion).collect(Collectors.toList());
    }

    public List<OperacionIngreso> getOperacionesIngreso() {
        return this.operaciones.stream().filter(operacion -> operacion instanceof OperacionIngreso).map(operacion -> (OperacionIngreso) operacion).collect(Collectors.toList());
    }

    public boolean realizasteOperacion(Operacion operacion) {
        return this.operaciones.contains(operacion);
    }

    public List<CategoriaOperacion> mostrarTodasCategorias() {
        List<CategoriaOperacion> categoriaOperacions = new ArrayList<>();

        this.criterios.forEach(criterio -> {
            Criterio hijo = criterio.getCriterioHijo();
            categoriaOperacions.addAll(criterio.getCategorias());

            while (null != hijo) {
                categoriaOperacions.addAll(hijo.getCategorias());
                hijo = hijo.getCriterioHijo();
            }
        });

        return categoriaOperacions;

    }

    public List<Criterio> mostrarTodosCriterios() {
        List<Criterio> criteriosSeleccionados = new ArrayList<>();

        this.criterios.forEach(criterio -> {
            Criterio hijo = criterio.getCriterioHijo();
            criteriosSeleccionados.add(criterio);

            while (null != hijo) {
                criteriosSeleccionados.add(hijo);
                hijo = hijo.getCriterioHijo();
            }

        });

        return criteriosSeleccionados;

    }

}
