package infrastructure.repositories;


import model.resource.Resource;
import model.resource.ResourceId;
import model.resource.ResourceNotFoundException;
import model.resource.ResourceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryResourceRepository implements ResourceRepository {
    List<Resource> resources;
    public InMemoryResourceRepository() {
        this.resources = new ArrayList<>();
    }

    @Override
    public Resource findById(ResourceId id) throws ResourceNotFoundException {
        List<Resource> resourcesFound = resources.stream().filter(resource -> resource.getResourceId().equals(id)).collect(Collectors.toList());

        if (resourcesFound.isEmpty()) {
            throw new ResourceNotFoundException(id);
        } else {
            return resourcesFound.get(0);
        }
    }

    @Override
    public void save(Resource resource) {
        resources.add(resource);
    }
}
