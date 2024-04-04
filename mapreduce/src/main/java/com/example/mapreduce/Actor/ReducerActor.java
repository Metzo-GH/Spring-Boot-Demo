package com.example.mapreduce.Actor;

import akka.actor.AbstractActor;
import java.util.HashMap;
import java.util.Map;

public class ReducerActor extends AbstractActor {

    private final Map<String, Integer> wordCounts = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, word -> {
                    // Logique de réduction - Comptage des occurrences de mots
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                })
                .matchEquals("getCounts", message -> {
                    // Envoyer les comptages de mots au demandeur
                    getSender().tell(wordCounts, getSelf());
                })
                .build();
    }
}
