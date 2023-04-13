package model.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String userId) {
        super("Utilisateur avec l'id " + userId + " non trouvé.");
    }
}
