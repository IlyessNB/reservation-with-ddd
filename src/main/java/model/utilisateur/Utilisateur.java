package model.utilisateur;

public class Utilisateur {
    String utlisateurId;
    String nom;
    String prenom;
    String mail;

    public Utilisateur(String utlisateurId, String nom, String prenom, String mail) {
        this.utlisateurId = utlisateurId;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }


    public String getUtlisateurId() {
        return utlisateurId;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }
}
