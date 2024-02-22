package com.example.demo.java.com.example.demo.ctrl;

public class Auteur {

    private long id;
    private String nom;
    private String prenom;

    public Auteur(long id, String nom, String prenom) {
        this.id=id;
        this.prenom=prenom;
        this.nom=nom;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
}
