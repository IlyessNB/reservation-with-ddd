package model.repositories;

import model.user.User;
import model.user.UserNotFoundException;

public interface UserRepository {
    User findById(String id) throws UserNotFoundException;

    void create(User user);
}
