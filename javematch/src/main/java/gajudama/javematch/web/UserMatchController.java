package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gajudama.javematch.logic.NotificacionLogic;
import gajudama.javematch.logic.UserMatchLogic;
import gajudama.javematch.model.UserMatch;

import java.util.List;

@RestController
@RequestMapping("/api/usermatch")
public class UserMatchController {

    @Autowired
    private UserMatchLogic userMatchLogic;

 
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
        // Verifica si los parámetros están siendo recibidos correctamente
        System.out.println("Solicitud para crear match entre usuarioId: " + usuarioId + " y likedUsuarioId: " + likedUsuarioId);
    
        UserMatch match = userMatchLogic.createMatch(usuarioId, likedUsuarioId);
        System.out.println("Match creado: " + match);
    
        return new ResponseEntity<>(match, HttpStatus.CREATED);
    }
    

    // Endpoint específico para crear un match aleatorio
    @PostMapping("/randomMatch")
    public ResponseEntity<UserMatch> randomMatch(@RequestParam Long usuarioId) {
        UserMatch match = userMatchLogic.randomMatch(usuarioId);
        return new ResponseEntity<>(match, HttpStatus.CREATED);
    }
    
    @Autowired
    NotificacionLogic notificacionLogic;

    @PostMapping("/accept/{likedUsuarioId}")
    public ResponseEntity<?> acceptUserAndNotify(@PathVariable Long likedUsuarioId, @RequestParam Long usuarioId) {
        try {
            // Crear el match entre los usuarios
            UserMatch match = userMatchLogic.createMatch(usuarioId, likedUsuarioId);
            System.out.println("Match creado: " + match);

            // Enviar una notificación al usuario aceptado
            String mensaje = "¡Felicidades! El usuario con ID " + usuarioId + " te ha aceptado como match.";
            notificacionLogic.sendMatchNotification(likedUsuarioId, mensaje);

            // Retornar el match creado y un mensaje de éxito
            return new ResponseEntity<>(match, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error al aceptar usuario y enviar notificación: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Endpoint para rechazar un usuario
    @PostMapping("/reject/{likedUsuarioId}")
    public ResponseEntity<String> rejectUser(@PathVariable Long likedUsuarioId) {
        // Lógica para rechazar al usuario (por ejemplo, notificarlo o actualizar su estado en la base de datos)
        System.out.println("Usuario con ID " + likedUsuarioId + " ha sido rechazado.");

        // Aquí podrías manejar la lógica de rechazo, por ejemplo, marcarlo como rechazado en la base de datos
        return new ResponseEntity<>("Usuario rechazado", HttpStatus.OK);
    }

    @GetMapping("/mutual/{userId}")
    public ResponseEntity<List<UserMatch>> getMutualMatches(@PathVariable Long userId) {
        try {
            List<UserMatch> mutualMatches = userMatchLogic.getMutualMatches(userId);
            return new ResponseEntity<>(mutualMatches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
