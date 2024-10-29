package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.UserMatchLogic;
import gajudama.javematch.model.UserMatch;

import java.util.List;

@RestController
@RequestMapping("/api/usermatch")
public class UserMatchController {

    @Autowired
    private UserMatchLogic userMatchLogic;

    @PostMapping
    public ResponseEntity<UserMatch> createMatch(@RequestBody UserMatch match) {
        UserMatch newMatch = userMatchLogic.createMatch(match);
        return new ResponseEntity<>(newMatch, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMatch> getMatchById(@PathVariable Long id) {
        return userMatchLogic.getMatchById(id)
            .map(match -> new ResponseEntity<>(match, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMatch> updateMatch(@PathVariable Long id, @RequestBody UserMatch matchDetails) {
        UserMatch updatedMatch = userMatchLogic.updateMatch(id, matchDetails);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        userMatchLogic.deleteMatch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserMatch>> getAllMatches() {
        List<UserMatch> matches = userMatchLogic.getAllMatches();
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    // Endpoint específico para crear un match entre dos usuarios
    @PostMapping("/createMatch")
    public ResponseEntity<UserMatch> createMatch(@RequestParam Long usuarioId, @RequestParam Long likedUsuarioId) {
        UserMatch match = userMatchLogic.createMatch(usuarioId, likedUsuarioId);
        return new ResponseEntity<>(match, HttpStatus.CREATED);
    }

    // Endpoint específico para crear un match aleatorio
    @PostMapping("/randomMatch")
    public ResponseEntity<UserMatch> randomMatch(@RequestParam Long usuarioId) {
        UserMatch match = userMatchLogic.randomMatch(usuarioId);
        return new ResponseEntity<>(match, HttpStatus.CREATED);
    }
}
