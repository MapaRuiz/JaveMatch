package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UserLikeRepository;
import gajudama.javematch.model.UserLike;
import gajudama.javematch.model.Usuario;
import jakarta.transaction.Transactional;

@Service
public class UserLikeLogic {
    @Autowired
    private UserLikeRepository likeRepository;

    @Transactional
    public UserLike createLike(UserLike like) {
        return likeRepository.save(like);
    }

    public Optional<UserLike> getLikeById(Long id) {
        return likeRepository.findById(id);
    }

    @Transactional
    public UserLike updateLike(Long id, UserLike likeDetails) {
        return likeRepository.findById(id).map(like -> {
            like.setFechaLike(likeDetails.getFechaLike());
            like.setLikedUsuario(likeDetails.getLikedUsuario());
            like.setUsuarioLike(likeDetails.getUsuarioLike());
            return likeRepository.save(like);
        }).orElseThrow(() -> new RuntimeException("Like not found"));
    }

    @Transactional
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    public List<UserLike> getAllLikes() {
        return likeRepository.findAll();
    }

    @Autowired
    private UsuarioLogic usuarioLogic;
    @Autowired
    private UserMatchLogic userMatchLogic;
    @Autowired
    private NotificacionLogic notificacionLogic;

    @Transactional
    public UserLike likeUser(Long usuarioId, Long likedUsuarioId) throws Exception {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new Exception("User not found"));
        Usuario likedUsuario = usuarioLogic.getUsuarioById(likedUsuarioId)
            .orElseThrow(() -> new Exception("likedUser not found"));
        
        if (usuario.getLikesGiven().size() >= usuario.getPlan().getMaxLikes()) {
            throw new Exception("Like limit reached for your plan");
        }

        Optional<UserLike> existingLike = likeRepository.findByUsuarioLikeAndLikedUsuario(usuarioId, likedUsuarioId);
        if (existingLike.isPresent()) {
            throw new Exception("You have already liked this user");
        }

        UserLike userLike = new UserLike();
        userLike.setUsuarioLike(usuario);
        userLike.setLikedUsuario(likedUsuario);
        likeRepository.save(userLike);

        // Update user's likesGiven and likesReceived
        usuario.getLikesGiven().add(userLike);
        likedUsuario.getLikesReceived().add(userLike);
        usuarioLogic.updateLikesGiven(usuarioId, usuario.getLikesGiven());
        usuarioLogic.updateLikesReceived(likedUsuarioId, likedUsuario.getLikesReceived());

        if (likeRepository.existsByUsuarioLikeAndLikedUsuario(likedUsuarioId, usuarioId)) {
            userMatchLogic.createMatch(usuarioId, likedUsuarioId);
            notificacionLogic.sendNotification(likedUsuarioId, "You have a new match!", false);
            notificacionLogic.sendNotification(usuarioId, "You have a new match!", false);
        }

        return userLike;
    }
}
