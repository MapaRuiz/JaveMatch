package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Amistad;

@Repository
public interface AmistadRepository extends JpaRepository<Amistad, Long> {
}
