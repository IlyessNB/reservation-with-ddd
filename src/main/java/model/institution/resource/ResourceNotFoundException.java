package model.institution.resource;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String resourceId) {
        super("Ressource avec l'id " + resourceId + " non trouvée.");
    }
}
