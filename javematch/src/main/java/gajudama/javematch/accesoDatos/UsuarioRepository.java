package gajudama.javematch.accesoDatos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gajudama.javematch.model.Interes;
import gajudama.javematch.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    @SuppressWarnings("null")
    Optional<Usuario> findById (Long id);

    List<Usuario> findByInteresesIn(List<Interes> intereses);
}
