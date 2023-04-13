package model;

import java.util.List;

public class Ressource {
    String id;
    String etablissementId;
    String nom;
    List<String> equipements;

    public Ressource(String id, String etablissementId, String nom, List<String> equipements) {
        this.id = id;
        this.etablissementId = etablissementId;
        this.nom = nom;
        this.equipements = equipements;
    }


    public String getId() {
        return id;
    }

    public String getEtablissementId() {
        return etablissementId;
    }

    public String getNom() {
        return nom;
    }

    public List<String> getEquipements() {
        return equipements;
    }
}
