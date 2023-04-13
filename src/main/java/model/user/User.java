package model.user;

public class User {
    String utlisateurId;
    String nom;
    String prenom;
    String mail;

    public User(String utlisateurId, String nom, String prenom, String mail) {
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
