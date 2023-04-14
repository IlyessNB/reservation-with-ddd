package model.resource;


import java.util.List;

public interface ResourceRepository {
    Resource create(String institutionId, String name, List<String> equipments, ResourceTimetables resourceTimetables);
    Resource findById(ResourceId id) throws ResourceNotFoundException;
    void add(Resource resource);
}
