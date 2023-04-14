package model.resource;

import model.reservation.DateWithTimeRange;

import java.time.DayOfWeek;
import java.util.List;

public class Resource {
    private final ResourceId resourceId;
    private final String institutionId;
    private final String name;
    private final List<String> equipments;
    private final ResourceTimetables timetables;

    private Resource(ResourceId resourceId, String institutionId, String name, List<String> equipments, ResourceTimetables timetables) {
        this.resourceId = resourceId;
        this.institutionId = institutionId;
        this.name = name;
        this.equipments = equipments;
        this.timetables = timetables;
    }

    public static Resource of(ResourceId resourceId, String institutionId, String name, List<String> equipments, ResourceTimetables timetables) {
        return new Resource(resourceId, institutionId, name, equipments, timetables);
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
