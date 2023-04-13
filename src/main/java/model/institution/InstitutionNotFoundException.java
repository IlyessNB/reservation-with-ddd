package model.institution;

public class InstitutionNotFoundException extends Exception {
    public InstitutionNotFoundException(String etablissementId) {
        super("Etablissement avec l'id " + etablissementId + " non trouvé.");
    }
}