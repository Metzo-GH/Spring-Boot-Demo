package com.example.tpmapreduce.Akka;

import akka.actor.UntypedActor;

import java.util.HashMap;
import java.util.Map;

public class WordCountReducer extends UntypedActor {

  private final Map<String, Integer> wordCounts = new HashMap<>();

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof WordCountMessage) {
      WordCountMessage wcm = (WordCountMessage) message;
      String word = wcm.getWord();
      int count = wcm.getCount();
      wordCounts.merge(word, count, Integer::sum);
    } else {
      unhandled(message);
    }
  }

  public int getWordCount(String word) {
    return wordCounts.getOrDefault(word, 0);
  }

}
