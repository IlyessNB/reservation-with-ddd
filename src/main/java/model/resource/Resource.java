package model.resource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Resource {
    ResourceId resourceId;
    String institutionId;
    String name;
    List<String> equipments;
    Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek;

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

    public void verifyIsOpen(LocalTime startTime, LocalTime endTime, LocalDate date) throws ResourceIsClosedException {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Timetable> timetables = timetablesByDayOfWeek.get(dayOfWeek);
        if (timetables == null || timetables.isEmpty()) {
            throw new ResourceIsClosedException(resourceId, date, startTime, endTime);
        }
        for (Timetable timetable : timetables) {
            if (timetable == null || !isBetween(timetable.getOpeningTime(), timetable.getClosingTime(), startTime, endTime)) {
                throw new ResourceIsClosedException(resourceId, date, startTime, endTime);
            }
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
