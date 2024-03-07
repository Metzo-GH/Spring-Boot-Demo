package com.tpagenda.tpagenda.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tpagenda.tpagenda.Agenda;

@Repository
public interface AgendaRepository extends CrudRepository<Agenda, Long> {

}