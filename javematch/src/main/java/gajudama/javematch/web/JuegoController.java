package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.JuegoLogic;
import gajudama.javematch.model.Juego;

@RestController
@RequestMapping("/api/juego")
public class JuegoController {

    @Autowired
    private JuegoLogic juegoLogic;

    @PostMapping
    public ResponseEntity<Juego> createJuego(@RequestBody Juego juego) {
        Juego newJuego = juegoLogic.createJuego(juego);
        return new ResponseEntity<>(newJuego, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Juego> getJuegoById(@PathVariable Long id) {
        return juegoLogic.getJuegosById(id)
            .map(juego -> new ResponseEntity<>(juego, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
