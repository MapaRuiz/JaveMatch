package gajudama.javematch.logic;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        // Obtener usuarios 
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Usuario likedUsuario = usuarioLogic.getUsuarioById(likedUsuarioId)
            .orElseThrow(() -> new RuntimeException("Liked user not found"));
    
        // Crear un nuevo objeto UserMatch
        UserMatch userMatch = new UserMatch();
        userMatch.setUser1(usuario);
        userMatch.setUser2(likedUsuario);
        userMatch.setFechaMatch(new Date());  // Set the match date
        userMatch.setAmistad(false);  // Inicialmente no es amistad
    
        
    
        // Guardar el match en la base de datos
        return matchRepository.save(userMatch);
    }
    
    
    public List<UserMatch> getMutualMatches(Long userId) {
        return matchRepository.findMutualMatches(userId);
    }    

}