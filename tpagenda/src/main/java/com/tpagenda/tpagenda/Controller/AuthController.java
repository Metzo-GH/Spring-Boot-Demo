package com.tpagenda.tpagenda.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tpagenda.tpagenda.Personne;
import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Evenement;
import com.tpagenda.tpagenda.Repository.PersonneRepository;
import com.tpagenda.tpagenda.Services.AgendaServices;
import com.tpagenda.tpagenda.Services.EvenementService;
import com.tpagenda.tpagenda.Services.PersonneServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private PersonneServices personneServices;
    @Autowired
    private PersonneRepository personneRepository;
    @Autowired
    private AgendaServices agendaService;
    @Autowired
    private EvenementService evenementService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("personne", new Personne());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {
        Personne personne = personneRepository.findByEmailAndPassword(email, password);
        if (personne != null) {
            session.setAttribute("email", email);
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
    public String loginError(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {
        Personne personne = personneRepository.findByEmailAndPassword(email, password);
        if (personne != null) {
            session.setAttribute("email", email);
            return "redirect:/loginsuccess";
        } else {
            return "redirect:/loginError";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String regEmail,
            @RequestParam String regPassword,
            @RequestParam String firstName,
            @RequestParam String lastName) {
        personneServices.ajoutPersonne(regEmail, regPassword, firstName, lastName);
        return "redirect:/login";
    }

    @GetMapping("/loginsuccess")
    public String loginsuccess(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");
        Iterable<Agenda> agendas = agendaService.getAgendasByEmail(email);
        model.addAttribute("agendas", agendas);
        model.addAttribute("newAgenda", new Agenda());
        return "loginsuccess";
    }

    @PostMapping("/addAgenda")
    public String ajoutAgenda(@RequestParam String nom, HttpSession session) {
        String email = (String) session.getAttribute("email");
        agendaService.ajoutAgenda(email, nom);
        return "redirect:/loginsuccess";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/loginsuccess/evenements/{nomAgenda}")
    public String listEvenements(@PathVariable String nomAgenda, Model model) {
        List<Evenement> evenements = evenementService.getEvenementsByNomAgenda(nomAgenda);
        model.addAttribute("evenements", evenements);
        return "loginSuccessEvenement";
    }

    @PostMapping("/agenda/addEvenement/{nomAgenda}")
    public String addEvenement(@PathVariable String nomAgenda,
            @RequestParam String nomEvenement,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam String label) {
        evenementService.ajouterEvenement(nomAgenda, date, startTime, endTime, label);
        return "redirect:/loginsuccess/evenements/" + nomAgenda;
    }

}
