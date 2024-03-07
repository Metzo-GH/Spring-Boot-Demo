package com.tpagenda.tpagenda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tpagenda.tpagenda.Personne;
import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Repository.PersonneRepository;
import com.tpagenda.tpagenda.Services.AgendaServices;

@Controller
public class AuthController {
    @Autowired
    private PersonneRepository personneRepository;
    private AgendaServices agendaService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("personne", new Personne());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        Personne personne = personneRepository.findByEmailAndPassword(email, password);
        if (personne != null) {
            return "redirect:/loginsuccess";
        } else {
            return "redirect:/loginError";
        }
    }

    @GetMapping("/loginError")
    public String loginError() {
        return "loginError";
    }

    @PostMapping("/loginError")
    public String loginError(@RequestParam("email") String email, @RequestParam("password") String password) {
        Personne personne = personneRepository.findByEmailAndPassword(email, password);
        if (personne != null) {
            return "redirect:/loginsuccess";
        } else {
            return "redirect:/loginError";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("personne") Personne personne) {
        personneRepository.save(personne);
        return "redirect:/login";
    }

    @GetMapping("/loginsuccess")
    public String loginsuccess(Model model) {
        Iterable<Agenda> agendas =  agendaService.getAllAgenda();
        model.addAttribute("agendas", agendas);
        model.addAttribute("newAgenda", new Agenda());
        return "loginsuccess";
    }
}