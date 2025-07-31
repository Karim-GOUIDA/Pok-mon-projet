import MG2D.*;
import MG2D.geometrie.*;
import java.util.ArrayList;

public class GameBoard {

    private Plateau plateau;
    private Pokemon[][] grid;
    private Souris souris;
    private Pokemon selected = null;
    private ArrayList<Point> accessibles = new ArrayList<>();
    private int joueurActif = 1;

    public GameBoard(Plateau plateau) {
        this.plateau = plateau;
        this.grid = plateau.getGrid();

        souris = new Souris(Plateau.TAILLE_CASE * 9);
        plateau.getFenetre().addMouseListener(souris);
        plateau.getFenetre().addMouseMotionListener(souris);

        plateau.majAffichage();
        afficherBandeauTour();
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

            try { Thread.sleep(80); } catch (InterruptedException e) {}
        }
    }

    private void handleClick(int x, int y) {
        Pokemon cible = grid[y][x];

        if (selected == null) {
            if (cible != null && cible.getJoueur() == joueurActif) {
                selected = cible;
                afficherCasesAccessibles(selected);
            }
        } else {
            if (estAccessible(x, y) && cible == null) {
                // Déplacement
                grid[selected.getY()][selected.getX()] = null;
                selected.setPosition(x, y);
                grid[y][x] = selected;
                finTour();
            }
            else if (cible != null && cible.getJoueur() != joueurActif && estAccessible(x, y)) {
                // Combat
                selected.combat(cible);

                if (cible.estMort()) {
                    // Si c'est Mewtwo → fin de partie
                    if (cible.getNumeroPokedex() == 150) {
                        afficherEcranVictoire(joueurActif);
                        return;
                    }

                    grid[y][x] = null;
                    grid[selected.getY()][selected.getX()] = null;
                    selected.setPosition(x, y);
                    grid[y][x] = selected;
                }
                finTour();
            }

            selected = null;
            accessibles.clear();
            plateau.majAffichage();
            afficherBandeauTour();
        }
    }

    private void afficherCasesAccessibles(Pokemon p) {
        accessibles.clear();
        // 8 directions possibles
        int[][] dirs = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] d : dirs) {
            int nx = p.getX() + d[0];
            int ny = p.getY() + d[1];
            if (nx >= 0 && nx < 9 && ny >= 0 && ny < 9) {
                if (grid[ny][nx] == null || grid[ny][nx].getJoueur() != p.getJoueur()) {
                    accessibles.add(new Point(nx, ny));
                    Point centre = new Point(
                        nx * Plateau.TAILLE_CASE + Plateau.TAILLE_CASE / 2,
                        ny * Plateau.TAILLE_CASE + Plateau.TAILLE_CASE / 2
                    );
                    Cercle cercle = new Cercle(Couleur.NOIR, centre, Plateau.TAILLE_CASE / 4, false);
                    plateau.getFenetre().ajouter(cercle);
                }
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

    private void finTour() {
        joueurActif = (joueurActif == 1) ? 2 : 1;
    }

    private void afficherBandeauTour() {
        int largeur = 9 * Plateau.TAILLE_CASE;
        Rectangle bandeau = new Rectangle(Couleur.GRIS_CLAIR,
            new Point(0, 9 * Plateau.TAILLE_CASE), largeur, 40, true);
        plateau.getFenetre().ajouter(bandeau);

        String msg = "Tour du joueur " + (joueurActif == 1 ? "ROUGE" : "VERT");
        Couleur couleur = (joueurActif == 1) ? Couleur.ROUGE : Couleur.VERT;
        Texte t = new Texte(couleur, msg,
            new java.awt.Font("Arial", java.awt.Font.BOLD, 18),
            new Point(largeur / 2, 9 * Plateau.TAILLE_CASE + 20), Texte.CENTRE);
        plateau.getFenetre().ajouter(t);
        plateau.getFenetre().rafraichir();
    }

    // 🎉 Écran de victoire avec boutons Rejouer / Quitter
    private void afficherEcranVictoire(int joueur) {
        plateau.majAffichage();

        Texte victoire = new Texte(
            Couleur.ROUGE,
            "🎉 Joueur " + joueur + " a gagné ! 🎉",
            new java.awt.Font("Arial", java.awt.Font.BOLD, 28),
            new Point(360, Plateau.TAILLE_CASE * 6),
            Texte.CENTRE
        );

        // Bouton Rejouer
        Rectangle btnRejouer = new Rectangle(Couleur.BLEU, new Point(200, Plateau.TAILLE_CASE * 3), 150, 50, true);
        Texte txtRejouer = new Texte(Couleur.BLANC, "Rejouer", new java.awt.Font("Arial", java.awt.Font.BOLD, 20),
            new Point(275, Plateau.TAILLE_CASE * 3 + 15), Texte.CENTRE);

        // Bouton Quitter
        Rectangle btnQuitter = new Rectangle(Couleur.ROUGE, new Point(500, Plateau.TAILLE_CASE * 3), 150, 50, true);
        Texte txtQuitter = new Texte(Couleur.BLANC, "Quitter", new java.awt.Font("Arial", java.awt.Font.BOLD, 20),
            new Point(575, Plateau.TAILLE_CASE * 3 + 15), Texte.CENTRE);

        plateau.getFenetre().ajouter(victoire);
        plateau.getFenetre().ajouter(btnRejouer);
        plateau.getFenetre().ajouter(txtRejouer);
        plateau.getFenetre().ajouter(btnQuitter);
        plateau.getFenetre().ajouter(txtQuitter);
        plateau.getFenetre().rafraichir();

        // Attente clic sur bouton
        while (true) {
            if (souris.getClicGauche()) {
                Point pos = souris.getPosition();
                // Rejouer
                if (pos.getX() >= 200 && pos.getX() <= 350 && pos.getY() >= Plateau.TAILLE_CASE * 3 &&
                    pos.getY() <= Plateau.TAILLE_CASE * 3 + 50) {
                    Plateau newPlateau = new Plateau();
                    new GameBoard(newPlateau);
                    return;
                }
                // Quitter
                if (pos.getX() >= 500 && pos.getX() <= 650 && pos.getY() >= Plateau.TAILLE_CASE * 3 &&
                    pos.getY() <= Plateau.TAILLE_CASE * 3 + 50) {
                    System.exit(0);
                }
                souris.reinitialisation();
            }
            try { Thread.sleep(80); } catch (InterruptedException e) {}
        }
    }
}
