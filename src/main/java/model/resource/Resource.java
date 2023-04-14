package model.resource;

import model.reservation.DateWithTimeRange;

import java.time.DayOfWeek;

public class Resource {
    private final ResourceId resourceId;
    private final String name;
    private final ResourceTimetables timetables;

    private Resource(ResourceId resourceId, String name, ResourceTimetables timetables) {
        this.resourceId = resourceId;
        this.name = name;
        this.timetables = timetables;
    }

    public static Resource of(ResourceId resourceId, String name, ResourceTimetables timetables) {
        return new Resource(resourceId, name, timetables);
    }

    public void verifyIsOpen(DateWithTimeRange timeRange) throws ResourceIsClosedException {
        DayOfWeek dayOfWeek = timeRange.getDate().getDayOfWeek();
        this.timetables.checkOpened(dayOfWeek, timeRange, resourceId);
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }

}
