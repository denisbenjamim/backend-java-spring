package br.com.alura.challenger.backendjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.challenger.backendjava.Exception.EmailJaExisteException;
import br.com.alura.challenger.backendjava.model.Usuario;
import br.com.alura.challenger.backendjava.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/novo")
    public ModelAndView novoGet(@ModelAttribute("usuario") Usuario usuario) {
        return new ModelAndView("cadastrar-usuario.html");
    }

    @PostMapping("/novo")
    public String novoPost(@Validated Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            service.salvar(usuario);
        } catch (EmailJaExisteException e) {
            redirectAttributes.addFlashAttribute("erroCadastroUsuario", e.getMessage());
        }

        return "redirect:/usuario";
    }

    @GetMapping
    public ModelAndView get() {
        return new ModelAndView("listar-usuarios.html").addObject("usuarios", service.todos());
    }
}
