package model.repositories;

import model.utilisateur.Utilisateur;
import model.utilisateur.UtilisateurNonTrouveException;

public interface UtilisateurRepository {
    Utilisateur findById(String id) throws UtilisateurNonTrouveException;

    void create(Utilisateur utilisateur);
}
