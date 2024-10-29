package gajudama.javematch.accesoDatos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.UserLike;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByUsuarioLikeAndLikedUsuario(Long usuarioLikeId, Long likedUsuarioId);
    boolean existsByUsuarioLikeAndLikedUsuario(Long usuarioLikeId, Long likedUsuarioId);
}

