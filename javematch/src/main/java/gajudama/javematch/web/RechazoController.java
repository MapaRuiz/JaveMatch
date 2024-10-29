package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.RechazoLogic;
import gajudama.javematch.model.Rechazo;

import java.util.List;

@RestController
@RequestMapping("/api/rechazo")
public class RechazoController {

    @Autowired
    private RechazoLogic rechazoLogic;

    @PostMapping
    public ResponseEntity<Rechazo> createRechazo(@RequestBody Rechazo rechazo) {
        Rechazo newRechazo = rechazoLogic.createRechazo(rechazo);
        return new ResponseEntity<>(newRechazo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rechazo> getRechazoById(@PathVariable Long id) {
        return rechazoLogic.getRechazoById(id)
            .map(rechazo -> new ResponseEntity<>(rechazo, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rechazo> updateRechazo(@PathVariable Long id, @RequestBody Rechazo rechazoDetails) {
        Rechazo updatedRechazo = rechazoLogic.updateRechazo(id, rechazoDetails);
        return new ResponseEntity<>(updatedRechazo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRechazo(@PathVariable Long id) {
        rechazoLogic.deleteRechazo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Rechazo>> getAllRechazos() {
        List<Rechazo> rechazos = rechazoLogic.getAllRechazos();
        return new ResponseEntity<>(rechazos, HttpStatus.OK);
    }

    // Endpoint específico para rechazar a un usuario
    @PostMapping("/reject")
    public ResponseEntity<Rechazo> rechazarUsuario(@RequestParam Long usuarioId, @RequestParam Long rechazadoUsuarioId) {
        Rechazo rechazo = rechazoLogic.rechazarUsuario(usuarioId, rechazadoUsuarioId);
        return new ResponseEntity<>(rechazo, HttpStatus.CREATED);
    }
}
