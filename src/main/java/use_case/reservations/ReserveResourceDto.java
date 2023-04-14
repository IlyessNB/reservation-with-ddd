package use_case.reservations;

import model.resource.ResourceId;
import model.user.UserId;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReserveResourceDto {
    UserId userId;
    ResourceId resourceId;
    LocalTime startTime;
    LocalTime endTime;
    LocalDate date;

    public ReserveResourceDto(UserId userId, ResourceId resourceId, LocalTime startTime, LocalTime endTime, LocalDate date) {
        this.userId = userId;
        this.resourceId = resourceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
}
