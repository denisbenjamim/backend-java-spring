package br.com.alura.challenger.backendjava.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.challenger.backendjava.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {
    public boolean existsByEmail(String email);

    public Optional<Usuario> findByEmail(String email);

    @Query("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario")
    public List<String> findAllPermissoesByUsuario(Usuario usuario);

    @Query("SELECT new Usuario(u.rowId, u.nome, u.email, u.hashSenha) FROM Usuario u WHERE u.rowId > 1 ORDER BY u.nome DESC")
    public List<Usuario> findAllByOrderByRowIdDesc();
    
}
