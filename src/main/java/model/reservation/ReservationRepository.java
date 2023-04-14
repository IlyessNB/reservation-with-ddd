package model.reservation;

import model.resource.ResourceId;
import model.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {
    Reservation create(UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date);

    void add(Reservation reservation);

    List<Reservation> findByDate(ResourceId resourceId, LocalDate date);

}
