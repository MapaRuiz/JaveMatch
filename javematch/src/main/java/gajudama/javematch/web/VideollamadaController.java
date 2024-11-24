package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gajudama.javematch.logic.UserMatchLogic;
import gajudama.javematch.logic.VideollamadaLogic;
import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Videollamada;
import gajudama.javematch.model.Juego;
import gajudama.javematch.model.Usuario;
import java.util.Optional;
import gajudama.javematch.model.UserMatch;
import java.util.HashMap;


import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/videollamada")
public class VideollamadaController {

    @Autowired
    private VideollamadaLogic videollamadaLogic;
    @Autowired
    private UserMatchLogic userMatchLogic;
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

   /* @PostMapping("/createWithMatch")
    public ResponseEntity<?> createVideollamadaWithMatch(@RequestParam Long matchId) {
        try {
            // Obtener el match por ID
            UserMatch match = userMatchLogic.getMatchById(matchId)
                .orElseThrow(() -> new RuntimeException("Match no encontrado"));
        
            // Crear la videollamada
            Videollamada videollamada = new Videollamada();
            videollamada.setFechaVideollamada(LocalDateTime.now());
            videollamada.setEstado("Iniciada");
            Videollamada createdVideollamada = videollamadaLogic.createVideollamada(videollamada);
        
            // Establecer el ID de la videollamada en el match
            match.setVideollamada_Match(createdVideollamada);
            userMatchLogic.updateMatch(matchId, match);
        
            // Crear un objeto de respuesta con los detalles
            return ResponseEntity.ok(Map.of(
                "videollamada", createdVideollamada,
                "user1", match.getUser1(),
                "user2", match.getUser2()
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

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
    @PostMapping("/{videollamadaId}/addJuego")
    public ResponseEntity<Videollamada> addJuegoToVideollamada(@PathVariable Long videollamadaId, @RequestBody Juego juego) {
        Videollamada updatedVideollamada = videollamadaLogic.addJuegoToVideollamada(videollamadaId, juego);
        return new ResponseEntity<>(updatedVideollamada, HttpStatus.OK);
    }

   @GetMapping("/match/{matchId}")
public ResponseEntity<?> getVideollamadaByMatchId(@PathVariable Long matchId) {
  Videollamada videollamada = videollamadaLogic.getVideollamadaByMatchId(matchId);

    if (videollamada != null) {
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