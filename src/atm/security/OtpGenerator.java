package atm.security;

/**
 * Generation et verification d'un code OTP a usage unique.
 * Generation and verification of a one-time OTP code.
 */
import java.util.Random;

public class OtpGenerator {
    // TODO: generateOtp(), verifyOtp(String input)
    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    public static boolean verifyOtp(String saisi, String genere) {
        return saisi.equals(genere);    
    }
}
