package repositorios;

import domain.Entidad.Usuario.BandejaMensaje;
import repositorios.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioBandejasDeMensajes extends Repositorio<BandejaMensaje> {

    public RepositorioBandejasDeMensajes(DAO<BandejaMensaje> dao) {
        super(dao);
    }

    public BandejaMensaje buscarBandejaDeUsuario (int userId){
        return this.dao.buscar(busquedaPorUsuario(userId));
    }

    public BusquedaCondicional busquedaPorUsuario(int idUsuario) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<BandejaMensaje> bandejaQuery = criteriaBuilder.createQuery(BandejaMensaje.class);

        Root<BandejaMensaje> condicionRaiz = bandejaQuery.from(BandejaMensaje.class);

        Predicate perteneceAUsuario = criteriaBuilder.equal(condicionRaiz.get("usuario"), idUsuario);

        bandejaQuery.where(perteneceAUsuario);

        java.util.function.Predicate<BandejaMensaje> predicado =
                bandeja -> bandeja.getUsuario().getId() == idUsuario;

        return new BusquedaCondicional(predicado, bandejaQuery);
    }

}
