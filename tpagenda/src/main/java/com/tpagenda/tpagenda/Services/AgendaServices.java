package com.tpagenda.tpagenda.Services;

import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Agenda;

@Service
public interface AgendaServices {
    void ajoutAgenda(String nom);
    Iterable<Agenda> getAllAgenda();
}

