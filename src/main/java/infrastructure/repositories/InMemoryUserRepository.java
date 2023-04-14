package infrastructure.repositories;

import model.common.IdGenerator;
import model.user.User;
import model.user.UserId;
import model.user.UserNotFoundException;
import model.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    List<User> users;


    private final IdGenerator idGenerator;

    public InMemoryUserRepository(IdGenerator idGenerator) {
        users = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    @Override
    public User create(String lastName, String firstName, String email) {
        UserId userId = new UserId(idGenerator.generate());
        return User.of(userId, lastName, firstName, email);
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
    public void add(User user) {
        users.add(user);
    }
}
