package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UserMatchRepository;
import gajudama.javematch.model.UserMatch;

@Service
public class UserMatchLogic {
    @Autowired
    private UserMatchRepository matchRepository;

    public UserMatch createMatch(UserMatch match) {
        return matchRepository.save(match);
    }

    public Optional<UserMatch> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public UserMatch updateMatch(Long id, UserMatch matchDetails) {
        return matchRepository.findById(id).map(match -> {
            match.setFechaMatch(matchDetails.getFechaMatch());
            match.setAmistad(matchDetails.getAmistad());
            match.setVideollamada(matchDetails.getVideollamada());
            return matchRepository.save(match);
        }).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public List<UserMatch> getAllMatches() {
        return matchRepository.findAll();
    }
}
