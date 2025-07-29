import MG2D.*;
import MG2D.geometrie.*;
import java.util.ArrayList;

public class GameBoard {

    private final int TAILLE_CASE = 80;
    private final int TAILLE_PLATEAU = 9;
    private Fenetre fenetre;
    private ArrayList<Pokemon> pokemons;

    public GameBoard() {
        fenetre = new Fenetre("Plateau Pokémon", TAILLE_PLATEAU * TAILLE_CASE, TAILLE_PLATEAU * TAILLE_CASE);
        pokemons = new ArrayList<>();
        placerPokemons();
        afficherPlateau();
    }

    private void placerPokemons() {
        // Ligne 0 - Joueur 2
        ajouter(94, 2, 0, 0); // Ectoplasma
        ajouter(57, 2, 1, 0); // Colossinge
        ajouter(143, 2, 2, 0); // Ronflex
        ajouter(145, 2, 3, 0); // Electhor
        ajouter(150, 2, 4, 0); // Mewtwo
        ajouter(144, 2, 5, 0); // Artikodin
        ajouter(143, 2, 6, 0); // Ronflex
        ajouter(57, 2, 7, 0); // Colossinge
        ajouter(94, 2, 8, 0); // Ectoplasma

        // Ligne 1 - Joueur 2
        ajouter(78, 2, 0, 1); // Galopa
        ajouter(133, 2, 1, 1); // Evoli
        ajouter(136, 2, 2, 1); // Pyroli
        ajouter(139, 2, 3, 1); // Amonistar
        ajouter(24, 2, 4, 1); // Arbok
        ajouter(76, 2, 5, 1); // Grolem
        ajouter(135, 2, 6, 1); // Voltali
        ajouter(134, 2, 7, 1); // Aquali
        ajouter(78, 2, 8, 1); // Galopa

        // Ligne 2 - Joueur 2
        ajouter(4, 2, 3, 2); // Salamèche
        ajouter(9,2 , 4, 2); // Tortank
        ajouter(1, 2, 5, 2); // Bulbizarre

        // Ligne 6 - Joueur 1
        ajouter(1, 1, 3, 6); // Bulbizarre
        ajouter(9,1 , 4, 6); // Tortank
        ajouter(4, 1, 5, 6); // Salamèche

        // Ligne 7 - Joueur 1
        ajouter(78, 1, 0, 7); // Galopa
        ajouter(134, 1, 1, 7); // Aquali
        ajouter(135, 1, 2, 7); // Voltali
        ajouter(76, 1, 3, 7); // Grolem
        ajouter(24, 1, 4, 7); // Arbok
        ajouter(139, 1, 5, 7); // Amonistar
        ajouter(136, 1, 6, 7); // Pyroli
        ajouter(133, 1, 7, 7); // Evoli
        ajouter(78, 1, 8, 7); // Galopa

        // Ligne 8 - Joueur 1
        ajouter(94, 1, 0, 8); // Ectoplasma
        ajouter(57, 1, 1, 8); // Colossinge
        ajouter(143, 1, 2, 8); // Ronflex
        ajouter(144, 1, 3, 8); // Artikodin
        ajouter(150, 1, 4, 8); // Mewtwo
        ajouter(145, 1, 5, 8); // Electhor
        ajouter(143, 1, 6, 8); // Ronflex
        ajouter(57, 1, 7, 8); // Colossinge
        ajouter(94, 1, 8, 8); // Ectoplasma
    }

    private void ajouter(int numPokedex, int joueur, int x, int y) {
        Pokemon p = new Pokemon(numPokedex, joueur, x, y);
        pokemons.add(p);
    }

    private void afficherPlateau() {
        // Affiche les cases
        for (int x = 0; x < TAILLE_PLATEAU; x++) {
            for (int y = 0; y < TAILLE_PLATEAU; y++) {
                Rectangle cell = new Rectangle(Couleur.NOIR, new Point(x * TAILLE_CASE, y * TAILLE_CASE), TAILLE_CASE, TAILLE_CASE, false);
                fenetre.ajouter(cell);
            }
        }

        // Affiche les Pokémon
        for (Pokemon p : pokemons) {
            p.draw(fenetre);
        }

        fenetre.rafraichir();
    }
}