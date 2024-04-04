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
        mappers = new ActorRef[3];
        reducers = new ActorRef[2];
        initializeActors();
    }

    public void initializeActors() {
        // Initialisation des acteurs Mapper
        for (int i = 1; i <= mappers.length; i++) {
            mappers[i] = actorSystem.actorOf(Props.create(MapperActor.class), "mapper" + i);
        }

        // Initialisation des acteurs Reducer
        for (int i = 1; i <= reducers.length; i++) {
            reducers[i] = actorSystem.actorOf(Props.create(ReducerActor.class), "reducer" + i);
        }
    }

    // Méthode de partitionnement
    private ActorRef partition(String word) {
        int reducerCount = reducers.length; // Nombre de Reducers
        int reducerIndex = Math.abs(word.hashCode() % reducerCount);
        return reducers[reducerIndex];
    }

    // Méthode pour distribuer les lignes du fichier aux Mappers
    public void distributeLines(String line) {
        String[] words = line.split("\\s+");
        for (String word : words) {
            ActorRef reducer = partition(word);
            mappers[(int) (Math.random() * mappers.length)].tell(word, reducer);
        }
    }

    // Méthode pour interroger les Reducers pour obtenir le nombre d'occurrences
    // d'un mot
    public void queryReducer(String word) {
        for (ActorRef reducer : reducers) {
            reducer.tell(word, ActorRef.noSender());
        }
    }
}
