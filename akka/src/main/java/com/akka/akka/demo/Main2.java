package com.akka.akka.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import scala.concurrent.duration.FiniteDuration;

public class Main2 {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("MySystem");
        var server = system.actorOf(Props.create(ResquestResponseActor.class), "server");

        Inbox inbox = Inbox.create(system);
        inbox.send(server, new RequestMessage("ping"));


        Object reply = null;
        try {
            reply = inbox.receive(FiniteDuration.create(5, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (reply instanceof ResponseMessage rm) {
            System.out.print("Réponse : "+rm.message());
        }
    }
}
