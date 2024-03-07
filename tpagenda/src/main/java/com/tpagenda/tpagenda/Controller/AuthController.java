package com.tpagenda.tpagenda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tpagenda.tpagenda.Personne;
import com.tpagenda.tpagenda.Repository.PersonneRepository;

public class AuthController {
    @Autowired
    private PersonneRepository personneRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("personne", new Personne());
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("personne") Personne personne) {
        personneRepository.save(personne);
        return "redirect:/login";
    }
}