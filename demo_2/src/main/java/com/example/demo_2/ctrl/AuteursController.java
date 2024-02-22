package com.example.demo_2.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/bib")
public class AuteursController {

    @Autowired
    private AuteursService service;

    @GetMapping("/home")
    public String home(Model model){
        Iterable<Auteurs> auteurs = service.getAllAuteurs();
        model.addAttribute("auteurs", auteurs);
        return "/home";
    }

    @PostMapping("/init")
    public String init() {
        service.init();
        return "redirect:/bib/home";
    }

    @PostMapping("/add")
    public String addAuteurs(@RequestParam String nom, @RequestParam String prenom) {
        service.ajoutAuteurs(nom,prenom);
        return "redirect:/bib/home";
    }
}
