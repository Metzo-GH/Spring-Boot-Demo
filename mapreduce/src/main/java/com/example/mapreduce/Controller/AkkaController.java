package com.example.mapreduce.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AkkaController {

    @RequestMapping("/")
    public String index(Model model) {
        // Vous pouvez ajouter des données au modèle ici si nécessaire
        return "index"; // Cela renvoie le nom du fichier HTML (index.html dans notre cas)
    }

    @PostMapping("/init")
    public String initializeActors() {
        // Ajoutez ici la logique pour initialiser les acteurs Akka
        return "redirect:/"; // Redirige vers la page d'accueil après l'initialisation
    }

    @PostMapping("/file")
    public String analyzeFile(@RequestParam("file") MultipartFile file) {
        // Ajoutez ici la logique pour traiter le fichier envoyé par l'utilisateur
        // Initiez le processus d'analyse des mots avec Akka
        return "redirect:/"; // Redirige vers la page d'accueil après l'analyse du fichier
    }

    @PostMapping("/search")
    public String searchWord(@RequestParam("words") String words, Model model) {
        // Ajoutez ici la logique pour rechercher les mots dans les résultats
        // Ajoutez les résultats de la recherche au modèle pour les afficher sur la page
        return "redirect:/"; // Redirige vers la page d'accueil après la recherche
    }
}
