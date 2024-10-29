package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gajudama.javematch.logic.AmistadLogic;
import gajudama.javematch.model.Notificacion;

@RestController
@RequestMapping("/api/amistad")
public class AmistadController {

    @Autowired
    private AmistadLogic amistadLogic;

    @PostMapping("/confirmarAmistad")
    public ResponseEntity<Void> confirmarAmistad(@RequestParam Long matchId, @RequestParam boolean isFriend) {
        amistadLogic.confirmFriendship(matchId, isFriend);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rejectFriendship")
    public ResponseEntity<Notificacion> rejectFriendship(@RequestParam Long usuarioId, @RequestParam Long matchId) {
        amistadLogic.confirmFriendship(matchId, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
