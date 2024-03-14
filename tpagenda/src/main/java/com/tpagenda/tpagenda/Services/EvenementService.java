package com.tpagenda.tpagenda.Services;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Evenement;

@Service
public interface EvenementService {
    void ajouterEvenement(String nomEvenement, Date date, String startTime, String endTime, String label, String nom);
    Iterable<Evenement> getAllEvenements();
    List<Evenement> getEvenementsByNomAgenda(String nomAgenda);
    Evenement getEvenementByNom(String nomEvenement);
}
