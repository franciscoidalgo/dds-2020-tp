package Operacion.Egreso.MediosDePago;

import Operacion.Egreso.MedioDePago;

public class ATM extends MedioDePago {
    private Integer atm;

    public Integer getAtm() {
        return atm;
    }
    public void setAtm(Integer atm) {
        this.atm = atm;
    }

    //Constructor
    public ATM(Integer atm){
        this.atm = atm;
    }

    @Override
    protected void actualizarTiposDePago() {

    }
}
