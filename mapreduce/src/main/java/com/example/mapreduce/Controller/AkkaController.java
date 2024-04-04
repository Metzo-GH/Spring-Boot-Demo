package com.example.mapreduce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.mapreduce.Service.AkkaService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class AkkaController {

    private final AkkaService akkaService;

    @Autowired
    public AkkaController(AkkaService akkaService) {
        this.akkaService = akkaService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        // Vous pouvez ajouter des données au modèle ici si nécessaire
        return "index"; // Cela renvoie le nom du fichier HTML (index.html dans notre cas)
    }

    @PostMapping("/init")
    public String initializeActors() {
        akkaService.initializeActors();
        return "redirect:/"; // Redirige vers la page d'accueil après l'initialisation
    }

    @PostMapping("/file")
    public String analyzeFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    akkaService.distributeLines(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/"; // Redirige vers la page d'accueil après l'analyse du fichier
    }

    @PostMapping("/search")
    public String searchWord(@RequestParam("words") String words, Model model) {
        if (!words.isEmpty()) {
            // Appeler le service Akka pour rechercher les mots et récupérer les résultats
            // Map<String, Integer> searchResults = akkaService.searchWord(words);
            // model.addAttribute("results", searchResults);
        }
        return "redirect:/"; // Redirige vers la page d'accueil après la recherche
    }
}
