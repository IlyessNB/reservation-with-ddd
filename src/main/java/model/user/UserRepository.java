package model.user;

public interface UserRepository {
    User findById(UserId id) throws UserNotFoundException;

    void save(User user);
}
