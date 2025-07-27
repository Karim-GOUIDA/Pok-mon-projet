
public class Type {
    public static final String[] nomsType = {
        "NORMAL", "FEU", "EAU", "PLANTE", "ELECTRIK", "GLACE", "COMBAT", "POISON",
        "SOL", "VOL", "PSY", "INSECTE", "ROCHE", "SPECTRE", "DRAGON", "SANS"
    };

    public static final int NORMAL = 0;
    public static final int FEU = 1;
    public static final int EAU = 2;
    public static final int PLANTE = 3;
    public static final int ELECTRIK = 4;
    public static final int GLACE = 5;
    public static final int COMBAT = 6;
    public static final int POISON = 7;
    public static final int SOL = 8;
    public static final int VOL = 9;
    public static final int PSY = 10;
    public static final int INSECTE = 11;
    public static final int ROCHE = 12;
    public static final int SPECTRE = 13;
    public static final int DRAGON = 14;
    public static final int SANS = 15;

    public static final double NEUTRE = 1.0;
    public static final double INEFFICACE = 0.0;
    public static final double PAS_EFFICACE = 0.5;
    public static final double SUPER_EFFICACE = 2.0;

    private static final double[][] efficacite = {
        {1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,0.5,0 ,1 },
        {1 ,0.5,0.5,2 ,1 ,2 ,1 ,1 ,1 ,1 ,1 ,2 ,0.5,1 ,0.5},
        {1 ,2 ,0.5,0.5,1 ,1 ,1 ,1 ,2 ,1 ,1 ,1 ,2 ,1 ,0.5},
        {1 ,0.5,2 ,0.5,1 ,1 ,1 ,0.5,2 ,0.5,1 ,0.5,2 ,1 ,0.5},
        {1 ,1 ,2 ,0.5,0.5,1 ,1 ,1 ,0 ,2 ,1 ,1 ,1 ,1 ,0.5},
        {1 ,1 ,0.5,2 ,1 ,0.5,1 ,1 ,2 ,2 ,1 ,1 ,1 ,1 ,2 },
        {2 ,1 ,1 ,1 ,1 ,2 ,1 ,0.5,1 ,0.5,0.5,0.5,2 ,0 ,1 },
        {1 ,1 ,1 ,2 ,1 ,1 ,1 ,0.5,0.5,1 ,1 ,2 ,0.5,0.5,1 },
        {1 ,2 ,1 ,0.5,2 ,1 ,1 ,2 ,1 ,0 ,1 ,0.5,2 ,1 ,1 },
        {1 ,1 ,1 ,2 ,0.5,1 ,2 ,1 ,1 ,1 ,1 ,2 ,0.5,1 ,1 },
        {1 ,1 ,1 ,1 ,1 ,1 ,2 ,2 ,1 ,1 ,0.5,1 ,1 ,1 ,1 },
        {1 ,0.5,1 ,2 ,1 ,1 ,0.5,2 ,1 ,0.5,2 ,1 ,1 ,0.5,1 },
        {1 ,2 ,1 ,1 ,1 ,2 ,0.5,1 ,0.5,2 ,1 ,2 ,1 ,1 ,1 },
        {0 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,0 ,1 ,1 ,2 ,1 },
        {1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,2 }
    };

    public static final String[] espece = new String[151];

    public static String getEspece(int numPokedex) {
        if (numPokedex >= 1 && numPokedex <= espece.length) {
            return espece[numPokedex - 1];
        } else {
            return "Inconnu";
        }
    }

    public static String getNomType(int type) {
        if (type >= 0 && type < nomsType.length) {
            return nomsType[type].toLowerCase();
        } else {
            return "inconnu";
        }
    }

    public static double getEfficacite(int typeAtt, int typeDef) {
        if (typeAtt >= 0 && typeAtt < efficacite.length && typeDef >= 0 && typeDef < efficacite[0].length) {
            return efficacite[typeAtt][typeDef];
        } else {
            return NEUTRE;
        }
    }

    public static int getIndiceType(String type) {
        for (int i = 0; i < nomsType.length; i++) {
            if (nomsType[i].equalsIgnoreCase(type)) {
                return i;
            }
        }
        return -1;
    }
}
