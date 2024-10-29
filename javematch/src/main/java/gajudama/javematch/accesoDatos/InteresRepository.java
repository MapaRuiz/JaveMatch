package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Interes;


@Repository
public interface InteresRepository extends JpaRepository<Interes, Long> {
    
}
