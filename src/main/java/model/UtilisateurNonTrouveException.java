package model;

public class UtilisateurNonTrouveException extends Exception {
    public UtilisateurNonTrouveException(String utilisateurId) {
        super("Utilisateur avec l'id " + utilisateurId + " non trouvé.");
    }
}
