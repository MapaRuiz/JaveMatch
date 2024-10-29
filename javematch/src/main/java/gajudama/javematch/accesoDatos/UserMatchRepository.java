package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.UserMatch;

@Repository
public interface UserMatchRepository extends JpaRepository<UserMatch, Long> {
}
