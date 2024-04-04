package com.example.mapreduce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.example.mapreduce.Service.AkkaService;

import akka.actor.ActorRef;

@Controller
public class AkkaController {

    @Autowired
    private AkkaService akkaService;

    
   @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/init")
    public String initializeActors() {
        akkaService.initializeActors();
        return "redirect:/";
    }

    @PostMapping("/file")
    public String analyzeFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            akkaService.distributeLines(file);
            return "redirect:/";
        }
        return "redirect:/"; // Redirige vers la page d'accueil après l'analyse du fichier
    }

     @PostMapping("/search")
public String searchWord(@RequestParam("word") String word, Model model) {
    if (!word.isEmpty()) {
        
        CompletionStage<Object> stage = akkaService.queryReducer(reducer, word);
        CompletableFuture<Object> future = stage.toCompletableFuture();
        try {
            Object result = future.get(5, TimeUnit.SECONDS);
            model.addAttribute("searchResult", result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    return "index";
}

}
