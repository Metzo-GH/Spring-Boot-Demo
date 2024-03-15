package com.tpagenda.tpagenda;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Evenement {

    @Id
    private String nomEvenement;
    private Date date;
    private String startTime;
    private String endTime;
    private String label;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "nom", insertable = false, updatable = false)
    private Agenda agenda;

    public Evenement() {
    }

    public Evenement(String nomEvenement, Date date, String startTime, String endTime, String label, String nom) {
        this.nomEvenement = nomEvenement;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.label = label;
        this.nom = nom;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
