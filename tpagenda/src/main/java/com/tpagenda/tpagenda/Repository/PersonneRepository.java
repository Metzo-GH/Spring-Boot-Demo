package com.tpagenda.tpagenda.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tpagenda.tpagenda.Personne;

@Repository
public interface PersonneRepository extends CrudRepository<Personne, Long> {
    Personne findByEmailAndPassword(String email, String password);
}