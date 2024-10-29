package gajudama.javematch.logic;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UserMatchRepository;
import gajudama.javematch.model.UserMatch;
import gajudama.javematch.model.Usuario;
import gajudama.javematch.model.Videollamada;
import jakarta.transaction.Transactional;

@Service
public class UserMatchLogic {
    @Autowired
    private UserMatchRepository matchRepository;

    @Transactional
    public UserMatch createMatch(UserMatch match) {
        return matchRepository.save(match);
    }

    public Optional<UserMatch> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    @Transactional
    public UserMatch updateMatch(Long id, UserMatch matchDetails) {
        return matchRepository.findById(id).map(match -> {
            match.setFechaMatch(matchDetails.getFechaMatch());
            match.setAmistad(matchDetails.getAmistad());
            match.setVideollamada_Match(matchDetails.getVideollamada_Match());
            return matchRepository.save(match);
        }).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    @Transactional
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public List<UserMatch> getAllMatches() {
        return matchRepository.findAll();
    }

    @Autowired
    private UsuarioLogic usuarioLogic;
    @Autowired
    private VideollamadaLogic videollamadaLogic;
    @Autowired
    private NotificacionLogic notificacionLogic;

    @Transactional
    public UserMatch createMatch(Long usuarioId, Long likedUsuarioId) {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Usuario likedUsuario = usuarioLogic.getUsuarioById(likedUsuarioId)
            .orElseThrow(() -> new RuntimeException("Liked user not found"));

        UserMatch userMatch = new UserMatch();
        userMatch.setUser1(usuario);
        userMatch.setUser2(likedUsuario);
        userMatch.setFechaMatch(new Date());
        userMatch.setAmistad(false);

        Videollamada videollamada = videollamadaLogic.createVideollamada(userMatch);
        // Enviar solicitud de amistad tras la videollamada
        notificacionLogic.sendFriendRequestNotification(usuarioId, likedUsuarioId);

        userMatch.setVideollamada_Match(videollamada);
        
        // Update user's matches
        usuario.getMatchesAsUser1().add(userMatch);
        likedUsuario.getMatchesAsUser2().add(userMatch);
        usuarioLogic.updateMatchesAsUser1(usuarioId, usuario.getMatchesAsUser1());
        usuarioLogic.updateMatchesAsUser2(likedUsuarioId, likedUsuario.getMatchesAsUser2());

        return matchRepository.save(userMatch);
    }
    
}
