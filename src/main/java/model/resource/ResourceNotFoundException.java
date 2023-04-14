package model.resource;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(ResourceId resourceId) {
        super("Ressource avec l'id " + resourceId.getValue() + " non trouvée.");
    }
}
