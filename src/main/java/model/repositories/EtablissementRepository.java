package model.repositories;

import model.etablissement.Etablissement;
import model.etablissement.EtablissementNonTrouveException;

public interface EtablissementRepository {
    Etablissement findById(String id) throws EtablissementNonTrouveException;
    void add(Etablissement etablissement);
}
