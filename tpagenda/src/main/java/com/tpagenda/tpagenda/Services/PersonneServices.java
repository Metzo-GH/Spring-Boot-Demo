package com.tpagenda.tpagenda.Services;

import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Personne;

@Service
public interface PersonneServices {
    void ajoutPersonne(String email, String password, String prenom, String nom);
    Iterable<Personne> getAllPersonne();
}
