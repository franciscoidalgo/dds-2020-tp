package Usuario;

import Entidad.CategorizacionOperacion.Criterio;

public interface Rol {
    //Metodos
    void daleJerarquiaA(Criterio unCriterioPadre, Criterio unCriterioHijo) throws Exception;
}