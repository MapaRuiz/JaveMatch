package gajudama.javematch.accesoDatos;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Videollamada;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface VideollamadaRepository extends JpaRepository<Videollamada, Long> {
       @Query("SELECT v FROM Videollamada v WHERE v.match.UserMatchId = :userMatchId")
    Optional<Videollamada> findByMatch_UserMatchId(@Param("userMatchId") Long userMatchId);

}
