package gajudama.javematch.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gajudama.javematch.logic.AmistadLogic;
import gajudama.javematch.model.Amistad;

@RestController
@RequestMapping("/api/amistad")
public class AmistadController {

    @Autowired
    private AmistadLogic amistadLogic;

    @PostMapping
    public ResponseEntity<Amistad> createAmistad(@RequestBody Amistad amistad) {
        Amistad newAmistad = amistadLogic.createAmistad(amistad);
        return new ResponseEntity<>(newAmistad, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Amistad> getAmistadById(@PathVariable Long id) {
        return amistadLogic.getAmistadById(id)
            .map(amistad -> new ResponseEntity<>(amistad, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Amistad> updateAmistad(@PathVariable Long id, @RequestBody Amistad amistadDetails) {
        Amistad updatedAmistad = amistadLogic.updateAmistad(id, amistadDetails);
        return new ResponseEntity<>(updatedAmistad, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmistad(@PathVariable Long id) {
        amistadLogic.deleteAmistad(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Amistad>> getAllAmistades() {
        List<Amistad> amistades = amistadLogic.getAllAmistades();
        return new ResponseEntity<>(amistades, HttpStatus.OK);
    }

    @PostMapping("/confirmFriendship")
    public ResponseEntity<Amistad> confirmFriendship(@RequestParam Long matchId, @RequestParam boolean isFriend) {
        Amistad amistad = amistadLogic.confirmFriendship(matchId, isFriend);
        return new ResponseEntity<>(amistad, HttpStatus.OK);
    }

    @PostMapping("/rejectFriendship")
    public ResponseEntity<Void> rejectFriendship(@RequestParam Long usuarioId, @RequestParam Long matchId) {
        amistadLogic.confirmFriendship(matchId, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
