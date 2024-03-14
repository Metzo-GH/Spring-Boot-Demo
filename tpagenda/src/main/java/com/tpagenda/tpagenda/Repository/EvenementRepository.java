package com.tpagenda.tpagenda.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Evenement;

@Repository
public interface EvenementRepository extends CrudRepository<Evenement, String> {
    List<Evenement> findByAgenda(Agenda agenda);
    List<Evenement> findByNomEvenement(String nomEvenement);
}
