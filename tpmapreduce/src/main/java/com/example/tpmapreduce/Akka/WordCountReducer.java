package com.example.tpmapreduce.Akka;

import akka.actor.Props;
import akka.actor.UntypedActor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class WordCountReducer extends UntypedActor {

  private final Map<String, Integer> wordCounts = new HashMap<>();

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof WordCountMessage) {
      WordCountMessage msg = (WordCountMessage) message;
      String word = msg.getWord();
      int count = msg.getCount();
      wordCounts.merge(word, count, Integer::sum);
    } else if (message instanceof WordCountQuery) {
      WordCountQuery query = (WordCountQuery) message;
      String word = query.getWord();
      int count = wordCounts.getOrDefault(word, 0);
      WordCountResult result = new WordCountResult(word, count);
      getSender().tell(result, getSelf());
    } else {
      unhandled(message);
    }
  }

  public static Props props() {
    return Props.create(WordCountReducer.class);
  }

  public static class WordCountMessage implements Serializable {

    private final String word;
    private final int count;

    public WordCountMessage(String word, int count) {
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

  public static class WordCountQuery implements Serializable {

    private final String word;

    public WordCountQuery(String word) {
      this.word = word;
    }

    public String getWord() {
      return word;
    }

  }

  public static class WordCountResult implements Serializable {

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
