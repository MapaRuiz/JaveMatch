package gajudama.javematch.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.UserLike;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    
}

