package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.UsuarioInteres;

@Repository
public interface UsuarioInteresRepository extends JpaRepository<UsuarioInteres, Long> {
}
