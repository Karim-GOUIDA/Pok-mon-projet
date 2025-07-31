import MG2D.*;
import MG2D.geometrie.*;
import java.util.ArrayList;

public class Plateau {

    public static final int TAILLE_CASE = 80;
    private final int NB_CASES = 9;

    private Fenetre fenetre;
    private Pokemon[][] grid;
    private ArrayList<Dessin> dessins;

    public Plateau() {
        fenetre = new Fenetre("Plateau Pokémon", NB_CASES * TAILLE_CASE, NB_CASES * TAILLE_CASE);
        grid = new Pokemon[NB_CASES][NB_CASES];
        dessins = new ArrayList<>();
        setupDefault();
        majAffichage();
    }

    private void setupDefault() {
        // Joueur 2 (en haut)
        placerPokemon(94, 2, 0, 0); placerPokemon(57, 2, 1, 0); placerPokemon(143, 2, 2, 0);
        placerPokemon(145, 2, 3, 0); placerPokemon(150, 2, 4, 0); placerPokemon(144, 2, 5, 0);
        placerPokemon(143, 2, 6, 0); placerPokemon(57, 2, 7, 0); placerPokemon(94, 2, 8, 0);
        placerPokemon(78, 2, 0, 1); placerPokemon(133, 2, 1, 1); placerPokemon(136, 2, 2, 1);
        placerPokemon(139, 2, 3, 1); placerPokemon(24, 2, 4, 1); placerPokemon(76, 2, 5, 1);
        placerPokemon(135, 2, 6, 1); placerPokemon(134, 2, 7, 1); placerPokemon(78, 2, 8, 1);
        placerPokemon(4, 2, 3, 2); placerPokemon(9, 2, 4, 2); placerPokemon(1, 2, 5, 2);

        // Joueur 1 (en bas)
        placerPokemon(1, 1, 3, 6); placerPokemon(9, 1, 4, 6); placerPokemon(4, 1, 5, 6);
        placerPokemon(78, 1, 0, 7); placerPokemon(134, 1, 1, 7); placerPokemon(135, 1, 2, 7);
        placerPokemon(76, 1, 3, 7); placerPokemon(24, 1, 4, 7); placerPokemon(139, 1, 5, 7);
        placerPokemon(136, 1, 6, 7); placerPokemon(133, 1, 7, 7); placerPokemon(78, 1, 8, 7);
        placerPokemon(94, 1, 0, 8); placerPokemon(57, 1, 1, 8); placerPokemon(143, 1, 2, 8);
        placerPokemon(144, 1, 3, 8); placerPokemon(150, 1, 4, 8); placerPokemon(145, 1, 5, 8);
        placerPokemon(143, 1, 6, 8); placerPokemon(57, 1, 7, 8); placerPokemon(94, 1, 8, 8);
    }

    public void placerPokemon(int numeroPokedex, int joueur, int x, int y) {
        Pokemon p = new Pokemon(numeroPokedex, joueur, x, y);
        grid[y][x] = p;
    }

    public void dessinerGrille() {
        for (int i = 0; i < NB_CASES; i++) {
            for (int j = 0; j < NB_CASES; j++) {
                Couleur couleur = ((i + j) % 2 == 0) ? Couleur.GRIS_CLAIR : Couleur.BLANC;
                Carre carre = new Carre(couleur, new Point(j * TAILLE_CASE, i * TAILLE_CASE), TAILLE_CASE, true);
                dessins.add(carre);
                fenetre.ajouter(carre);
            }
        }
    }

    public void placerTousLesPokemons() {
        for (int y = 0; y < NB_CASES; y++) {
            for (int x = 0; x < NB_CASES; x++) {
                Pokemon p = grid[y][x];
                if (p != null) {
                    p.draw(fenetre);
                    // PV coin bas gauche
                    Texte t = new Texte(
                        (p.getJoueur() == 1 ? Couleur.ROUGE : Couleur.VERT),
                        String.valueOf(p.getPv()),
                        new java.awt.Font("Arial", java.awt.Font.BOLD, 12),
                        new Point(x * TAILLE_CASE + 5, y * TAILLE_CASE + 5)
                    );
                    fenetre.ajouter(t);
                }
            }
        }
    }

    public void majAffichage() {
        fenetre.effacer();
        dessins.clear();
        dessinerGrille();
        placerTousLesPokemons();
        fenetre.rafraichir();
    }

    public Fenetre getFenetre() {
        return fenetre;
    }

    public Pokemon[][] getGrid() {
        return grid;
    }
}
