package infrastructure.repository;


import model.Ressource;
import model.RessourceNonTrouveeException;

import java.util.List;
import java.util.stream.Collectors;

public class RessourceRepository {

    List<Ressource> ressources = List.of();

    public Ressource findById(String id) throws RessourceNonTrouveeException {
        Ressource ressourceFound = ressources.stream().filter(ressource -> ressource.getId().equals(id)).collect(Collectors.toList()).get(0);

        if(ressourceFound == null) {
            return ressourceFound;
        } else {
            throw new RessourceNonTrouveeException(id);
        }
}}
