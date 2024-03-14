package com.tpagenda.tpagenda.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Personne;
import com.tpagenda.tpagenda.Repository.AgendaRepository;
import com.tpagenda.tpagenda.Repository.PersonneRepository;

@Service
public class AgendaServicesImpl implements AgendaServices {
    @Autowired
    private AgendaRepository agendaRepository;

    @Override
    public Iterable<Agenda> getAllAgenda() {
        return agendaRepository.findAll();
    }

    @Override
    public void ajoutAgenda(String email ,String nom) {
        agendaRepository.save(new Agenda(email, nom));
    }

     @Autowired
    private PersonneRepository personneRepository;
    
    public List<Agenda> getAgendasByEmail(String email) {
        Personne personne = personneRepository.findByEmail(email);
        

        if(personne != null) {
            return agendaRepository.findByPersonne(personne);
        } else {
            return null;
        }
    }
}
