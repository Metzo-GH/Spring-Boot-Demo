package com.example.demo.java.com.example.demo.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bib")
public class BibliothequeController {
    
    @GetMapping("/home")
    public String home (Model model) {
        List<Auteur> auteurs = new ArrayList<>();
        auteurs.add(new Auteur(1,"Hugo","Victor"));
        auteurs.add(new Auteur(2,"Zola","Emile"));

        model.addAttribute("auteurs",auteurs);

        return "home";
    }

}
