package com.example.tpmapreduce.Akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

@Service
public class AkkaService {

    private final ActorSystem system1;
    private final ActorSystem system2;
    private final List<ActorRef> mappers;
    private final List<ActorRef> reducers;

    public AkkaService(ActorSystem system1, ActorSystem system2) {
        this.system1 = system1;
        this.system2 = system2;
        this.mappers = new ArrayList<>();
        this.reducers = new ArrayList<>();
        initActors();
    }

    private void initActors() {
        for (int i = 0; i < 3; i++) {
            ActorRef mapper = system1.actorOf(WordCountMapper.props(getReducer()), "mapper" + i);
            mappers.add(mapper);
        }

        for (int i = 0; i < 2; i++) {
            ActorRef reducer = system2.actorOf(WordCountReducer.props(), "reducer" + i);
            reducers.add(reducer);
        }
    }

    private ActorRef getReducer() {
        return reducers.get(0);
    }

    public void distributeLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            ActorRef mapper = mappers.get(i % mappers.size());
            mapper.tell(lines.get(i), ActorRef.noSender());
        }
    }

    public int getWordCount(String word) throws ExecutionException, InterruptedException {
        WordCountReducer.WordCountQuery query = new WordCountReducer.WordCountQuery(word);
        int count = 0;
        for (ActorRef reducer : reducers) {
            Timeout timeout = Timeout.create(Duration.ofSeconds(5));
            WordCountReducer.WordCountResult result = (WordCountReducer.WordCountResult) Patterns
                    .ask(reducer, query, timeout).toCompletableFuture().get();
            count += result.getCount();
        }
        return count;
    }

}
