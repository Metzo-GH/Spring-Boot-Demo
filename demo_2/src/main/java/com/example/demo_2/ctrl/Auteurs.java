package com.example.demo_2.ctrl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Auteurs {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;

    public Auteurs(String nom, String prenom) {
        this.prenom=prenom;
        this.nom=nom;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
}