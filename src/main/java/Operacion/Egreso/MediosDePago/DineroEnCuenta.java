package Operacion.Egreso.MediosDePago;

import Operacion.Egreso.MedioDePago;

public class DineroEnCuenta extends MedioDePago {
    private Integer dineroEnCuenta;

    public Integer getDineroEnCuenta() {
        return dineroEnCuenta;
    }
    public void setDineroEnCuenta(Integer dineroEnCuenta) {
        this.dineroEnCuenta = dineroEnCuenta;
    }

    //Constructor
    public DineroEnCuenta(Integer dineroEnCuenta){
        this.dineroEnCuenta = dineroEnCuenta;
    }

    @Override
    protected void actualizarTiposDePago() {

    }
}
