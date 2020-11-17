package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;
import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "detalle_operacion")
public class DetalleOperacion extends EntidadPersistente {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Pedido> pedidos;

    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    @JoinTable(name = "asociacion_categoria")
    private Set<CategoriaOperacion> categorias;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "comprobante_id")
    private Comprobante comprobante;

    //Constructors
    public DetalleOperacion(Proveedor proveedor) {
        this.pedidos = new HashSet<>();
        this.categorias = new HashSet<>();
        this.proveedor = proveedor;
        this.comprobante = null;
    }

    public DetalleOperacion(Proveedor proveedor, List<Pedido> items, List<CategoriaOperacion> categoriaOperaciones, Comprobante comprobante) {
        this.pedidos = new HashSet<>(items);
        this.categorias = new HashSet<>(categoriaOperaciones);
        this.proveedor = proveedor;
        this.comprobante = comprobante;
    }

    public DetalleOperacion() {
        this.pedidos = new HashSet<>();
        this.categorias = new HashSet<>();

    }

    //Getter and Setter


    public void setCategoriaOperacion(List<CategoriaOperacion> categorias) {
        this.categorias = new HashSet<>(categorias);
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

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = new HashSet<>(pedidos);
    }

    public List<CategoriaOperacion> getCategorias() {
        return new ArrayList<>(categorias);
    }


    //Funcionalidad

    public Boolean tieneItems() {
        return !this.pedidos.isEmpty();
    }

    public Boolean coincidenPedido(DetalleOperacion unDetalle) {
        return unDetalle.getPedidos().stream().
                anyMatch(pedido -> pedido.coincidenPedidos(this.getPedidos())) ;
    }

    public Boolean coincidenProveedores(DetalleOperacion unDetalle) {
        return this.proveedor.equals(unDetalle.getProveedor());
    }

    public void agregaPedido(Pedido nuevoItem) {
        this.pedidos.add(nuevoItem);
    }

    public void removePedido(Pedido nuevoItem) {
        this.pedidos.remove(nuevoItem);
    }

    public void agregaCategoria(CategoriaOperacion unaCategoria) {
        categorias.add(unaCategoria);
    }

    public void removeCategoria(CategoriaOperacion unaCategoria) {
        categorias.remove(unaCategoria);
    }

    public double calcularMontoTotal() {
        return pedidos.stream().mapToDouble(Pedido::precioTotal).sum();
    }

    public List<Item> getItems() {
        return this.getPedidos().stream()
                .map(Pedido::getItem)
                .collect(Collectors.toList());
    }

    public boolean tenesCategoria(CategoriaOperacion categoria) {
        return this.categorias.stream()
                .anyMatch(categoriaOperacion -> categoriaOperacion.equals(categoria));
    }
}
