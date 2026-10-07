package atm;

/**
 * Point d'entree du programme.
 * Entry point of the program.
 */
import java.util.Scanner;
import atm.model.CheckingAccount;
import atm.security.PinHasher;
import atm.security.OtpGenerator;
import atm.core.Session;
import atm.core.ATM;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner clavier = new Scanner(System.in);
        String pinCorrectHash = PinHasher.hash("1234");

        System.out.println("Bienvenue au distributeur automatique");
        System.out.println("Veuillez inserer votre carte");
        System.out.println("Entrez votre code PIN");

        CheckingAccount compte = new CheckingAccount();
        compte.credit(50000.0); // Exemple de solde initiail
        System.out.println(compte.getSolde());

            String pinSaisi = clavier.nextLine();
            Session session = new Session();

            if (session.checkPin(pinSaisi, pinCorrectHash)) {
                System.out.println("CODE PIN CORRECT. GENERATION DU CODE DE VERIFICATION...");

                String otp = OtpGenerator.generateOtp();
                System.out.println("VOTRE CODE DE VERIFICATION EST : " + otp);
                System.out.println("ENTREZ LE CODE DE VERIFICATION : ");

                String otpSaisi = clavier.nextLine();

                if (session.checkOtp(otpSaisi, otp)) {
                    ATM atm = new ATM();
                    atm.showMenu(clavier, compte);
                } else {
                    System.out.println("CODE DE VERIFICATION INCORRECT. ACCESS DENIED.");    
                }
            } else {
                System.out.println("CODE PIN INCORRECT. ACCESS DENIED.");
            }
        }
}