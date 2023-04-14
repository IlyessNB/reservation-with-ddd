package model.resource;


public interface ResourceRepository {
    Resource findById(ResourceId id) throws ResourceNotFoundException;

    void save(Resource resource);
}
