package requiredModels.operacion.egreso.MediosDePago;


import requiredModels.operacion.egreso.MedioDePago;

import java.sql.Timestamp;

public class Tarjeta extends MedioDePago {
    private Integer numeroTarjeta;
    private Timestamp fechaVencimiento;
    private Integer codSeguridad;
    private String tipoTarjeta;

}
