package model.resource;

import model.reservation.DateWithTimeRange;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ResourceTimetables {
    private final Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek;

    public ResourceTimetables(Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek) {
        this.timetablesByDayOfWeek = timetablesByDayOfWeek;
    }

    public void checkOpened(DayOfWeek dayOfWeek, DateWithTimeRange timeRange, ResourceId resourceId) throws ResourceIsClosedException {
        List<Timetable> timeTableOfTheDay = timetablesByDayOfWeek.get(dayOfWeek);
        checkClosedOnDay(timeTableOfTheDay, resourceId, timeRange);
        checkClosedDuringTimeRange(timeTableOfTheDay, resourceId, timeRange);
    }

    private void checkClosedOnDay(List<Timetable> timeTableOfTheDay, ResourceId resourceId, DateWithTimeRange timeRange) throws ResourceIsClosedException {
        if (timeTableOfTheDay == null || timeTableOfTheDay.isEmpty()) {
            throw new ResourceIsClosedException(resourceId, timeRange);
        }
    }

    private void checkClosedDuringTimeRange(List<Timetable> timeTableOfTheDay, ResourceId resourceId, DateWithTimeRange timeRange) throws ResourceIsClosedException {
        for (Timetable timetable : timeTableOfTheDay) {
            if (timetable == null || !isBetween(timetable.getOpeningTime(), timetable.getClosingTime(), timeRange.getStartTime(), timeRange.getEndTime())) {
                throw new ResourceIsClosedException(resourceId, timeRange);
            }
        }
    }

    private boolean isBetween(LocalTime openingTime, LocalTime closingTime, LocalTime reservationStartTime, LocalTime reservationEndTime) {
        return (reservationStartTime.isAfter(openingTime) || reservationStartTime.equals(openingTime)) && (reservationEndTime.isBefore(closingTime) || reservationEndTime.equals(closingTime));
    }
}
