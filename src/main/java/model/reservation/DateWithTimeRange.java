package model.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateWithTimeRange {
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;

    private DateWithTimeRange(LocalTime startTime, LocalTime endTime, LocalDate date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public static DateWithTimeRange of(LocalTime startTime, LocalTime endTime, LocalDate date) {
        return new DateWithTimeRange(startTime, endTime, date);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isSameDate(LocalDate date) {
        return this.date.equals(date);
    }

    private boolean startTimeIsDuring(LocalTime startTime, LocalTime endTime) {
        return this.startTime.isAfter(startTime) && this.startTime.isBefore(endTime);
    }

    private boolean endTimeIsDuring(LocalTime startTime, LocalTime endTime) {
        return this.endTime.isAfter(startTime) && this.endTime.isBefore(endTime);
    }

    private boolean startTimeIsBefore(LocalTime startTime) {
        return this.startTime.isBefore(startTime);
    }

    private boolean endTimeIsAfter(LocalTime endTime) {
        return this.endTime.isAfter(endTime);
    }

    private boolean isSameTimeRange(DateWithTimeRange timeRange) {
        return this.startTime.equals(timeRange.getStartTime()) && this.endTime.equals(timeRange.getEndTime());
    }

    public boolean isOverLapping(DateWithTimeRange timeRange) {
        LocalTime startTimeToCompare = timeRange.getStartTime();
        LocalTime endTimeToCompare = timeRange.getEndTime();
        return startTimeIsDuring(startTimeToCompare, endTimeToCompare)
                || endTimeIsDuring(startTimeToCompare, endTimeToCompare)
                || (startTimeIsBefore(startTimeToCompare) && endTimeIsAfter(endTimeToCompare))
                || isSameTimeRange(timeRange);
    }

    public void checkTimeCoherence() {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("La reservation n'est pas valide car la date de fin est avant la date de début");
        }
    }

    public void checkIsNotInPast() {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La reservation ne peut pas être dans le passé");
        }
    }
}
