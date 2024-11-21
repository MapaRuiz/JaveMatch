package gajudama.javematch.accesoDatos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.UserMatch;

@Repository
public interface UserMatchRepository extends JpaRepository<UserMatch, Long> {

    @Query("SELECT um FROM UserMatch um " +
           "WHERE um.user1.userId = :userId " +
           "AND EXISTS (SELECT 1 FROM UserMatch um2 WHERE um2.user1.userId = um.user2.userId AND um2.user2.userId = um.user1.userId)")
    List<UserMatch> findMutualMatches(@Param("userId") Long userId);
}
