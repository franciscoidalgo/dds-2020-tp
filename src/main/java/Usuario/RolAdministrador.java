package Usuario;

import Entidad.CategorizacionOperacion.Criterio;

public class RolAdministrador implements Rol {
    @Override
    public void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) {
        unCriterioPadre.setCriterioHijo(unCriterioHijo);
    }

    /*
    public void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) throws Exception {

        if (this.rol.criterioCredenciales()) {
            unCriterioPadre.setCriterioHijo(unCriterioHijo);
        }else {
            throw new Exception("No tiene permiso para hacer esto");
        }
    }*/
    //Esto mismo va en el test
}
