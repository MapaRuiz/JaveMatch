package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gajudama.javematch.logic.VideollamadaLogic;
import gajudama.javematch.model.Juego;
import gajudama.javematch.model.Videollamada;

@RestController
@RequestMapping("/api/videollamada")
public class VideollamadaController {

    @Autowired
    private VideollamadaLogic videollamadaLogic;

    @PostMapping("/{videollamadaId}/addJuego")
    public ResponseEntity<Videollamada> addJuego(@PathVariable Long videollamadaId, @RequestBody Juego juego) {
        Videollamada updatedVideollamada = videollamadaLogic.addJuegoToVideollamada(videollamadaId, juego);
        return new ResponseEntity<>(updatedVideollamada, HttpStatus.OK);
    }
}
