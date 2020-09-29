package requiredModels.operacion;


import requiredModels.usuario.Usuario;

import java.time.LocalDate;

public abstract class Operacion {

    protected long nroOperacion;
    protected LocalDate fecha;
    protected Usuario creadoPor;

    public LocalDate getFecha() {
        return fecha;
    }

}
