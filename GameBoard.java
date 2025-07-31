import MG2D.*;
import MG2D.geometrie.*;
import java.awt.Font;
import java.util.ArrayList;
public class GameBoard {
   private final int TAILLE_CASE = 80;
   private final int TAILLE_PLATEAU = 9;
   private final int LARGEUR = TAILLE_CASE * TAILLE_PLATEAU;
   private final int HAUTEUR = TAILLE_CASE * TAILLE_PLATEAU;
   private Fenetre fenetre;
   private ArrayList<Pokemon> pokemons;
   public GameBoard() {
       fenetre = new Fenetre("Plateau Pokémon", LARGEUR, HAUTEUR);
       pokemons = new ArrayList<>();
       placerPokemons();
       afficherPlateau();
   }
   private void placerPokemons() {
       ajouter(94, 2, 0, 0); ajouter(57, 2, 1, 0); ajouter(143, 2, 2, 0);
       ajouter(145, 2, 3, 0); ajouter(150, 2, 4, 0); ajouter(144, 2, 5, 0);
       ajouter(143, 2, 6, 0); ajouter(57, 2, 7, 0); ajouter(94, 2, 8, 0);
       ajouter(78, 2, 0, 1); ajouter(133, 2, 1, 1); ajouter(136, 2, 2, 1);
       ajouter(139, 2, 3, 1); ajouter(24, 2, 4, 1); ajouter(76, 2, 5, 1);
       ajouter(135, 2, 6, 1); ajouter(134, 2, 7, 1); ajouter(78, 2, 8, 1);
       ajouter(4, 2, 3, 2); ajouter(9, 2, 4, 2); ajouter(1, 2, 5, 2);
       ajouter(1, 1, 3, 6); ajouter(9, 1, 4, 6); ajouter(4, 1, 5, 6);
       ajouter(78, 1, 0, 7); ajouter(134, 1, 1, 7); ajouter(135, 1, 2, 7);
       ajouter(76, 1, 3, 7); ajouter(24, 1, 4, 7); ajouter(139, 1, 5, 7);
       ajouter(136, 1, 6, 7); ajouter(133, 1, 7, 7); ajouter(78, 1, 8, 7);
       ajouter(94, 1, 0, 8); ajouter(57, 1, 1, 8); ajouter(143, 1, 2, 8);
       ajouter(144, 1, 3, 8); ajouter(150, 1, 4, 8); ajouter(145, 1, 5, 8);
       ajouter(143, 1, 6, 8); ajouter(57, 1, 7, 8); ajouter(94, 1, 8, 8);
   }
   private void ajouter(int numPokedex, int joueur, int x, int y) {
       pokemons.add(new Pokemon(numPokedex, joueur, x, y));
   }
   private void afficherPlateau() {
       fenetre.effacer();
       Font font = new Font("Arial", Font.BOLD, 14);
       // Grille
       for (int x = 0; x < TAILLE_PLATEAU; x++) {
           for (int y = 0; y < TAILLE_PLATEAU; y++) {
               Point point = new Point(x * TAILLE_CASE, y * TAILLE_CASE);
               fenetre.ajouter(new Rectangle(Couleur.NOIR, point, TAILLE_CASE, TAILLE_CASE, false));
           }
       }
       for (Pokemon p : pokemons) {
           p.draw(fenetre);
           Couleur couleurPv = (p.getJoueur() == 1) ? Couleur.ROUGE : Couleur.VERT;
           String txt = String.valueOf(p.getPv());
           int xText = p.getX() * TAILLE_CASE + 5;
           int yText = p.getY() * TAILLE_CASE + 5;
           fenetre.ajouter(new Texte(couleurPv, txt, font, new Point(xText, yText), Texte.CENTRE));
       }
       fenetre.rafraichir();
   }
}