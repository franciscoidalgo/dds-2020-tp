package domain.Factories;

import domain.Operacion.CategorizacionOperacion.CategoriaOperacion;
import domain.Operacion.Egreso.Comprobante;
import domain.Operacion.Egreso.DetalleOperacion;
import domain.Operacion.Egreso.Pedido;
import domain.Operacion.Egreso.Proveedor;
import spark.Request;

import java.util.List;

public class FactoryDetalle {
    public static DetalleOperacion get(Request request){
        DetalleOperacion detalleOperacion = new DetalleOperacion();

        List<Pedido> pedidos = FactoryPedido.get(request);
        List<CategoriaOperacion> categorias = FactoryCategoria.get(request);
        Proveedor proveedor = FactoryProveedor.get(request);
        Comprobante comprobante = FactoryComprobante.get(request);
        detalleOperacion.setProveedor(proveedor);
        categorias.forEach(categoriaOperacion -> System.out.println(categoriaOperacion.getDescripcion()));
        detalleOperacion.setCategoriaOperacion(categorias);
        detalleOperacion.getCategorias().forEach(categoriaOperacion -> System.out.println(categoriaOperacion.getDescripcion()));
        detalleOperacion.setComprobante(comprobante);
        detalleOperacion.setPedidos(pedidos);

        return  detalleOperacion;
    }
}
