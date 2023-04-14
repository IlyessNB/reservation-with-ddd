package use_case.institution;

import model.user.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {
    @Test
    void test_user_id_hash() {
        UserId userId = new UserId("123");
        UserId userId2 = new UserId("123");
        Assertions.assertEquals(userId.hashCode(), userId2.hashCode());
    }
}
