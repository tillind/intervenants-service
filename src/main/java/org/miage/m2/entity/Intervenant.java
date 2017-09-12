package org.miage.m2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Intervenant implements Serializable {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private String commune;
    private String codepostal;

    public Intervenant() {
    }

    public Intervenant(String nom, String prenom, String commune, String codepostal) {
        this.nom = nom;
        this.prenom = prenom;
        this.commune = commune;
        this.codepostal = codepostal;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }
    
    
}
