package com.example.demo_2.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuteursServiceImpl implements AuteursService {
    
    @Autowired
    private AuteursRepository repo;

    @Override
    public void init(){
        repo.save(new Auteurs("Zola", "Emile"));
        repo.save(new Auteurs("Hugo","Victor"));
    }

    @Override
    public void ajoutAuteurs(String nom, String prenom) {
        repo.save(new Auteurs(nom,prenom));
    }

    @Override
    public Iterable<Auteurs> getAllAuteurs(){
        return repo.findAll();
    }
}
