package model.user;

public interface UserRepository {
    User create(String lastName, String firstName, String email);

    User findById(UserId id) throws UserNotFoundException;

    void save(User user);
}
