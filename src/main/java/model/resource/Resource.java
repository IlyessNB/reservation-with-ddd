package model.resource;

import model.reservation.TimeRange;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Resource {
    private final ResourceId resourceId;
    private final String institutionId;
    private final String name;
    private final List<String> equipments;
    private final Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek;

    private Resource(ResourceId resourceId, String institutionId, String name, List<String> equipments, Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek) {
        this.resourceId = resourceId;
        this.institutionId = institutionId;
        this.name = name;
        this.equipments = equipments;
        this.timetablesByDayOfWeek = timetablesByDayOfWeek;
    }

    public static Resource of(ResourceId resourceId, String institutionId, String name, List<String> equipments, Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek) {
        return new Resource(resourceId, institutionId, name, equipments, timetablesByDayOfWeek);
    }

    public void verifyIsOpen(TimeRange timeRange) throws ResourceIsClosedException {
        DayOfWeek dayOfWeek = timeRange.getDate().getDayOfWeek();
        List<Timetable> timetables = timetablesByDayOfWeek.get(dayOfWeek);
        isClosed(timeRange, timetables);
        // TODO extract, use time range
        for (Timetable timetable : timetables) {
            if (timetable == null || !isBetween(timetable.getOpeningTime(), timetable.getClosingTime(), timeRange.getStartTime(), timeRange.getEndTime())) {
                throw new ResourceIsClosedException(resourceId, timeRange);
            }
        }
    }

    private void isClosed(TimeRange timeRange, List<Timetable> timetables) throws ResourceIsClosedException {
        if (timetables == null || timetables.isEmpty()) {
            throw new ResourceIsClosedException(resourceId, timeRange);
        }
    }

    private boolean isBetween(LocalTime openingTime, LocalTime closingTime, LocalTime reservationStartTime, LocalTime reservationEndTime) {
        return reservationStartTime.isAfter(openingTime) && reservationEndTime.isBefore(closingTime);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }

}
