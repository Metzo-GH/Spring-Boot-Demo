package com.akka.akka.demo;

public record ResponseMessage(String content) {
    public String message() {
        return content;
    }
}
