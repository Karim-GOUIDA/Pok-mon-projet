import MG2D.*;
import MG2D.geometrie.*;
import java.util.ArrayList;

public class GameBoard {

    private Plateau plateau;
    private Pokemon[][] grid;
    private Souris souris;
    private Pokemon selected = null;
    private ArrayList<Point> accessibles = new ArrayList<>();

    public GameBoard(Plateau plateau) {
        this.plateau = plateau;
        this.grid = plateau.getGrid();

        souris = new Souris(Plateau.TAILLE_CASE * 9);

        plateau.majAffichage();

        boucleJeu();
    }

    private void boucleJeu() {
        while (true) {
            if (souris.getClicGauche()) {
                Point pos = souris.getPosition();
                int x = pos.getX() / Plateau.TAILLE_CASE;
                int y = pos.getY() / Plateau.TAILLE_CASE;

                if (x >= 0 && x < 9 && y >= 0 && y < 9) {
                    handleClick(x, y);
                }

                souris.reinitialisation();
            }

            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
        
            }
        }
    }

    private void handleClick(int x, int y) {
        Pokemon cible = grid[y][x];

        if (selected == null) {
            if (cible != null) {
                selected = cible;
                afficherCasesAccessibles(selected);
            }
        } else {
            if (estAccessible(x, y) && grid[y][x] == null) {
                grid[selected.getY()][selected.getX()] = null;
                selected.setPosition(x, y);
                grid[y][x] = selected;
            }

            selected = null;
            accessibles.clear();
            plateau.majAffichage();
        }
    }

    private void afficherCasesAccessibles(Pokemon p) {
        accessibles.clear();
        int[][] dirs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} }; // haut, bas, gauche, droite

        for (int[] d : dirs) {
            int nx = p.getX() + d[0];
            int ny = p.getY() + d[1];
            if (nx >= 0 && nx < 9 && ny >= 0 && ny < 9 && grid[ny][nx] == null) {
                accessibles.add(new Point(nx, ny));
                Point centre = new Point(
                    nx * Plateau.TAILLE_CASE + Plateau.TAILLE_CASE / 2,
                    ny * Plateau.TAILLE_CASE + Plateau.TAILLE_CASE / 2
                );
                Cercle cercle = new Cercle(Couleur.NOIR, centre, Plateau.TAILLE_CASE / 4, false);
                plateau.getFenetre().ajouter(cercle);
            }
        }
        plateau.getFenetre().rafraichir();
    }

    private boolean estAccessible(int x, int y) {
        for (Point p : accessibles) {
            if (p.getX() == x && p.getY() == y) return true;
        }
        return false;
    }
}