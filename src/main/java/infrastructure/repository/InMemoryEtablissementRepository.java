package infrastructure.repository;

import model.etablissement.Etablissement;
import model.etablissement.EtablissementNonTrouveException;
import model.repositories.EtablissementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryEtablissementRepository implements EtablissementRepository {
    List<Etablissement> etablissements;

    public InMemoryEtablissementRepository() {
        this.etablissements = new ArrayList<>();
    }

    @Override
    public Etablissement findById(String id) throws EtablissementNonTrouveException {
        Etablissement etablissementFound = etablissements.stream().filter(etablissement -> etablissement.getId().equals(id)).collect(Collectors.toList()).get(0);

        if(etablissementFound == null) {
            throw new EtablissementNonTrouveException(id);
        } else {
            return etablissementFound;
        }
    }

    @Override
    public void add(Etablissement etablissement) {
        etablissements.add(etablissement);
    }
}
