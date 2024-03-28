package com.akka.akka.demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("MySystem");
        ActorRef greeter1, greeter2;

        greeter1 = system.actorOf(Props.create(GreetingActor.class), "greeter1");
        greeter2 = system.actorOf(Props.create(GreetingActor.class), "greeter2");

        greeter1.tell(new GreetingMessage("Bob"), ActorRef.noSender());
        greeter2.tell(new GreetingMessage("Robert"), ActorRef.noSender());
    }
}
