package model.institution.resource;

import model.institution.Timetable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Resource {
    String id;
    String institutionId;
    String name;
    List<String> equipments;

    Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek;

    public Resource(String id, String institutionId, String name, List<String> equipments, Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek) {
        this.id = id;
        this.institutionId = institutionId;
        this.name = name;
        this.equipments = equipments;
        this.timetablesByDayOfWeek = timetablesByDayOfWeek;
    }

    public void isOpen(LocalTime startTime, LocalTime endTime, LocalDate date) throws ResourceIsClosedException {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Timetable> timetables = timetablesByDayOfWeek.get(dayOfWeek);
        if (timetables == null || timetables.isEmpty()) {
            throw new ResourceIsClosedException(id, date, startTime, endTime);
        }
        for (Timetable timetable : timetables) {
            if (timetable == null || !isBetween(timetable.getOpeningTime(), timetable.getClosingTime(), startTime, endTime)) {
                throw new ResourceIsClosedException(id, date, startTime, endTime);
            }
        }
    }

    private boolean isBetween(LocalTime openingTime, LocalTime closingTime, LocalTime reservationStartTime, LocalTime reservationEndTime) {
        return reservationStartTime.isAfter(openingTime) && reservationEndTime.isBefore(closingTime);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
