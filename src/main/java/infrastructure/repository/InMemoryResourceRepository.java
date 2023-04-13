package infrastructure.repository;


import model.institution.resource.Resource;
import model.institution.resource.ResourceNotFoundException;
import model.repositories.ResourceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryResourceRepository implements ResourceRepository {

    List<Resource> resources;

    public InMemoryResourceRepository() {
        this.resources = new ArrayList<>();
    }

    @Override
    public Resource findById(String id) throws ResourceNotFoundException {
        Resource resourceFound = resources.stream().filter(resource -> resource.getId().equals(id)).collect(Collectors.toList()).get(0);

        if (resourceFound == null) {
            throw new ResourceNotFoundException(id);
        } else {
            return resourceFound;
        }
    }

    @Override
    public void add(Resource resource) {
        resources.add(resource);
    }
}
