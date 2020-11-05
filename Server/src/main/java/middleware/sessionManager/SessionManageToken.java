package middleware.sessionManager;

import com.google.common.annotations.Beta;
import domain.Usuario.Usuario;
import middleware.LoginToken;
import repositorios.RepositorioDeTokens;
import repositorios.factories.FactoryRepoTokens;
import spark.Request;
import spark.Response;

@Beta
public class SessionManageToken implements SessionManageMethod {
    @Override
    public boolean estaLogueado(Request request) {
        RepositorioDeTokens repositorioDeTokens = FactoryRepoTokens.get();

        if(request.cookie("idUsuario")!=null){
            int idUsuario = Integer.parseInt(request.cookie("idUsuario"));
            try{
                return repositorioDeTokens.existeToken(idUsuario);
            }catch (Exception e){
                return false;
            }

        }else return false;
    }

    @Override
    public void login(Request request, Response response, Usuario usuario) {
        response.cookie("idUsuario", String.valueOf(usuario.getId()), 86400, false, true);
        LoginToken.generarToken(usuario, request.ip());
    }

    @Override
    public void logout(Request request, Response response) {
        RepositorioDeTokens repositorioDeTokens = FactoryRepoTokens.get();
        LoginToken loginToken = repositorioDeTokens.buscarPorUsuario(Integer.parseInt(request.cookie("idUsuario")));
        repositorioDeTokens.eliminar(loginToken);
        response.removeCookie("idUsuario");
    }
}
