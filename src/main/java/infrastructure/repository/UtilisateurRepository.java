package infrastructure.repository;

import model.Utilisateur;
import model.UtilisateurNonTrouveException;

import java.util.List;
import java.util.stream.Collectors;

public class UtilisateurRepository {
    List<Utilisateur> utilisateurs = List.of(new Utilisateur("1", "Xia", "Louis", "louis.xia@gmail.com"));

    public UtilisateurRepository() {

    }

    public Utilisateur findById(String id) throws UtilisateurNonTrouveException {
        Utilisateur utilisateurFound = utilisateurs.stream().filter(utilisateur -> utilisateur.getUtlisateurId().equals(id)).collect(Collectors.toList()).get(0);

         if(utilisateurFound == null) {
             return utilisateurFound;
         } else {
             throw new UtilisateurNonTrouveException(id);
         }
    }
}
