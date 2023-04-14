package infrastructure.repositories;

import model.institution.Institution;
import model.institution.InstitutionNotFoundException;
import model.institution.InstitutionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryInstitutionRepository implements InstitutionRepository {
    List<Institution> institutions;

    public InMemoryInstitutionRepository() {
        this.institutions = new ArrayList<>();
    }

    @Override
    public Institution findById(String id) throws InstitutionNotFoundException {
        Institution institutionFound = institutions.stream().filter(institution -> institution.getId().equals(id)).collect(Collectors.toList()).get(0);

        if(institutionFound == null) {
            throw new InstitutionNotFoundException(id);
        } else {
            return institutionFound;
        }
    }

    @Override
    public void add(Institution institution) {
        institutions.add(institution);
    }
}
