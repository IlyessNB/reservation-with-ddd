package model.repositories;


import model.etablissement.ressource.Ressource;
import model.etablissement.ressource.RessourceNonTrouveeException;

public interface RessourceRepository {
    Ressource findById(String id) throws RessourceNonTrouveeException;
    void add(Ressource ressource);
}
