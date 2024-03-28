package com.example.tpmapreduce.Akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.HashMap;
import java.util.Map;

public class WordCountReducer extends UntypedActor {

  private final Map<String, Integer> wordCounts = new HashMap<>();

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof WordCountMessage) {
      WordCountMessage wordCountMessage = (WordCountMessage) message;
      String word = wordCountMessage.getWord();
      int count = wordCountMessage.getCount();
      wordCounts.merge(word, count, Integer::sum);
    } else if (message instanceof WordCountReducer.WordCountQuery) {
      WordCountReducer.WordCountQuery query = (WordCountReducer.WordCountQuery) message;
      String word = query.getWord();
      int count = wordCounts.getOrDefault(word, 0);
      WordCountReducer.WordCountResult result = new WordCountReducer.WordCountResult(word, count);
      getSender().tell(result, getSelf());
    } else {
      unhandled(message);
    }
  }

  public static Props props() {
    return Props.create(WordCountReducer.class);
  }

  public static class WordCountQuery {

    private final String word;

    public WordCountQuery(String word) {
      this.word = word;
    }

    public String getWord() {
      return word;
    }

  }

  public static class WordCountResult {

    private final String word;
    private final int count;

    public WordCountResult(String word, int count) {
      this.word = word;
      this.count = count;
    }

    public String getWord() {
      return word;
    }

    public int getCount() {
      return count;
    }

  }

}