package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
