package com.akka.akka.demo;

import akka.actor.UntypedActor;

public class ResquestResponseActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof RequestMessage rm) {
            System.out.println(rm.message());
            getSender().tell(new ResponseMessage("pong"), getSelf());
        }
    }
}
