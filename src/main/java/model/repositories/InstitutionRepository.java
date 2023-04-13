package model.repositories;

import model.institution.Institution;
import model.institution.InstitutionNotFoundException;

public interface InstitutionRepository {
    Institution findById(String id) throws InstitutionNotFoundException;
    void add(Institution institution);
}
