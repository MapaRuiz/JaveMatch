package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Juego;


@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    
}
