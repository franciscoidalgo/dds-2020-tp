package middleware;

import domain.Entidad.EntidadPersistente;
import domain.Usuario.Usuario;
import repositorios.RepositorioDeTokens;
import repositorios.factories.FactoryRepoTokens;

import javax.persistence.*;
import java.util.UUID;

import static spark.Spark.halt;

@Entity
@Table(name = "login_token")
public class LoginToken extends EntidadPersistente {
    @Column(name="token")
    private String token;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Column(name="ip")
    private String ip;

    public LoginToken(){}

    public LoginToken(Usuario usuario, String ip){
        this.token = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.ip = ip;
    }

    public static void generarToken (Usuario usuario, String ip){
        int idUsuario = usuario.getId();
        RepositorioDeTokens repositorioLoginToken = FactoryRepoTokens.get();
        LoginToken nuevoToken = new LoginToken(usuario, ip);
        try{
            if(repositorioLoginToken.existeToken(idUsuario)){

                LoginToken tokenPrevio = repositorioLoginToken.buscarPorUsuario(idUsuario);
                nuevoToken.setId(tokenPrevio.getId());
                repositorioLoginToken.modificar(nuevoToken);

            }else{
                repositorioLoginToken.agregar(nuevoToken);
            }
        }catch (Exception e){
            repositorioLoginToken.agregar(nuevoToken);
        }
    }

    //Getters y setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
