package com.example.demo_2.ctrl;

import org.springframework.stereotype.Service;

@Service
public interface AuteursService {
    void init();
    void ajoutAuteurs( String nom, String prenom);
    Iterable<Auteurs> getAllAuteurs();
}