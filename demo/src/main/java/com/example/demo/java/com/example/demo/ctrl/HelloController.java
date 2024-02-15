package com.example.demo.java.com.example.demo.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/Hello")
    @ResponseBody
    public String sayHello() {
        return "<h1>Hello world!</h1>";
    }

    @GetMapping("HelloView")
    public String sayHelloWithView() {
        return "hello";
    }

    @GetMapping("/Hello2")
    public String sayHello(Model model) {
        model.addAttribute("nom", "Doe");
        model.addAttribute("prenom","John");
        return "hello2";
    }
}
