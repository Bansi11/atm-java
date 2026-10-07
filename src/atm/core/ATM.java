package atm.core;

/**
 * Represente le distributeur physique : orchestre le materiel et les sessions.
 * Represents the physical machine: orchestrates hardware and sessions.
 */

import java.util.Scanner;
import atm.model.CheckingAccount;

public class ATM {
    // TODO: attributs (cardReader, keypad, screen, cashDispenser...) 
    // TODO: methodes startSession(), endSession()

    public void showMenu(Scanner clavier,CheckingAccount account) {
        System.out.println("QUE SOUHAITEZ-VOUS FAIRE ?");
        System.out.println("1. Retirer de l'argent");
        System.out.println("2. Consulter le solde");
        System.out.println("3. Quitter");

        int choix = clavier.nextInt();

        switch (choix) {
            case 1:
            System.out.println("ENTREZ LE MONTANT A RETIRER : ");
            double montantRetirer = clavier.nextDouble();

            if (montantRetirer <= account.getSolde()) {
                account.debit(montantRetirer);
                System.out.println("RETRAIT EFFECTUE. SOLDE RESTANT : " + account.getSolde());
            } else {
                System.out.println("FONDS INSUFFISANTS.");
            }
            break;
            case 2:
                System.out.println("VOTRE SOLDE EST : " + account.getSolde());
                break;
                case 3:
                    System.out.println("MERCI D'AVOIR UTILISE NOTRE DISTRIBUTEUR. AU REVOIR !");
                    break;
                default:
                    System.out.println("CHOIX INVALIDE.");

        }
    }
}
