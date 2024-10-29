package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UserMatchRepository;
import gajudama.javematch.model.UserMatch;
import gajudama.javematch.model.Usuario;
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
            match.setVideollamada(matchDetails.getVideollamada());
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

    @Transactional
    public void createMatch(Long usuarioId, Long likedUsuarioId) {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Usuario likedUsuario = usuarioLogic.getUsuarioById(likedUsuarioId)
            .orElseThrow(() -> new RuntimeException("Liked user not found"));

        UserMatch userMatch = new UserMatch();
        userMatch.setUser1(usuario);
        userMatch.setUser2(likedUsuario);
        matchRepository.save(userMatch);
    }
    
}
