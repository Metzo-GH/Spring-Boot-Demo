package com.example.mapreduce.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.AskTimeoutException;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.mapreduce.Actor.MapperActor;
import com.example.mapreduce.Actor.ReducerActor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@Service
public class AkkaService {

    private ActorSystem actorSystem;
    private ActorRef[] mappers;
    private ActorRef[] reducers;

    private List<ActorRef> reducersList;

    public void initializeActors() {
        actorSystem = ActorSystem.create("MapReduceSystem");
        mappers = new ActorRef[3];
        reducers = new ActorRef[2];

        for (int i = 0; i < 2; i++) {
            String reducerName = "reducer" + i;
            reducers[i] = actorSystem.actorOf(Props.create(ReducerActor.class), reducerName);
            System.out.println(reducerName);
        }

        reducersList = Arrays.asList(reducers);

        for (int i = 0; i < mappers.length; i++) {
            String mapperName = "mapper" + System.currentTimeMillis() + "_" + i;
            mappers[i] = actorSystem.actorOf(Props.create(MapperActor.class, reducersList), mapperName);
            System.out.println(mapperName);
        }
    }

    public void distributeLines(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Distributing line: " + line);
                for (ActorRef mapper : mappers) {
                    mapper.tell(line, ActorRef.noSender());
                }
            }
        } catch (Exception e) {
            System.out.println("Error distributing file lines" + e);
        }
    }

    public int searchWordOccurrences(String word) {
        int totalOccurrences = 0;
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));

        for (ActorRef reducer : reducersList) {
            try {
                Future<Object> future = Patterns.ask(reducer, new ReducerActor.getWord(word), timeout);

                CompletionStage<Object> javaFuture = FutureConverters.toJava(future);

                CompletionStage<Integer> processedFuture = javaFuture.thenApply(response -> {
                    if (response instanceof ReducerActor.WordCount) {
                        return ((ReducerActor.WordCount) response).count;
                    } else {
                        return 0;
                    }
                });

                Integer result = processedFuture.toCompletableFuture().get(5, TimeUnit.SECONDS);
                totalOccurrences += result;
            } catch (AskTimeoutException e) {
                System.err.println("Timeout occurred while waiting for response from reducer: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(totalOccurrences);
        return totalOccurrences;
    }
}
