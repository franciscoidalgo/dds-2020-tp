package domain.Operacion.Egreso;

import domain.Entidad.CategorizacionOperacion.CategoriaOperacion;
import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "detalle_operacion")
public class DetalleOperacion extends EntidadPersistente {

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "pedido")
    private List<Item> items;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "asociacion_categoria")
    private List<CategoriaOperacion> categorias;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Proveedor proveedor;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Comprobante comprobante;

    //Constructors
    public DetalleOperacion(Proveedor proveedor) {
        this.items = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.proveedor = proveedor;
        this.comprobante = null;
    }

    public DetalleOperacion() {
        this.items = new ArrayList<>();
        this.categorias = new ArrayList<>();

    }

    //Getter and Setter

    public List<CategoriaOperacion> getCriterio() {
        return categorias;
    }

    public void setCategoriaOperacion(List<CategoriaOperacion> categorias) {
        this.categorias = categorias;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public List<Item> getItems() {
        return items;
    }
    //Funcionalidad

    public Boolean tieneItems(){
        return !this.items.isEmpty();
    }

    public Boolean coincidenPedido(DetalleOperacion unDetalle){
        return unDetalle.getItems().stream().
                map(this::tenesEnListaItem).
                reduce(Boolean::logicalAnd).get();
    }

    public Boolean coincidenProveedores(DetalleOperacion unDetalle){
        return this.proveedor == unDetalle.getProveedor();
    }

    public void agregaItem(Item nuevoItem){
        this.items.add(nuevoItem);
    }

    public void removeItem(Item nuevoItem){
        this.items.remove(nuevoItem);
    }

    public void agregaCategoria(CategoriaOperacion unaCategoria){categorias.add(unaCategoria);}

    public void removeCategoria(CategoriaOperacion unaCategoria){categorias.remove(unaCategoria);}

    private Boolean tenesEnListaItem(Item unItem) {
        return this.getItems().stream().
                anyMatch(item -> item.coincidenItems(unItem));
    }


}
