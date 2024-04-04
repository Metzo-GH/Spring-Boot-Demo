package com.example.mapreduce.Actor;

import akka.actor.AbstractActor;
import java.util.HashMap;
import java.util.Map;

public class MapperActor extends AbstractActor {

    private final Map<String, Integer> wordCounts = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, line -> {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        String reducerId = partition(word); // Utilisation de la méthode de partitionnement
                        getContext().getSystem().actorSelection("/user/reducers/" + reducerId)
                                .tell(word, getSelf());
                    }
                })
                .matchEquals("getCounts", message -> {
                    getSender().tell(wordCounts, getSelf());
                })
                .build();
    }

    private String partition(String word) {
        // Logique de partitionnement - Utilisation d'une méthode de hachage simple
        int reducerCount = 5; // Nombre de Reducers
        return Integer.toString(Math.abs(word.hashCode() % reducerCount));
    }
}
