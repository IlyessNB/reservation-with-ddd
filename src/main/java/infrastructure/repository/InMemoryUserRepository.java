package infrastructure.repository;

import model.repositories.UserRepository;
import model.user.User;
import model.user.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    List<User> users;

    public InMemoryUserRepository() {
        users = new ArrayList<>();
        users.add(new User("1", "Xia", "Louis", "louis.xia@gmail.com"));
    }

    @Override
    public User findById(String id) throws UserNotFoundException {
        User userFound = users.stream().filter(user -> user.getUtlisateurId().equals(id)).collect(Collectors.toList()).get(0);

        if (userFound == null) {
            throw new UserNotFoundException(id);
        } else {
            return userFound;
        }
    }

    @Override
    public void create(User user) {
        users.add(user);
    }
}
