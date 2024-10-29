package gajudama.javematch.logic;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.MatchRepository;
import gajudama.javematch.model.Match;
import gajudama.javematch.model.Usuario;
import gajudama.javematch.model.Videollamada;

@Service
public class MatchLogic {
    private final MatchRepository matchRepository;

    public MatchLogic(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match createMatch(Usuario user1, Usuario user2, Videollamada videollamada) {
        Match match = new Match();
        match.setUser1(user1);
        match.setUser2(user2);
        match.setFechaMatch(new Date());
        match.setAmistad(false);
        match.setVideollamada(videollamada);
        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, Match updatedMatch) {
        return matchRepository.findById(id).map(match -> {
            match.setUser1(updatedMatch.getUser1());
            match.setUser2(updatedMatch.getUser2());
            match.setAmistad(updatedMatch.getAmistad());
            match.setVideollamada(updatedMatch.getVideollamada());
            return matchRepository.save(match);
        }).orElseGet(() -> {
            updatedMatch.setId(id);
            return matchRepository.save(updatedMatch);
        });
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}
