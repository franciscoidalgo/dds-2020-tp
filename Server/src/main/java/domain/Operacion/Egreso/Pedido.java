package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido extends EntidadPersistente {

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Item item;

    @Column(name = "cantidad")
    Integer cantidad;

    //Constructor
    public Pedido(Item item, Integer cantidad) {
        this.item = item;
        this.cantidad = cantidad;
    }

    public Pedido() {}

    //Getters y Setters
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    //Funcionalidad
    public Double precioTotal() {
        return this.cantidad * this.item.getPrecioUnitario();
    }

    public Boolean coincidenPedidos(List<Pedido> pedidos) {
        return  pedidos.stream().
                anyMatch(unPedido -> sonPedidosIdenticos(this,unPedido));
    }

    private Boolean sonPedidosIdenticos(Pedido otroPedido,Pedido unPedido){
      return   otroPedido.cantidad.equals(unPedido.cantidad) &&
                otroPedido.item.coincidenItems(unPedido.getItem());
    }

}
