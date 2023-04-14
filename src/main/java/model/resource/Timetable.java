package model.resource;

import java.time.LocalTime;

public class Timetable {
    private final LocalTime openingTime;
    private final LocalTime closingTime;

    public Timetable(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }
}
