package Usuario;

public class RolAdministrador implements Rol {
    @Override
    public boolean criterioCredenciales() {
        return true;
    }
}
