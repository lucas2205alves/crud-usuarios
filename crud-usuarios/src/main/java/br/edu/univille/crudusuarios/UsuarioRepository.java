package br.edu.univille.crudusuarios;

import br.edu.univille.crudusuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}