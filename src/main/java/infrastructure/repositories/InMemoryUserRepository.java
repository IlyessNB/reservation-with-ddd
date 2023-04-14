package infrastructure.repositories;

import model.user.User;
import model.user.UserId;
import model.user.UserNotFoundException;
import model.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    List<User> users;

    public InMemoryUserRepository() {
        users = new ArrayList<>();
    }

    @Override
    public User findById(UserId id) throws UserNotFoundException {
        User userFound = users.stream().filter(user -> user.getUserId().equals(id)).collect(Collectors.toList()).get(0);

        if (userFound == null) {
            throw new UserNotFoundException(id);
        } else {
            return userFound;
        }
    }

    @Override
    public void save(User user) {
        users.add(user);
    }
}
