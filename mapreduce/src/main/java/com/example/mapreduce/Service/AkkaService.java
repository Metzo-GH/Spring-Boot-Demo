package com.example.mapreduce.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.springframework.stereotype.Service;

import com.example.mapreduce.Actor.MapperActor;
import com.example.mapreduce.Actor.ReducerActor;

@Service
public class AkkaService {

    private final ActorSystem actorSystem;
    private final ActorRef[] mappers;
    private final ActorRef[] reducers;

    public AkkaService() {
        actorSystem = ActorSystem.create("MapReduceSystem");

        // Initialisation des acteurs Mapper
        mappers = new ActorRef[3]; // Vous pouvez ajuster le nombre de Mappers selon vos besoins
        for (int i = 0; i < mappers.length; i++) {
            mappers[i] = actorSystem.actorOf(Props.create(MapperActor.class), "mapper" + i);
        }

        // Initialisation des acteurs Reducer
        reducers = new ActorRef[2]; // Vous pouvez ajuster le nombre de Reducers selon vos besoins
        for (int i = 0; i < reducers.length; i++) {
            reducers[i] = actorSystem.actorOf(Props.create(ReducerActor.class), "reducer" + i);
        }
    }

    // Méthode de partitionnement
    private String partition(String word) {
        int reducerCount = reducers.length; // Nombre de Reducers
        return Integer.toString(Math.abs(word.hashCode() % reducerCount));
    }

    // Méthode pour distribuer les lignes du fichier aux Mappers
    public void distributeLines(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            mappers[i % mappers.length].tell(lines[i], ActorRef.noSender());
        }
    }

    // Méthode pour interroger les Reducers pour obtenir le nombre d'occurrences d'un mot
    public void queryReducer(String word) {
        for (ActorRef reducer : reducers) {
            reducer.tell(word, ActorRef.noSender());
        }
    }
}

