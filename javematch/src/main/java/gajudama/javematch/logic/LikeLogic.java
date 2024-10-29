package gajudama.javematch.logic;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.LikeRepository;
import gajudama.javematch.model.Like;
import gajudama.javematch.model.Usuario;

@Service
public class LikeLogic {
    private final LikeRepository likeRepository;

    public LikeLogic(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    public Like createLike(Usuario usuario, Usuario likedUsuario) {
        Like like = new Like();
        like.setUsuarioLike(usuario);
        like.setLikedUsuario(likedUsuario);
        like.setFechaLike(new Date());
        return likeRepository.save(like);
    }

    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

}
