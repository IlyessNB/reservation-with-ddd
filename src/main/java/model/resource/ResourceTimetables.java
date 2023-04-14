package model.resource;

import model.reservation.DateWithTimeRange;

import java.time.DayOfWeek;
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
            if (timetable == null || timetable.isClosedDuringTimeRange(timeRange)) {
                throw new ResourceIsClosedException(resourceId, timeRange);
            }
        }
    }
}
