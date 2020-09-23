package repositorios;

import middleware.LoginToken;
import repositorios.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeTokens extends Repositorio<LoginToken> {

    public RepositorioDeTokens(DAO<LoginToken> dao) {
        super(dao);
    }

    public boolean existeToken (int idUsuario){
        return this.buscarPorUsuario(idUsuario) != null;
    }

    public LoginToken buscarPorUsuario(int idUsuario){
        return this.dao.buscar(busquedaPorId(idUsuario));
    }



    private BusquedaCondicional busquedaPorId (int idUsuario){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<LoginToken> ltQuery = criteriaBuilder().createQuery(LoginToken.class);

        Root<LoginToken> condicionRaiz = ltQuery.from(LoginToken.class);

        Predicate condicionIdUsuario = criteriaBuilder.equal(condicionRaiz.get("usuario"), idUsuario);

        ltQuery.where(condicionIdUsuario);

        java.util.function.Predicate<LoginToken> matcheoIdUsuario =
                loginToken -> loginToken.getUsuario().getId() == idUsuario;

        return new BusquedaCondicional(matcheoIdUsuario, ltQuery);
    }
}
