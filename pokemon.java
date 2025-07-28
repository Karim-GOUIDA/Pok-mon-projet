import MG2D.*;
import MG2D.geometrie.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pokemon {
    private String nom;
    private int numeroPokedex;
    private int type1;
    private int type2;
    private int attaque;
    private int defense;
    private int pvMax;
    private int pv;
    private int vitesse;
    private int x, y;
    private int joueur;
    private Texture image;

    // Default constructor (Missingno)
    public Pokemon() {
        this.numeroPokedex = 0;
        this.nom = "Missingno";
        this.type1 = Type.NORMAL;
        this.type2 = Type.SANS;
        this.attaque = 33;
        this.defense = 0;
        this.pvMax = 33;
        this.pv = pvMax;
        this.vitesse = 29;
        this.joueur = 0;
        this.x = 0;
        this.y = 0;
        loadImage();
    }

    // Full constructor
    public Pokemon(int numeroPokedex, String nom, int type1, int type2, int attaque, int defense, int pvMax, int vitesse, int joueur, int x, int y) {
        this.numeroPokedex = numeroPokedex;
        this.nom = nom;
        this.type1 = type1;
        this.type2 = type2;
        this.attaque = attaque;
        this.defense = defense;
        this.pvMax = pvMax;
        this.pv = pvMax;
        this.vitesse = vitesse;
        this.joueur = joueur;
        this.x = x;
        this.y = y;
        loadImage();
    }

    // Simplified constructor using Pokédex CSV
    public Pokemon(int numeroPokedex, int joueur, int x, int y) {
        this.numeroPokedex = numeroPokedex;
        this.joueur = joueur;
        this.x = x;
        this.y = y;
        loadFromPokedex();
        loadImage();
    }

    private void loadImage() {
        String path = numeroPokedex == 0 ? "images/missingno.png" : "images/" + numeroPokedex + ".png";
        Point position = new Point(x * 80, y * 80);
        try {
            image = new Texture(path, position, 80, 80);
        } catch (Exception e) {
            System.out.println("Erreur chargement image : " + (numeroPokedex == 0 ? "Missingno" : numeroPokedex));
        }
    }

    private void loadFromPokedex() {
        try (BufferedReader br = new BufferedReader(new FileReader("pokedex_gen1.csv"))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (Integer.parseInt(data[0]) == numeroPokedex) {
                    this.nom = data[1]; // Load name from CSV
                    this.type1 = Type.getIndiceType(data[2]);
                    this.type2 = data[3].isEmpty() ? Type.SANS : Type.getIndiceType(data[3]);
                    this.pvMax = Integer.parseInt(data[4]);
                    this.pv = pvMax;
                    this.attaque = Integer.parseInt(data[5]);
                    this.defense = Integer.parseInt(data[6]);
                    this.vitesse = Integer.parseInt(data[7]);
                    break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erreur lecture pokedex : " + numeroPokedex);
            this.nom = "Unknown";
            this.type1 = Type.NORMAL;
            this.type2 = Type.SANS;
            this.pvMax = 50;
            this.pv = pvMax;
            this.attaque = 50;
            this.defense = 50;
            this.vitesse = 50;
        }
    }

    public void draw(Fenetre f) {
        if (image != null) {
            image.setA(new Point(x * 80, y * 80));
            f.ajouter(image);
        }
    }

    public String getNom() { return nom; }
    public int getNumeroPokedex() { return numeroPokedex; }
    public int getType1() { return type1; }
    public int getType2() { return type2; }
    public int getAttaque() { return attaque; }
    public int getDefense() { return defense; }
    public int getPv() { return pv; }
    public int getPvMax() { return pvMax; }
    public int getVitesse() { return vitesse; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getJoueur() { return joueur; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        if (image != null) {
            image.setA(new Point(x * 80, y * 80));
        }
    }

    public void setPv(int pv) {
        this.pv = Math.max(0, Math.min(pv, this.pvMax));
    }

    public boolean estMort() {
        return this.pv <= 0;
    }

    public void attaquer(Pokemon cible) {
        double effectiveness = Type.getEfficacite(this.type1, cible.getType1());
        if (cible.getType2() != Type.SANS) {
            effectiveness *= Type.getEfficacite(this.type1, cible.getType2());
        }
        int degats = (int) (this.attaque * effectiveness - cible.getDefense());
        if (degats < 1) degats = 1;
        cible.setPv(cible.getPv() - degats);
    }

    public void combat(Pokemon cible) {
        if (this.vitesse >= cible.getVitesse()) {
            this.attaquer(cible);
            if (!cible.estMort()) {
                cible.attaquer(this);
            }
        } else {
            cible.attaquer(this);
            if (!this.estMort()) {
                this.attaquer(cible);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pokemon)) return false;
        Pokemon other = (Pokemon) obj;
        return this.numeroPokedex == other.numeroPokedex &&
               this.nom.equals(other.nom) &&
               this.type1 == other.type1 &&
               this.type2 == other.type2 &&
               this.attaque == other.attaque &&
               this.defense == other.defense &&
               this.pvMax == other.pvMax &&
               this.vitesse == other.vitesse &&
               this.x == other.x &&
               this.y == other.y &&
               this.joueur == other.joueur;
    }

    @Override
    public String toString() {
        String specie = Type.getEspece(numeroPokedex);
        return "Pokemon{" +
               "nom='" + nom + '\'' +
               ", espèce='" + specie + '\'' +
               ", numeroPokedex=" + numeroPokedex +
               ", type1=" + Type.getNomType(type1) +
               ", type2=" + (type2 == Type.SANS ? "none" : Type.getNomType(type2)) +
               ", pv=" + pv + "/" + pvMax +
               ", attaque=" + attaque +
               ", defense=" + defense +
               ", vitesse=" + vitesse +
               ", joueur=" + joueur +
               ", position=(" + x + "," + y + ")" +
               '}';
    }
}