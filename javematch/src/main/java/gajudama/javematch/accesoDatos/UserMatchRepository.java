package gajudama.javematch.accesoDatos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.UserMatch;
import java.util.Optional;

@Repository
public interface UserMatchRepository extends JpaRepository<UserMatch, Long> {

@Query("SELECT um FROM UserMatch um " +
       "WHERE um.user1.userId = :userId OR um.user2.userId = :userId")
    List<UserMatch> findMutualMatch(@Param("userId") Long userId);

    @Query("SELECT um FROM UserMatch um WHERE " +
       "(um.user1.userId = :user1Id AND um.user2.userId = :user2Id) OR " +
       "(um.user1.userId = :user2Id AND um.user2.userId = :user1Id)")
Optional<UserMatch> findByUsers(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);
    


}
