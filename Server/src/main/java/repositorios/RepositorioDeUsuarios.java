package repositorios;

import domain.Usuario.Usuario;
import repositorios.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class RepositorioDeUsuarios extends Repositorio<Usuario> {

    public RepositorioDeUsuarios(DAO<Usuario> dao) {
        super(dao);
    }

    public Boolean verificarExistencia (String nombreDeUsuario, String contrasenia){
        return buscarUsuario(nombreDeUsuario, contrasenia) != null;
    }

    public Usuario buscarUsuario(String nombreDeUsuario, String contrasenia){
        return this.dao.buscar(matchearUsuarioYContrasenia(nombreDeUsuario, contrasenia));
    }

    private BusquedaCondicional matchearUsuarioYContrasenia (String nombreDeUsuario, String contrasenia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Usuario> usuarioQuery = criteriaBuilder.createQuery(Usuario.class);

        Root<Usuario> condicionRaiz = usuarioQuery.from(Usuario.class);

        Predicate condicionNombreDeUsuario = criteriaBuilder.equal(condicionRaiz.get("username"), nombreDeUsuario);
        Predicate condicionContrasenia =criteriaBuilder.equal(condicionRaiz.get("password"), contrasenia);

        Predicate condicionExisteUsuario = criteriaBuilder.and(condicionNombreDeUsuario, condicionContrasenia);

        usuarioQuery.where(condicionExisteUsuario);

        java.util.function.Predicate<Usuario> matcheoUsuarioContrasenia =
                usuario -> usuario.getUsername().equals(nombreDeUsuario) && usuario.getPassword().equals(contrasenia);

        return new BusquedaCondicional(matcheoUsuarioContrasenia, usuarioQuery);
    }
}
