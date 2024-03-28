package com.example.tpmapreduce.Akka;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class WordCountMapper extends UntypedActor {

  private final ActorSelection[] reducers;

  public WordCountMapper(ActorSelection[] reducers) {
    this.reducers = reducers;
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof String) {
      String line = (String) message;
      String[] words = line.split("\\s+");
      for (String word : words) {
        ActorSelection reducer = getReducer(word);
        reducer.tell(new WordCountMessage(word, 1), getSelf());
      }
    } else {
      unhandled(message);
    }
  }

  private ActorSelection getReducer(String word) {
    int index = Math.abs(word.hashCode() % reducers.length);
    return reducers[index];
  }

}
