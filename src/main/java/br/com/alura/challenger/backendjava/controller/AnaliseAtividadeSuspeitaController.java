package br.com.alura.challenger.backendjava.controller;

import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/analise")
public class AnaliseAtividadeSuspeitaController {
    
    @GetMapping
    public ModelAndView getAnalise(){
        
        return new ModelAndView("analise-transacoes-suspeitas")
                .addObject("transacoes", Collections.emptyList())
                   ;
    }
}
