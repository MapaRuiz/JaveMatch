package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gajudama.javematch.logic.UserMatchLogic;
import gajudama.javematch.logic.VideollamadaLogic;
import gajudama.javematch.model.Videollamada;
import gajudama.javematch.model.Juego;
import gajudama.javematch.model.UserMatch;

import java.util.List;

@RestController
@RequestMapping("/api/videollamada")
public class VideollamadaController {

    @Autowired
    private VideollamadaLogic videollamadaLogic;
    @Autowired
    private UserMatchLogic userMatchLogic;

    @PostMapping
    public ResponseEntity<Videollamada> createVideollamada(@RequestBody Videollamada videollamada) {
        Videollamada newVideollamada = videollamadaLogic.createVideollamada(videollamada);
        return new ResponseEntity<>(newVideollamada, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videollamada> getVideollamadaById(@PathVariable Long id) {
        return videollamadaLogic.getVideollamadaById(id)
            .map(videollamada -> new ResponseEntity<>(videollamada, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videollamada> updateVideollamada(@PathVariable Long id, @RequestBody Videollamada videollamadaDetails) {
        Videollamada updatedVideollamada = videollamadaLogic.updateVideollamada(id, videollamadaDetails);
        return new ResponseEntity<>(updatedVideollamada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideollamada(@PathVariable Long id) {
        videollamadaLogic.deleteVideollamada(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Videollamada>> getAllVideollamadas() {
        List<Videollamada> videollamadas = videollamadaLogic.getAllVideollamadas();
        return new ResponseEntity<>(videollamadas, HttpStatus.OK);
    }

    // Endpoint específico para crear una videollamada con un match
    @PostMapping("/createWithMatch")
    public ResponseEntity<Videollamada> createVideollamadaWithMatch(@RequestParam Long matchId) {
        UserMatch match = userMatchLogic.getMatchById(matchId)
            .orElseThrow(() -> new RuntimeException("Match not found"));
        Videollamada videollamada = videollamadaLogic.createVideollamada(match);
        return new ResponseEntity<>(videollamada, HttpStatus.CREATED);
    }

    // Endpoint específico para agregar un juego a una videollamada
    @PostMapping("/{videollamadaId}/addJuego")
    public ResponseEntity<Videollamada> addJuegoToVideollamada(@PathVariable Long videollamadaId, @RequestBody Juego juego) {
        Videollamada updatedVideollamada = videollamadaLogic.addJuegoToVideollamada(videollamadaId, juego);
        return new ResponseEntity<>(updatedVideollamada, HttpStatus.OK);
    }
}
