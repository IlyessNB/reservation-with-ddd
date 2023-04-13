package infrastructure.repository;

import model.repositories.UtilisateurRepository;
import model.utilisateur.Utilisateur;
import model.utilisateur.UtilisateurNonTrouveException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryUtilisateurRepository implements UtilisateurRepository {
    List<Utilisateur> utilisateurs;

    public InMemoryUtilisateurRepository() {
        utilisateurs = new ArrayList<>();
        utilisateurs.add(new Utilisateur("1", "Xia", "Louis", "louis.xia@gmail.com"));
    }

    @Override
    public Utilisateur findById(String id) throws UtilisateurNonTrouveException {
        Utilisateur utilisateurFound = utilisateurs.stream().filter(utilisateur -> utilisateur.getUtlisateurId().equals(id)).collect(Collectors.toList()).get(0);

        if (utilisateurFound == null) {
            throw new UtilisateurNonTrouveException(id);
        } else {
            return utilisateurFound;
        }
    }

    @Override
    public void create(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }
}
