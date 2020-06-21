package Operacion.Egreso;

public class MedioDePago {
    private String medioDePago;
    private String paymentTypeID;
    private String id;

    //Getter Setter
    public String getMedioDePago() { return medioDePago; }
    public void setMedioDePago(String medioDePago) { this.medioDePago = medioDePago; }

    public String getPaymentTypeID() { return paymentTypeID; }
    public void setPaymentTypeID(String paymentTypeID) { this.paymentTypeID = paymentTypeID; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    //Constructor
    public MedioDePago(String medioDePago, String paymentTypeID, String id){
        this.medioDePago = medioDePago;
        this.paymentTypeID = paymentTypeID;
        this.id = id;
    }

    //Funcionalidad
    public void actualizarTiposDePago(){

    }
}
