package infrastructure.factories;

import model.common.IdGenerator;
import model.user.User;
import model.user.UserId;

public class UserFactory {
    private final IdGenerator idGenerator;

    public UserFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public User create(String lastName, String firstName, String email) {
        UserId userId = new UserId(idGenerator.generate());
        return User.of(userId, lastName, firstName, email);
    }
}
