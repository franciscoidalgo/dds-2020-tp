package domain.Usuario;

import domain.Operacion.CategorizacionOperacion.Criterio;

public class RolEstandar implements Rol {
    @Override
    public void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) throws Exception {

            throw new Exception("No tiene permiso para hacer esto");

    }
}
