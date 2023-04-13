package model;

public class EtablissementNonTrouveException extends Exception {
    public EtablissementNonTrouveException(String etablissementId) {
        super("Etablissement avec l'id " + etablissementId + " non trouvé.");
    }
}