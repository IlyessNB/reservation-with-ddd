package infrastructure.common;

import model.common.IdGenerator;

public class UUIDGenerator implements IdGenerator {
    @Override
    public String generate() {
        return java.util.UUID.randomUUID().toString();
    }
}
