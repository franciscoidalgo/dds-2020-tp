package Egreso;

import Usuario.Usuario;

import java.sql.Timestamp;
import java.util.ArrayList;



public class Egreso {

    //Atributos
    private long nroEgreso;
    private Timestamp fecha;
    private Proveedor proveedor;
    private Comprobante comprobante;
    private MedioDePago medioDePago;
    private ArrayList<Detalle> detalle;
    private Usuario usuario;

    //Constructor



    //Getters-Setters
    public long getNroEgreso() {
        return nroEgreso;
    }

    public void setNroEgreso(long nroEgreso) {
        this.nroEgreso = nroEgreso;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
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

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public ArrayList<Detalle> getDetalle(){
        return this.detalle;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //Metodos

    public void agregarItem(Producto nuevoProducto, int cantidad){//TODO: Cambiar por un producto entero y una cantidad
        //Agregar try catch?
        this.detalle.add(new Detalle(nuevoProducto,cantidad));
    }

    public double precioTotal(){
     return  detalle.stream()
             .mapToDouble(unItem -> unItem.subtotal())
             .sum();//TODO: Tratar cuando no haya items.
    }

    public void registrar(){
        //TODO: Agregar logica de registro(No definiada).
    }
}