package passwordHasher;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Guava256Hasher implements HashingMethod{
    @Override
    public String hashPassword(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
