package com.tpagenda.tpagenda.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Evenement;
import com.tpagenda.tpagenda.Repository.EvenementRepository;

@Service
public class EvenementServiceImpl implements EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Override
    public void ajouterEvenement(String nomEvenement, Date date, String startTime, String endTime, String label) {
        evenementRepository.save(new Evenement(nomEvenement, date, startTime, endTime, label));
    }

    @Override
    public Iterable<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    @Override
    public List<Evenement> getEvenementsByNomAgenda(String nomAgenda) {
        return evenementRepository.findByNomEvenement(nomAgenda);
    }

}
