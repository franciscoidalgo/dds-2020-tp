package domain.Operacion.Egreso;

import domain.Entidad.EntidadPersistente;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido extends EntidadPersistente {

    @ManyToOne(cascade = CascadeType.ALL,fetch =  FetchType.EAGER)
    private Item item;

    @Column(name = "cantidad")
    private Integer cantidad;

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
        return pedidos.stream().anyMatch(this::sosIdenticosA);
    }

    public Boolean sosIdenticosA(Pedido unPedido){
      return this.cantidad.equals(unPedido.getCantidad()) && this.item.coincidenItems(unPedido.getItem());
    }

}
