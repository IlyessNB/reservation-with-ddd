package model;

public class RessourceNonTrouveeException extends Exception {
    public RessourceNonTrouveeException(String ressourceId) {
        super("Ressource avec l'id " + ressourceId + " non trouvée.");
    }
}
