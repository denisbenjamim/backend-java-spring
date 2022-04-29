package br.com.alura.challenger.backendjava.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.challenger.backendjava.Exception.EmailJaExisteException;
import br.com.alura.challenger.backendjava.Exception.UsuarioNaoEncontradoException;
import br.com.alura.challenger.backendjava.Exception.UsuarioNaoPodeSerAlteradoException;
import br.com.alura.challenger.backendjava.Exception.UsuarioNaoPodeSerExcluidoException;
import br.com.alura.challenger.backendjava.model.Usuario;
import br.com.alura.challenger.backendjava.repository.ImportacaoRepository;
import br.com.alura.challenger.backendjava.repository.UsuarioRepository;
import br.com.alura.challenger.backendjava.security.UsuarioSistema;
import br.com.alura.challenger.backendjava.utils.GeradorSenha;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ImportacaoRepository importacaoRepository;

    @Autowired
    private EnviarEmail enviarEmail;
    
    private final long ID_USUARIO_ADM = 1L;
    
    public void salvar(Usuario usuario)throws EmailJaExisteException, UsuarioNaoPodeSerAlteradoException{
        
        boolean novoUsuario  = usuario.getRowId()== null;
        
        if(novoUsuario && usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new EmailJaExisteException("Este endereço de e-mail, ja esta em uso!.");
        
        validarUsuarioAdministradorAlteracao(usuario.getRowId());
        
        if(novoUsuario){
            usuario.setSenha(GeradorSenha.getSenhaAleatoria());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
            
            usuario.setHashSenha(passwordEncoder.encode(usuario.getSenha()));
            enviarEmail.enviar(usuario.getEmail(), usuario.getSenha());
        }

        usuarioRepository.save(usuario);
    }

    public List<Usuario> todosOrdernadosPorNomeDesc(){
        return usuarioRepository.findAllByOrderByRowIdDesc();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Usuario usuario = usuarioRepository.findByEmailAndAtivo(email, true).orElseThrow(() -> new UsernameNotFoundException("Usuário/Senha incorretos"));
        final UsuarioSistema usuarioLogado = new UsuarioSistema(usuario, getPermissoes(usuario));
        
        return usuarioLogado;
    }

    private Collection<? extends GrantedAuthority>  getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> autorizacoes = new HashSet<>();

        List<String> permissoes = usuarioRepository.findAllPermissoesByUsuario(usuario);
       
        for(String permissao: permissoes){
            autorizacoes.add(new SimpleGrantedAuthority(permissao.toUpperCase()));
        }
        return autorizacoes;
    }

    public Usuario getUsuarioLogado(){
        return ((UsuarioSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsuario();
    }

    public Usuario getUsuarioPorId(Long id) throws UsuarioNaoEncontradoException{
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNaoEncontradoException :: new);
        
        return usuario;
    }

    public void excluirUsuarioPeloId(Long id) throws UsuarioNaoPodeSerExcluidoException{
        validarUsuarioAdministradorExclusao(id);
        
        Usuario usuarioParaDesativar = getUsuarioPorId(id);
        
        if(importacaoRepository.existsByUsuarioImportacao(usuarioParaDesativar)){
            throw new UsuarioNaoPodeSerExcluidoException("Não pode ser desativado, por que possui importações");
        }

        usuarioParaDesativar.setAtivo(false);

        salvar(usuarioParaDesativar);
      
    }

    private void validarUsuarioAdministradorExclusao(Long id) throws UsuarioNaoPodeSerExcluidoException {
        if(id == ID_USUARIO_ADM)
            throw new UsuarioNaoPodeSerExcluidoException();
    }

    private void validarUsuarioAdministradorAlteracao(Long id) throws UsuarioNaoPodeSerAlteradoException {
        if(id == ID_USUARIO_ADM)
            throw new UsuarioNaoPodeSerAlteradoException();
    }
}
