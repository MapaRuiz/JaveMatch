package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
