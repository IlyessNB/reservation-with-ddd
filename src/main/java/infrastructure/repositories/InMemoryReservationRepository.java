package infrastructure.repositories;

import model.reservation.DateWithTimeRange;
import model.reservation.Reservation;
import model.reservation.ReservationRepository;
import model.resource.ResourceId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryReservationRepository implements ReservationRepository {
    List<Reservation> reservations;

    public InMemoryReservationRepository() {
        this.reservations = new ArrayList<>();
    }

    @Override
    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findByDateWithTimeRange(ResourceId resourceId, DateWithTimeRange timeRange) {
        return reservations
                .stream()
                .filter(reservation -> reservation.getResourceId().equals(resourceId))
                .filter(reservation -> timeRange.isSameDate(reservation.getDateWithTimeRange().getDate()))
                .filter(reservation -> timeRange.isOverLapping(reservation.getDateWithTimeRange()))
                .collect(Collectors.toList());
    }
}
