package com.example.tpmapreduce.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Controller
public class AkkaController {
    private ActorSystem actorSystem;

    @PostConstruct
    public void initActors() {
        actorSystem = ActorSystem.create("WordCountSystem");
    }

    @GetMapping("/")
    public String index() {
        return "tp3-akka";
    }

    @PostMapping("/init")
    public String initActors() {
        // Initialiser les acteurs Akka ici
        return "redirect:/";
    }

    @PostMapping("/file")
    public String processFile(@RequestParam("file") MultipartFile file, Model model) {
        // Traiter le fichier sélectionné ici
        // Ajouter les résultats du traitement au modèle pour les afficher dans la vue
        return "akka";
    }

    @PostMapping("/search")
    public String searchWords(@RequestParam("words") String words, Model model) {
        // Rechercher les mots saisis dans le fichier traité
        // Ajouter les résultats de la recherche au modèle pour les afficher dans la vue
        return "akka";
    }

    @PreDestroy
    public void shutdownActors() {
        actorSystem.terminate();
    }
}
