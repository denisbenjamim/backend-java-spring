package br.com.alura.challenger.backendjava.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.alura.challenger.backendjava.model.Usuario;
import lombok.Getter;

public class UsuarioSistema extends User {

    @Getter
    private Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getHashSenha(), authorities);
        this.usuario = usuario;
    }

}
