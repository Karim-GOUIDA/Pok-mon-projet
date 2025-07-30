import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.util.ArrayList;

public class GameBoard {

    private final int TAILLE_CASE = 80;
    private final int TAILLE_PLATEAU = 9;
    private final int HAUTEUR_TOTAL = TAILLE_CASE * TAILLE_PLATEAU + 50;

    private Fenetre fenetre;
    private ArrayList<Pokemon> pokemons;
    private int joueurActif = 1;

    public GameBoard() {
        fenetre = new Fenetre("Plateau Pokémon", TAILLE_PLATEAU * TAILLE_CASE, HAUTEUR_TOTAL);
        pokemons = new ArrayList<>();
        placerPokemons();
        afficherPlateau();
    }

    private void afficherTour() {
        Rectangle bandeau = new Rectangle(Couleur.GRIS_CLAIR, new Point(0, TAILLE_CASE * TAILLE_PLATEAU),
                TAILLE_CASE * TAILLE_PLATEAU, 50, true);
        fenetre.ajouter(bandeau);
        String message = "AU JOUEUR " + (joueurActif == 1 ? "ROUGE" : "BLEU") + " DE JOUER";
        Texte texteTour = new Texte(Couleur.NOIR, message, new Font("Arial", Font.BOLD, 20),
                new Point((TAILLE_CASE * TAILLE_PLATEAU) / 2, TAILLE_CASE * TAILLE_PLATEAU + 25), Texte.CENTRE);
        fenetre.ajouter(texteTour);
        fenetre.rafraichir();
    }

    private void placerPokemons() {
        // Joueur 2 (Bleu) - Top rows
        ajouter(94, 2, 0, 0); ajouter(57, 2, 1, 0); ajouter(143, 2, 2, 0);
        ajouter(145, 2, 3, 0); ajouter(150, 2, 4, 0); ajouter(144, 2, 5, 0);
        ajouter(143, 2, 6, 0); ajouter(57, 2, 7, 0); ajouter(94, 2, 8, 0);
        ajouter(78, 2, 0, 1); ajouter(133, 2, 1, 1); ajouter(136, 2, 2, 1);
        ajouter(139, 2, 3, 1); ajouter(24, 2, 4, 1); ajouter(76, 2, 5, 1);
        ajouter(135, 2, 6, 1); ajouter(134, 2, 7, 1); ajouter(78, 2, 8, 1);
        ajouter(4, 2, 3, 2); ajouter(9, 2, 4, 2); ajouter(1, 2, 5, 2);
        // Joueur 1 (Rouge) - Bottom rows
        ajouter(1, 1, 3, 6); ajouter(9, 1, 4, 6); ajouter(4, 1, 5, 6);
        ajouter(78, 1, 0, 7); ajouter(134, 1, 1, 7); ajouter(135, 1, 2, 7);
        ajouter(76, 1, 3, 7); ajouter(24, 1, 4, 7); ajouter(139, 1, 5, 7);
        ajouter(136, 1, 6, 7); ajouter(133, 1, 7, 7); ajouter(78, 1, 8, 7);
        ajouter(94, 1, 0, 8); ajouter(57, 1, 1, 8); ajouter(143, 1, 2, 8);
        ajouter(144, 1, 3, 8); ajouter(150, 1, 4, 8); ajouter(145, 1, 5, 8);
        ajouter(143, 1, 6, 8); ajouter(57, 1, 7, 8); ajouter(94, 1, 8, 8);
    }

    private void ajouter(int numPokedex, int joueur, int x, int y) {
        if (x < 0 || x >= TAILLE_PLATEAU || y < 0 || y >= TAILLE_PLATEAU) {
            System.out.println("Position invalide: (" + x + "," + y + ")");
            return;
        }
        for (Pokemon p : pokemons) {
            if (p.getX() == x && p.getY() == y) {
                System.out.println("Position (" + x + "," + y + ") déjà occupée");
                return;
            }
        }
        Pokemon p = new Pokemon(numPokedex, joueur, x, y);
        pokemons.add(p);
    }

    private void afficherPlateau() {
        fenetre.effacer();
        Font font = new Font("Arial", Font.BOLD, 12); // 12pt font for larger text

        // Draw grid starting at (0,0) to avoid offset
        for (int x = 0; x < TAILLE_PLATEAU; x++) {
            for (int y = 0; y < TAILLE_PLATEAU; y++) {
                Rectangle cell = new Rectangle(Couleur.NOIR,
                        new Point(x * TAILLE_CASE, y * TAILLE_CASE),
                        TAILLE_CASE, TAILLE_CASE, false);
                fenetre.ajouter(cell);
            }
        }

        // Draw Pokémon and HP
        for (Pokemon p : pokemons) {
            p.draw(fenetre);
            Couleur couleurPv = (p.getJoueur() == 1) ? Couleur.ROUGE : Couleur.VERT;
            String txt = String.valueOf(p.getPv());
            int yOffset = p.getY() * TAILLE_CASE + 80 + 5; // Below sprite + 5px spacing
            int xOffset = p.getX() * TAILLE_CASE + 20; // Left side of sprite
            Texte pv = new Texte(couleurPv, txt, font,
                    new Point(xOffset, yOffset),
                    Texte.CENTRE);
            fenetre.ajouter(pv);
            // Debug: Print each Pokémon's position and HP
            System.out.println("Pokémon at (" + p.getX() + "," + p.getY() + "): PV = " + p.getPv() +
                    ", Rendered at (" + xOffset + "," + yOffset + ")");
        }

        afficherTour();
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public int getJoueurActif() {
        return joueurActif;
    }

    public void setJoueurActif(int joueur) {
        if (joueur == 1 || joueur == 2) {
            joueurActif = joueur;
            afficherPlateau();
        }
    }
}