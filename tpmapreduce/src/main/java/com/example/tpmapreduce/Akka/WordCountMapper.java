package com.example.tpmapreduce.Akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

@Component
public class WordCountMapper extends UntypedActor {

  private final ActorRef reducer;

  public WordCountMapper(ActorRef reducer) {
    this.reducer = reducer;
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof String) {
      String line = (String) message;
      StringTokenizer tokenizer = new StringTokenizer(line);
      while (tokenizer.hasMoreElements()) {
        String word = tokenizer.nextToken();
        WordCountReducer.WordCountMessage msg = new WordCountReducer.WordCountMessage(word, 1);
        reducer.tell(msg, getSelf());
      }
    } else {
      unhandled(message);
    }
  }

  public static Props props(ActorRef reducer) {
    return Props.create(WordCountMapper.class, reducer);
  }

}
