package br.com.alura.challenger.backendjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.challenger.backendjava.Exception.EmailJaExisteException;
import br.com.alura.challenger.backendjava.Exception.UsuarioNaoEncontradoException;
import br.com.alura.challenger.backendjava.Exception.UsuarioNaoPodeSerAlteradoException;
import br.com.alura.challenger.backendjava.Exception.UsuarioNaoPodeSerExcluidoException;
import br.com.alura.challenger.backendjava.model.Usuario;
import br.com.alura.challenger.backendjava.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/novo")
    public ModelAndView novoGet(@ModelAttribute("usuario") Usuario usuario) {
        return new ModelAndView("cadastrar-usuario");
    }

    @PostMapping("/novo")
    public String novoPost(@Validated Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            service.salvar(usuario);
        } catch (EmailJaExisteException | UsuarioNaoPodeSerAlteradoException e) {
            addMensagemErro(redirectAttributes, e);
        }

        return "redirect:/usuarios";
    }

    @GetMapping
    public ModelAndView get() {
        return new ModelAndView("listar-usuarios")
        .addObject("usuarios", service.todosOrdernadosPorNomeDesc())
        .addObject("rowIdUsuarioLogado", getRowIdUsuarioLogado())
        ;
    }

    @GetMapping("/{id}")
    public ModelAndView getEditarUsuarioPorId(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuarioSelecionado = service.getUsuarioPorId(id);
            
            return new ModelAndView("cadastrar-usuario").addObject("usuario", usuarioSelecionado);
        } catch (UsuarioNaoEncontradoException e) {
            addMensagemErro(redirectAttributes, e);
            return new ModelAndView("redirect:/usuarios");
        }      
    }

    @PutMapping("/editar")
    public String alterarPorId(@Validated Usuario usuario, RedirectAttributes redirectAttributes){
        return novoPost(usuario, redirectAttributes);
    }

    @DeleteMapping("/{id}")
    public String excluirPorId(@Validated @PathVariable("id") @NonNull Long rowId, RedirectAttributes redirectAttributes){
        try {
            service.excluirUsuarioPeloId(rowId);
            redirectAttributes.addFlashAttribute("mensagem", "Desabilitado com Sucesso");
            redirectAttributes.addFlashAttribute("tituloMensagem", "Mensagem");
        } catch (UsuarioNaoPodeSerExcluidoException e) {
            addMensagemErro(redirectAttributes, e);
        }

        return "redirect:/usuarios";
    }

    private Long getRowIdUsuarioLogado(){
        Usuario u = service.getUsuarioLogado();
        return u.getRowId();
    }

    private void addMensagemErro(RedirectAttributes redirectAttributes, RuntimeException e) {
        redirectAttributes.addFlashAttribute("tituloMensagem", "Ocorreu um Problema");
        redirectAttributes.addFlashAttribute("mensagem", e.getMessage());
    }
}
