package passwordHasher;


public class PasswordHasher {
    private static HashingMethod hasher = new Guava256Hasher();

    public static String hash(String password){
        return hasher.hashPassword(password);
    }
}
