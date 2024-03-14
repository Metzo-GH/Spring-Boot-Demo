package com.tpagenda.tpagenda.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Personne;

@Repository
public interface AgendaRepository extends CrudRepository<Agenda, Long> {
    List<Agenda> findByPersonne(Personne personne);
}
