package model.repositories;


import model.institution.resource.Resource;
import model.institution.resource.ResourceNotFoundException;

public interface ResourceRepository {
    Resource findById(String id) throws ResourceNotFoundException;
    void add(Resource resource);
}
