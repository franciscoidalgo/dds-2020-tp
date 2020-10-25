package domain.Entidad.Usuario;

import domain.Entidad.CategorizacionOperacion.Criterio;

public class RolAdministrador implements Rol {
    @Override
    public void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) {
        unCriterioPadre.setCriterioHijo(unCriterioHijo);
    }

}
