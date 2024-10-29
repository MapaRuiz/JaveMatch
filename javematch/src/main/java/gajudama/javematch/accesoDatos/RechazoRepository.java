package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Rechazo;

@Repository
public interface RechazoRepository extends JpaRepository<Rechazo, Long> {
}
