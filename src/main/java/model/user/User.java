package model.user;

public class User {
    private final UserId userId;
    private final String lastName;
    private final String firstName;
    private final String email;

    private User(UserId userId, String lastName, String firstName, String email) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public static User of(UserId userId, String lastName, String firstName, String email) {
        return new User(userId, lastName, firstName, email);
    }


    public UserId getUserId() {
        return userId;
    }
}
