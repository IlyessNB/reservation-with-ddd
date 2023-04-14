package model.reservation;

import model.resource.ResourceId;
import model.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {
    Reservation create(UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date);

    void save(Reservation reservation);

    List<Reservation> findByTimeRange(ResourceId resourceId, DateWithTimeRange timeRange);
}
