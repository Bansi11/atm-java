package atm.security;

/**
 * Hachage et verification du PIN (jamais stocke/compare en clair).
 * PIN hashing and verification (never stored/compared in plain text).
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PinHasher {
    // TODO: hash(String pin), verify(String pin, String storedHash)
    public static String hash(String pin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(pin.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
