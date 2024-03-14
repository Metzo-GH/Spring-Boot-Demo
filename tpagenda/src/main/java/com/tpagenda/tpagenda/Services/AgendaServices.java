package com.tpagenda.tpagenda.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Agenda;

@Service
public interface AgendaServices {
    void ajoutAgenda(String email, String nom);
    Iterable<Agenda> getAllAgenda();

    List<Agenda> getAgendasByEmail(String email);

}

