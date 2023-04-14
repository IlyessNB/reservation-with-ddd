package model.resource;

import model.reservation.DateWithTimeRange;

import java.time.LocalTime;

public class Timetable {
    private final LocalTime openingTime;
    private final LocalTime closingTime;

    public Timetable(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isClosedDuringTimeRange(DateWithTimeRange timeRange) {
        return !isBetween(timeRange.getStartTime(), timeRange.getEndTime());
    }

    private boolean isBetween(LocalTime reservationStartTime, LocalTime reservationEndTime) {
        return (reservationStartTime.isAfter(openingTime) || reservationStartTime.equals(openingTime)) && (reservationEndTime.isBefore(closingTime) || reservationEndTime.equals(closingTime));
    }
}
