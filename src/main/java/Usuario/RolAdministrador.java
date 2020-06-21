package Usuario;

public class RolAdministrador extends Rol {

    @Override
    boolean criterioCredenciales() {
        return true;
    }
}
