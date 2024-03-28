package com.example.tpmapreduce.Akka;

import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.actor.UntypedActor;
import org.springframework.stereotype.Component;

@Component
public class WordCountMapper extends UntypedActor {

  private final AkkaService akkaService;

  public WordCountMapper(AkkaService akkaService) {
    this.akkaService = akkaService;
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof String) {
      String line = (String) message;
      String[] words = line.split("\\s+");
      for (String word : words) {
        ActorSelection reducer = akkaService.getReducer(word);
        reducer.tell(new WordCountMessage(word, 1), getSelf());
      }
    } else {
      unhandled(message);
    }
  }

  public static Props props() {
    return Props.create(WordCountMapper.class, AkkaService.class);
  }

}
