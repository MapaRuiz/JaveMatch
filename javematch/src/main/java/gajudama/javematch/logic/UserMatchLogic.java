package gajudama.javematch.logic;

import java.util.Date;
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

    @Autowired
    private UsuarioLogic usuarioLogic;

    public Optional<UserMatch> getMatchById(Long id) {
        return matchRepository.findById(id);
    }
    
    @Transactional
    public UserMatch updateMatch(Long id, UserMatch matchDetails) {
        return matchRepository.findById(id).map(match -> {
            match.setFechaMatch(matchDetails.getFechaMatch());
            match.setVideollamada_Match(matchDetails.getVideollamada_Match());
            return matchRepository.save(match);
        }).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    

    @Transactional
    public UserMatch createMatch(Long usuarioId, Long likedUsuarioId) {
        if (usuarioId.equals(likedUsuarioId)) {
            throw new RuntimeException("No puedes hacer match contigo mismo.");
        }

        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Usuario likedUsuario = usuarioLogic.getUsuarioById(likedUsuarioId)
            .orElseThrow(() -> new RuntimeException("Liked user not found"));
    
        UserMatch userMatch = new UserMatch();
        userMatch.setUser1(usuario);
        userMatch.setUser2(likedUsuario);
        userMatch.setFechaMatch(new Date());
        return matchRepository.save(userMatch);
    }
    
    
    public List<UserMatch> getMutualMatches(Long userId) {
        return matchRepository.findMutualMatches(userId);
    }

}