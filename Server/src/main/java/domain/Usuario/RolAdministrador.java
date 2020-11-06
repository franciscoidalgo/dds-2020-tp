package domain.Usuario;

import domain.Operacion.CategorizacionOperacion.Criterio;

public class RolAdministrador implements Rol {
    @Override
    public void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) {
        unCriterioPadre.setCriterioHijo(unCriterioHijo);
    }

}
