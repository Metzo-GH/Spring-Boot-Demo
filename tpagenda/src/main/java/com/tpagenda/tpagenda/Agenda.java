package com.tpagenda.tpagenda;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Agenda {

    @Id
    private String email;
    private String nom;

    @ManyToOne
    @JoinColumn(name = "email", insertable = false, updatable = false)
    private Personne personne;

    public Agenda() {
    }

    public Agenda(String email, String nom) {
        this.email = email;
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
}
