package br.com.alura.challenger.backendjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.challenger.backendjava.model.Usuario;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public ModelAndView get(@ModelAttribute("usuario") Usuario usuario) {
        return new ModelAndView("login.html");
    }
}
