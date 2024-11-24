package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.VideollamadaLogic;
import gajudama.javematch.accesoDatos.JuegoRepository;
import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Videollamada;
import gajudama.javematch.model.Juego;
import gajudama.javematch.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videollamada")
public class VideollamadaController {

    @Autowired
    private VideollamadaLogic videollamadaLogic;
    @Autowired
    private UsuarioLogic userLogic;

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

   @PostMapping("/createWithMatch")
public ResponseEntity<Map<String, Long>> createVideollamada(@RequestParam Long matchId) {
    try {
        Videollamada videollamada = videollamadaLogic.createVideollamada(matchId);
        Map<String, Long> response = new HashMap<>();
        response.put("id", videollamada.getVideollamada_id());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    
    // Endpoint específico para agregar un juego a una videollamada
    @Autowired
    private JuegoRepository juegoRepository;
    @PostMapping("/{videollamadaId}/addJuego")
    public Videollamada addJuegoToVideollamada(
    @PathVariable Long videollamadaId,
    @RequestBody Juego juego) {

        // Fetch the videollamada
        Videollamada videollamada = videollamadaLogic.getVideollamadaById(videollamadaId)
            .orElseThrow(() -> new RuntimeException("Videollamada not found"));

        // Verifica si el objeto match contiene user1
        if (videollamada.getMatch() == null || videollamada.getMatch().getUser1() == null) {
        throw new RuntimeException("Error: El objeto Match o user1 está incompleto.");
        }
        // Check if the juego already exists in the database by name
        Juego existingJuego = juegoRepository.findByNombre(juego.getNombre())
        .orElseGet(() -> juegoRepository.save(juego)); // Save the juego if it doesn't exist

        // Add the game to the Videollamada's list of games
        List<Juego> juegos = videollamada.getJuegos();
        if (!juegos.contains(existingJuego)) { // Avoid duplicate games
            juegos.add(existingJuego);
        }

        // Update the list of juegos in the Videollamada
        videollamada.setJuegos(juegos);

        videollamada = videollamadaLogic.createVideollamada(videollamada);

        // Force a fetch to ensure the persistence context is updated
        return videollamada = videollamadaLogic.getVideollamadaById(videollamada.getVideollamada_id())
        .orElseThrow(() -> new RuntimeException("Failed to retrieve updated videollamada"));
    }

   @GetMapping("/match/{matchId}")
public ResponseEntity<?> getVideollamadaByMatchId(@PathVariable Long matchId) {
   Videollamada videollamada = videollamadaLogic.getVideollamadaByMatchId(matchId);

    if (videollamada != null) {
        // Ensure user1 is fully loaded
        if (videollamada.getMatch().getUser1() != null &&
            videollamada.getMatch().getUser1().getUserId() instanceof Long) {
            Usuario user1 = userLogic.getUsuarioporID(videollamada.getMatch().getUser1().getUserId());
            if (user1 != null) {
                videollamada.getMatch().setUser1(user1);
            }
        }
        // Asegúrate de cargar los datos completos de user2 si solo está el ID
        if (videollamada.getMatch().getUser2() != null && 
            videollamada.getMatch().getUser2().getUserId() instanceof Long) {
            Usuario user2 = userLogic.getUsuarioporID(videollamada.getMatch().getUser2().getUserId());
            if (user2 != null) {
                videollamada.getMatch().setUser2(user2);
            }
        }

        // Depuración: verifica los datos cargados
        System.out.println("Videollamada retornada: " + videollamada);

        return ResponseEntity.ok(videollamada);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}
}