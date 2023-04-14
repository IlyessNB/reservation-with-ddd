package infrastructure.factories;

import model.common.IdGenerator;
import model.resource.Resource;
import model.resource.ResourceId;
import model.resource.ResourceTimetables;

public class ResourceFactory {
    private final IdGenerator idGenerator;

    public ResourceFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }


    public Resource create(String name, ResourceTimetables resourceTimetables) {
        ResourceId resourceId = new ResourceId(idGenerator.generate());
        return Resource.of(resourceId, name, resourceTimetables);
    }
}
