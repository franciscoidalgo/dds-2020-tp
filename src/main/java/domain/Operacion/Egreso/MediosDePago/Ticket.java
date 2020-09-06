package domain.Operacion.Egreso.MediosDePago;

import domain.Operacion.Egreso.MedioDePago;

public class Ticket extends MedioDePago {
    private Integer ticket;

    public Integer getTicket() {
        return ticket;
    }
    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    //Constructor
    public Ticket(Integer ticket){
        this.ticket = ticket;
    }

    @Override
    protected void actualizarTiposDePago() {

    }
}
