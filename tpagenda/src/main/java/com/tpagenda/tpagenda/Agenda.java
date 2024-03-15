package com.tpagenda.tpagenda;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Agenda {

    
    private String email;
    @Id
    private String nom;

    @ManyToOne
    @JoinColumn(name = "email", insertable = false, updatable = false)
    private Personne personne;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL)
    private List<Evenement> Evenement;

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
