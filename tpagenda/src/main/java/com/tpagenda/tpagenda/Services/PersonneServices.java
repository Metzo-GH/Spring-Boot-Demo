package com.tpagenda.tpagenda.Services;

import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Personne;

@Service
public interface PersonneServices {
    void init();
    void ajoutAuteurs( String nom, String prenom);
    Iterable<Personne> getAllAuteurs();
}
