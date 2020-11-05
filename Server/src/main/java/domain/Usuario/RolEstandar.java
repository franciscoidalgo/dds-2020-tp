package domain.Usuario;

import domain.Entidad.CategorizacionOperacion.Criterio;

public class RolEstandar implements Rol {
    @Override
    public void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) throws Exception {

            throw new Exception("No tiene permiso para hacer esto");

    }
}
