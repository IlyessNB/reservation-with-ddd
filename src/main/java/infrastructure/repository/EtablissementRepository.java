package infrastructure.repository;

import model.Etablissement;
import model.Etablissement;
import model.EtablissementNonTrouveException;

import java.util.List;
import java.util.stream.Collectors;

public class EtablissementRepository {
    List<Etablissement> etablissements = List.of();

    public Etablissement findById(String id) throws EtablissementNonTrouveException {
        Etablissement etablissementFound = etablissements.stream().filter(etablissement -> etablissement.getId().equals(id)).collect(Collectors.toList()).get(0);

        if(etablissementFound == null) {
            return etablissementFound;
        } else {
            throw new EtablissementNonTrouveException(id);
        }
    }
}
