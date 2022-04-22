package br.com.alura.challenger.backendjava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenger.backendjava.Exception.EmailJaExisteException;
import br.com.alura.challenger.backendjava.model.Usuario;
import br.com.alura.challenger.backendjava.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnviarEmail enviarEmail;

    public void salvar(Usuario usuario)throws EmailJaExisteException{
        if(usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new EmailJaExisteException("Este endereço de e-mail, ja esta em uso!.");

        usuarioRepository.save(usuario);
        
        enviarEmail.enviar(usuario.getEmail(), usuario.getSenha());
    }

    public List<Usuario> todos(){
        return usuarioRepository.findAll();
    }
}
