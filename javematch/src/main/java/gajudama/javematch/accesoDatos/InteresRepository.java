package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Interes;
import java.util.Optional;


@Repository
public interface InteresRepository extends JpaRepository<Interes, Long> {
    Optional<Interes> findByNombre(String nombre);
}
