package com.example.tpmapreduce.Akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AkkaService {

  private final ActorSystem system1;
  private final ActorSystem system2;
  private final List<ActorRef> reducers;

  public AkkaService(ActorSystem system1, ActorSystem system2) {
    this.system1 = system1;
    this.system2 = system2;
    this.reducers = new ArrayList<>();
    initActors();
  }

  public void initActors() {
    // Créer 3 acteurs Mapper dans le système d'acteurs 1
    for (int i = 0; i < 3; i++) {
      system1.actorOf(WordCountMapper.props(), "mapper" + i);
    }

    // Créer 2 acteurs Reducer dans le système d'acteurs 2
    for (int i = 0; i < 2; i++) {
      ActorRef reducer = system2.actorOf(Props.create(WordCountReducer.class), "reducer" + i);
      reducers.add(reducer);
    }
  }

  public void distributeLines(List<String> lines) {
    // Distribuer les lignes alternativement à chaque acteur Mapper dans le système d'acteurs 1
    for (int i = 0; i < lines.size(); i++) {
      system1.actorSelection("/user/mapper" + (i % 3)).tell(lines.get(i), ActorRef.noSender());
    }
  }

  public int getWordCount(String word) {
    int count = 0;
    // Interroger chaque acteur Reducer dans le système d'acteurs 2 pour obtenir le nombre d'occurrences du mot
    for (ActorRef reducer : reducers) {
      WordCountReducer.WordCountQuery query = new WordCountReducer.WordCountQuery(word);
      WordCountReducer.WordCountResult result = (WordCountReducer.WordCountResult) Patterns.ask(reducer, query,
          Timeout.create(Duration.ofSeconds(5))).toCompletableFuture().get(5, TimeUnit.SECONDS);
      count += result.getCount();
    }
    return count;
  }

  private ActorRef getReducer(String word) {
    int index = Math.abs(word.hashCode() % reducers.size());
    return reducers.get(index);
  }

}
