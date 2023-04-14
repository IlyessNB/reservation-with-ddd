package infrastructure.repositories;


import model.common.IdGenerator;
import model.resource.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryResourceRepository implements ResourceRepository {
    List<Resource> resources;
    private final IdGenerator idGenerator;

    public InMemoryResourceRepository(IdGenerator idGenerator) {
        this.resources = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    @Override
    public Resource create(String institutionId, String name, List<String> equipments, ResourceTimetables resourceTimetables) {
        ResourceId resourceId = new ResourceId(idGenerator.generate());
        return Resource.of(resourceId, "1", "Salle de réunion de 10 personnes", new ArrayList<>(), resourceTimetables);
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
    public void add(Resource resource) {
        resources.add(resource);
    }
}
