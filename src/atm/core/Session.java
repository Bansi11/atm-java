package atm.core;

/**
 * Represente une session utilisateur : du moment ou la carte est inseree
 * jusqu'a l'ejection.
 * Represents a user session: from card insertion to card ejection.
 */ 

import atm.security.PinHasher;
import atm.security.OtpGenerator;

public class Session {
    //  isAuthenticated, authenticate(pin), cancel()
    private boolean authenticated;

    public boolean checkPin(String pinSaisi, String pinCorrectHash) 
    throws Exception {
        String pinSaisiHash = PinHasher.hash(pinSaisi);
        return pinSaisiHash.equals(pinCorrectHash);
    }
    public boolean checkOtp (String otpSaisi, String otpCorrect) {
        return OtpGenerator.verifyOtp(otpSaisi, otpCorrect);
    }
}
