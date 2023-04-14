package model.resource;


import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface ResourceRepository {
    Resource create(String institutionId, String name, List<String> equipments, Map<DayOfWeek, List<Timetable>> timetablesByDayOfWeek);
    Resource findById(ResourceId id) throws ResourceNotFoundException;
    void add(Resource resource);
}
