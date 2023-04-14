package infrastructure.repositories;

import model.common.IdGenerator;
import model.reservation.Reservation;
import model.reservation.ReservationId;
import model.reservation.ReservationRepository;
import model.reservation.TimeRange;
import model.resource.ResourceId;
import model.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryReservationRepository implements ReservationRepository {
    List<Reservation> reservations;
    private final IdGenerator idGenerator;

    public InMemoryReservationRepository(IdGenerator idGenerator) {
        this.reservations = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    @Override
    public Reservation create(UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) {
        ReservationId reservationId = new ReservationId(idGenerator.generate());
        return Reservation.of(reservationId, userId, resourceId, TimeRange.of(startTime, endTime, date));
    }

    @Override
    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findByTimeRange(ResourceId resourceId, TimeRange timeRange) {
        return reservations
                .stream()
                .filter(reservation -> reservation.getResourceId().equals(resourceId))
                .filter(reservation -> timeRange.isSameDate(reservation.getTimeRange().getDate()))
                .filter(reservation -> timeRange.isOverLapping(reservation.getTimeRange()))
                .collect(Collectors.toList());
    }
}
