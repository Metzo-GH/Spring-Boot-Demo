package com.example.mapreduce.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperActor extends AbstractActor {

    private final Map<String, Integer> wordCounts = new HashMap<>();

     private List<ActorRef> reducersList;

    public MapperActor(List<ActorRef> reducersList) {
        this.reducersList = reducersList;
    }
   
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, line -> {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        ActorRef reducerId = partition(word); // Utilisation de la méthode de partitionnement
                        reducerId.tell(word, ActorRef.noSender());
                    }
                })
                .matchEquals("getCounts", message -> {
                    getSender().tell(wordCounts, getSelf());
                })
                .build();
    }

    private ActorRef partition(String word) {
    
        return reducersList.get(Math.abs(word.hashCode()) % reducersList.size());
    }
}
