package repositorios;

import javax.persistence.criteria.CriteriaQuery;
import java.util.function.Predicate;

public class BusquedaCondicional {
    private Predicate condicionPredicado;
    private CriteriaQuery condicionCriterio;

    public BusquedaCondicional(Predicate condicionPredicado, CriteriaQuery condicionCriterio) {
        this.condicionPredicado = condicionPredicado;
        this.condicionCriterio = condicionCriterio;
    }

    public CriteriaQuery getCondicionCriterio() {
        return condicionCriterio;
    }

    public Predicate getCondicionPredicado() {
        return condicionPredicado;
    }
}
