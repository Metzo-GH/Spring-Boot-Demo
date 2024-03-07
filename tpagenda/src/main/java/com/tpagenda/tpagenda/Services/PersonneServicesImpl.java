package com.tpagenda.tpagenda.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpagenda.tpagenda.Personne;
import com.tpagenda.tpagenda.Repository.PersonneRepository;

@Service
public class PersonneServicesImpl implements PersonneServices {
    
    @Autowired
    private PersonneRepository repo;

    @Override
    public void ajoutPersonne(String email, String password, String prenom, String nom) {
        repo.save(new Personne(email,password,nom,prenom));
    }

    @Override
    public Iterable<Personne> getAllPersonne(){
        return repo.findAll();
    }
}