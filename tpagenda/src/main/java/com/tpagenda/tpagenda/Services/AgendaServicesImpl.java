package com.tpagenda.tpagenda.Services;

import org.springframework.beans.factory.annotation.Autowired;

import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Repository.AgendaRepository;

public class AgendaServicesImpl implements AgendaServices {
    @Autowired
    private AgendaRepository agendaRepository;

    @Override
    public Iterable<Agenda> getAllAgenda() {
        return agendaRepository.findAll();
    }

    @Override
    public void ajoutAgenda(String nom) {
        agendaRepository.save(new Agenda(nom));
    }
}
