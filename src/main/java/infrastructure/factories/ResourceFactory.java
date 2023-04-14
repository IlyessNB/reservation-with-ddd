package infrastructure.factories;

import model.common.IdGenerator;
import model.resource.Resource;
import model.resource.ResourceId;
import model.resource.ResourceTimetables;

import java.util.List;

public class ResourceFactory {
    private final IdGenerator idGenerator;

    public ResourceFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }


    public Resource create(String institutionId, String name, List<String> equipments, ResourceTimetables resourceTimetables) {
        ResourceId resourceId = new ResourceId(idGenerator.generate());
        return Resource.of(resourceId, institutionId, name, equipments, resourceTimetables);
    }
}
