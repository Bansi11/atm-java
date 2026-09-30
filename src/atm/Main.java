package atm;

/**
 * Point d'entree du programme.
 * Entry point of the program.
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);

        System.out.println("Bienvenue au distributeur automatique");
        System.out.println("Veuillez inserer votre carte");
        System.out.println("Entrez votre code PIN");

        double solde = 50000.0; // Exemple de solde initial
        System.out.println(solde);
        int pinCorrect = 1234; // Exemple de code PIN correct
        System.out.println(pinCorrect == 1234);

        int pinSaisi = clavier.nextInt();

        if (pinSaisi == pinCorrect) {
            System.out.println("Code PIN correct.");
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Retirer de l'argent");
            System.out.println("2. Consulter le solde");
            System.out.println( "3. Quitter");

            int choix = clavier.nextInt();
            
            switch (choix) {
                case 1:
                    System.out.println("Entrez le montant a retirer : ");
                    double montantRetirer = clavier.nextDouble();
                    if (montantRetirer <= solde) {
                        solde -= montantRetirer;
                        System.out.println("Retrait effectue. Solde restant : " + solde);
                    } else {
                        System.out.println("Fonds insuffisants.");
                    }
                    break;
                case 2:
                    System.out.println("Votre solde est : " + solde);
                    break;
                case 3:
                    System.out.println("Merci d'avoir utilise notre distributeur automatique.");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } else {
            System.out.println("Code PIN incorrect. Access Denied");
        }
    }
}