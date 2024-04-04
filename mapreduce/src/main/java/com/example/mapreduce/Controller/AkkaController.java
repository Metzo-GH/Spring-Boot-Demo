package com.example.mapreduce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.mapreduce.Service.AkkaService;

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
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchWord(@RequestParam("word") String word, Model model) {
        int wordCount = akkaService.searchWordOccurrences(word);
        model.addAttribute("searchedWord", word);
        model.addAttribute("wordCount", wordCount);
        return "index";
    }
}
