package br.com.alura.challenger.backendjava.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.challenger.backendjava.Exception.ArquivoImportacaoVazioException;
import br.com.alura.challenger.backendjava.Exception.CSVInvalidoException;
import br.com.alura.challenger.backendjava.Exception.DataImportacaoJaRealizadaException;
import br.com.alura.challenger.backendjava.service.ImportacaoService;

@Controller
@RequestMapping("/importacao")
public class ImportarTransacaoController {

    @Autowired
    private ImportacaoService service;

    @GetMapping
    public ModelAndView get(){
        return new ModelAndView("importar-transacao.html")
                    .addObject("importacoes", service.todasImportacoesOrdendasPorDataTransacaoDesc());
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, ModelMap modelMap, RedirectAttributes redirectAttributes){
        try{
            service.processarArquivo(file);
        }catch(ArquivoImportacaoVazioException | DataImportacaoJaRealizadaException | CSVInvalidoException e){
            redirectAttributes.addFlashAttribute("erroImportacao", e.getMessage());
        }
        return "redirect:/importacao";
    }

    @GetMapping("/detalhes/{dataTransacao}")
    public ModelAndView getDetalhesImportacao(
        @PathVariable("dataTransacao") 
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataTransacao
    ){
        
        return new ModelAndView("detalhes-transacao.html")
                    .addObject("importacao",  service.getImportacaoComTransacoes(dataTransacao));
    }
}
