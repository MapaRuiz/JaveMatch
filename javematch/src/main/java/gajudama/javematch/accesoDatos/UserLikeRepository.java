package gajudama.javematch.accesoDatos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gajudama.javematch.model.UserLike;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    Optional<UserLike> findByUsuarioLike_User_idAndLikedUsuario_User_id(Long usuarioLikeId, Long likedUsuarioId);
    boolean existsByUsuarioLike_User_idAndLikedUsuario_User_id(Long usuarioLikeId, Long likedUsuarioId);
}

