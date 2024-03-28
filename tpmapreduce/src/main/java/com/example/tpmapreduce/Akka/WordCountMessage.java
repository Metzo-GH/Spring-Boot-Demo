package com.example.tpmapreduce.Akka;

import java.io.Serializable;

public class WordCountMessage implements Serializable {

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
