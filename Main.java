import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {

        Fenetre f = new Fenetre("Pokemon Battle", 720, 720);

        
        Font police = new Font("Arial", Font.PLAIN, 12);

        
        System.out.println("=== Combat 1: Colossinge vs. Ectoplasma ===");
        Pokemon p1 = new Pokemon(150, 1, 2, 2); 
        Pokemon p2 = new Pokemon(94, 2, 6, 6);  
        runCombat(f, police, p1, p2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Erreur pause entre combats: " + e.getMessage());
        }

        
        System.out.println("\n=== Combat 2: Bulbizarre vs. MISSIGNO ===");
        p1 = new Pokemon();
        p2 = new Pokemon(10, 2, 6, 6);
        runCombat(f, police, p1, p2);

        // Final pause
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Erreur pause finale: " + e.getMessage());
        }
        System.exit(0);
    }

    private static void runCombat(Fenetre f, Font police, Pokemon p1, Pokemon p2) {
        
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Rectangle cell = new Rectangle(Couleur.BLANC, new Point(x * 80, y * 80), 80, 80, false);
                f.ajouter(cell);
            }
        }

       
        while (!p1.estMort() && !p2.estMort()) {
            
            f.effacer();
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    Rectangle cell = new Rectangle(Couleur.BLANC, new Point(x * 80, y * 80), 80, 80, false);
                    f.ajouter(cell);
                }
            }

            // Perform one combat round
            p1.combat(p2);

            // Draw Pokémon
            p1.draw(f);
            p2.draw(f);

            // Display HP above Pokémon
            Texte hp1 = new Texte(Couleur.NOIR, p1.getNom() + ": " + p1.getPv() + "/" + p1.getPvMax(), police, new Point(p1.getX() * 80 + 40, p1.getY() * 80 + 90), Texte.CENTRE);
            Texte hp2 = new Texte(Couleur.NOIR, p2.getNom() + ": " + p2.getPv() + "/" + p2.getPvMax(), police, new Point(p2.getX() * 80 + 40, p2.getY() * 80 + 90), Texte.CENTRE);
            f.ajouter(hp1);
            f.ajouter(hp2);

            // Refresh display
            f.rafraichir();

            // Print status to console
            System.out.println(p1);
            System.out.println(p2);

            // Pause for visibility
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Erreur pause: " + e.getMessage());
            }
        }

        // Announce winner
        String winner = p1.estMort() ? p2.getNom() : p1.getNom();
        String loser = p1.estMort() ? p1.getNom() : p2.getNom();
        System.out.println(winner + " gagne ! " + loser + " retourne dans sa Pokéball !");

        // Display victory message
        Texte victory = new Texte(Couleur.ROUGE, winner + " gagne ! " + loser + " retourne dans sa Pokéball !", police, new Point(360, 360), Texte.CENTRE);
        f.effacer();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Rectangle cell = new Rectangle(Couleur.BLANC, new Point(x * 80, y * 80), 80, 80, false);
                f.ajouter(cell);
            }
        }
        if (!p1.estMort()) p1.draw(f);
        if (!p2.estMort()) p2.draw(f);
        f.ajouter(victory);
        f.rafraichir();
    }
}