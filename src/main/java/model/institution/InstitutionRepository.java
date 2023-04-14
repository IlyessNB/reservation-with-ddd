package model.institution;

public interface InstitutionRepository {
    Institution findById(String id) throws InstitutionNotFoundException;
    void add(Institution institution);
}
