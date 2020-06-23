package Usuario;

public class RolEstandar extends Rol {
    @Override
    boolean criterioCredenciales() {
        return false;
    }
}
