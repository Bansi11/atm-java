package atm.model;

/**
 * Classe abstraite - un compte generique.
 * Abstract class - a generic account.
 */
public abstract class Account {
    private double solde;
    public double getSolde() {
        return solde;
    }

    public void debit(double montant) {
        solde = solde - montant;
    }

    public void credit(double montant) { 
        solde = solde + montant;
    }
}
