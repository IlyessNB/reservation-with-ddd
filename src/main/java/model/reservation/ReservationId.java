package model.reservation;

import java.util.Objects;

public class ReservationId {
    private final String value;

    public ReservationId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationId reservationId = (ReservationId) o;
        return Objects.equals(value, reservationId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}