package model.user;

public interface UserRepository {
    void exists(UserId id) throws UserNotFoundException;

    void save(User user);
}
