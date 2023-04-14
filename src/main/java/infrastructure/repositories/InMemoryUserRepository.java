package infrastructure.repositories;

import model.user.User;
import model.user.UserId;
import model.user.UserNotFoundException;
import model.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users;

    public InMemoryUserRepository() {
        users = new ArrayList<>();
    }

    @Override
    public void exists(UserId id) throws UserNotFoundException {
        List<User> userFound = users.stream().filter(user -> user.getUserId().equals(id)).collect(Collectors.toList());

        if (userFound.isEmpty()) {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public void save(User user) {
        users.add(user);
    }
}
