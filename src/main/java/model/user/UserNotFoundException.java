package model.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(UserId userId) {
        super("Utilisateur avec l'id " + userId.getValue() + " non trouvé.");
    }
}
