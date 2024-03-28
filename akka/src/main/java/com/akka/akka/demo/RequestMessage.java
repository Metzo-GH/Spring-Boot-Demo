package com.akka.akka.demo;

public record RequestMessage(String content) {
    public String message() {
        return content;
    }
}
