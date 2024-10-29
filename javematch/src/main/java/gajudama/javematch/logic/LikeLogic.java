package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.LikeRepository;
import gajudama.javematch.model.Like;

@Service
public class LikeLogic {
    @Autowired
    private LikeRepository likeRepository;

    public Like createLike(Like like) {
        return likeRepository.save(like);
    }

    public Optional<Like> getLikeById(Long id) {
        return likeRepository.findById(id);
    }

    public Like updateLike(Long id, Like likeDetails) {
        return likeRepository.findById(id).map(like -> {
            like.setFechaLike(likeDetails.getFechaLike());
            like.setLikedUsuario(likeDetails.getLikedUsuario());
            like.setUsuarioLike(likeDetails.getUsuarioLike());
            return likeRepository.save(like);
        }).orElseThrow(() -> new RuntimeException("Like not found"));
    }

    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

}
