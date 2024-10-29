package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Videollamada;

@Repository
public interface VideollamadaRepository extends JpaRepository<Videollamada, Long> {
}
