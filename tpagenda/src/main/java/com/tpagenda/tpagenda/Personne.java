package com.tpagenda.tpagenda;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Personne {

    @Id
    private String email;
    private String password;
    private String prenom;
    private String nom;

    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL)
    private List<Agenda> agendas;

    public Personne() {
    }

    public Personne(String email, String password, String prenom, String nom) {
        this.email = email;
        this.password = password;
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }
}
