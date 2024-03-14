package com.tpagenda.tpagenda.Services;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Evenement;
import com.tpagenda.tpagenda.Repository.AgendaRepository;
import com.tpagenda.tpagenda.Repository.EvenementRepository;

@Service
public class EvenementServiceImpl implements EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    @Override
    public void ajouterEvenement(String nomEvenement, Date date, String startTime, String endTime, String label, String nom) {
        evenementRepository.save(new Evenement(nomEvenement, date, startTime, endTime, label, nom));
    }

    @Override
    public Iterable<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

@Override
public List<Evenement> getEvenementsByNomAgenda(String nomAgenda) {
    Agenda agenda = agendaRepository.findByNom(nomAgenda);
    if (agenda != null) {
        return evenementRepository.findByAgenda(agenda);
    } else {
        return Collections.emptyList();
    }
}


    @Override
    public Evenement getEvenementByNom(String nomEvenement) {
        return evenementRepository.findByNomEvenement(nomEvenement);
    }

}
