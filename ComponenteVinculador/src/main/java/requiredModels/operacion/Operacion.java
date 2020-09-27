package requiredModels.operacion;


import requiredModels.usuario.Usuario;

import java.time.LocalTime;

public abstract class Operacion {

    protected long nroOperacion;
    protected LocalTime fecha;
    protected Usuario creadoPor;

}
