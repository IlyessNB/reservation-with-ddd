package model.resource;

import java.util.Objects;

public class ResourceId {
    private final String value;

    public ResourceId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceId resourceId = (ResourceId) o;
        return Objects.equals(value, resourceId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}