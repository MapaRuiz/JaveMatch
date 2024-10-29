package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UserLikeRepository;
import gajudama.javematch.model.UserLike;

@Service
public class UserLikeLogic {
    @Autowired
    private UserLikeRepository likeRepository;

    public UserLike createLike(UserLike like) {
        return likeRepository.save(like);
    }

    public Optional<UserLike> getLikeById(Long id) {
        return likeRepository.findById(id);
    }

    public UserLike updateLike(Long id, UserLike likeDetails) {
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

    public List<UserLike> getAllLikes() {
        return likeRepository.findAll();
    }

}
