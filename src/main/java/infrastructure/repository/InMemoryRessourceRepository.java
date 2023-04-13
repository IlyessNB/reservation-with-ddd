package infrastructure.repository;


import model.etablissement.ressource.Ressource;
import model.etablissement.ressource.RessourceNonTrouveeException;
import model.repositories.RessourceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryRessourceRepository implements RessourceRepository {

    List<Ressource> ressources;

    public InMemoryRessourceRepository() {
        this.ressources = new ArrayList<>();
    }

    @Override
    public Ressource findById(String id) throws RessourceNonTrouveeException {
        Ressource ressourceFound = ressources.stream().filter(ressource -> ressource.getId().equals(id)).collect(Collectors.toList()).get(0);

        if(ressourceFound == null) {
            throw new RessourceNonTrouveeException(id);
        } else {
            return ressourceFound;
        }
}

    @Override
    public void add(Ressource ressource) {
        ressources.add(ressource);
    }
}
