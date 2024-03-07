package com.tpagenda.tpagenda.Repository;

import org.springframework.data.repository.CrudRepository;

import com.tpagenda.tpagenda.Personne;

public interface PersonneRepository extends CrudRepository<Personne, Long> {

    Personne findByEmailAndPassword(String email, String password);

}