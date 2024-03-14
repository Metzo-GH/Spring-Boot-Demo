package com.tpagenda.tpagenda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tpagenda.tpagenda.Personne;
import com.tpagenda.tpagenda.Agenda;
import com.tpagenda.tpagenda.Repository.PersonneRepository;
import com.tpagenda.tpagenda.Services.AgendaServices;
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
            session.setAttribute("email", email); // Stocker l'email dans la session
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
            session.setAttribute("email", email); // Stocker l'email dans la session
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
        String email = (String) session.getAttribute("email"); // Supposons que vous stockiez l'identifiant de la
                                                               // personne dans la session
        Iterable<Agenda> agendas = agendaService.getAgendasByEmail(email); // Récupérez les agendas associés à la
                                                                           // personne connectée
        model.addAttribute("agendas", agendas);
        model.addAttribute("newAgenda", new Agenda());
        return "loginsuccess";
    }

    @PostMapping("/addAgenda")
    public String ajoutAgenda(@RequestParam String nom, HttpSession session) {
        String email = (String) session.getAttribute("email"); // Supposons que vous stockiez l'identifiant de la
                                                               // personne dans la session
        agendaService.ajoutAgenda(email, nom); // Utilisez l'identifiant de la personne pour associer l'agenda à la
                                               // personne
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
}
