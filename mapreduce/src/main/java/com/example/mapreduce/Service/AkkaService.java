package com.example.mapreduce.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import akka.pattern.Patterns;

import com.example.mapreduce.Actor.MapperActor;
import com.example.mapreduce.Actor.ReducerActor;

@Service
public class AkkaService {

    private ActorSystem actorSystem;
    private ActorRef[] mappers;
    private ActorRef[] reducers;

    private List<ActorRef> reducersList;


    public void initializeActors() {

        actorSystem = ActorSystem.create("MapReduceSystem");
        mappers = new ActorRef[3];
        reducers = new ActorRef[2];
        // Initialisation des acteurs Mapper
        
    
        // Initialisation des acteurs Reducer
        for (int i = 0; i < 2 ; i++) {
            String reducerName = "reducer" + i;
            reducers[i] = actorSystem.actorOf(Props.create(ReducerActor.class), reducerName);
            System.out.println(reducerName);
        }

        reducersList = Arrays.asList(reducers);

        for (int i = 0; i < mappers.length; i++) {
            String mapperName = "mapper" + System.currentTimeMillis() + "_" + i;
            mappers[i] = actorSystem.actorOf(Props.create(MapperActor.class,reducersList), mapperName);
            System.out.println(mapperName);
        }
    }    

    // Méthode pour distribuer les lignes du fichier aux Mappers
    public void distributeLines(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            int mapperId = 0;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println("Distributing line {"+ lineNumber+"} to mapper {"+ mapperId+"}");
                mappers[mapperId].tell(line, ActorRef.noSender());
                mapperId = (mapperId + 1) % mappers.length;
                lineNumber++;
            }
            System.out.println("Total lines distributed: {"+ lineNumber+"}");
        } catch (Exception e) {
            System.out.println("Error distributing file lines"+ e);
        }
    }

    // Méthode pour interroger les Reducers pour obtenir le nombre d'occurrences d'un mot
    public CompletionStage<Object> queryReducer(ActorRef reducer, String word) {
        // Envoyer une demande à l'acteur Reducer et attendre une réponse
        return Patterns.ask(reducer, word, Duration.ofSeconds(5));
    }

}
