package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.NotificacionLogic;
import gajudama.javematch.model.Notificacion;

import java.util.List;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionLogic notificacionLogic;

    @PostMapping
    public ResponseEntity<Notificacion> createNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion newNotificacion = notificacionLogic.createNotificacion(notificacion);
        return new ResponseEntity<>(newNotificacion, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> getNotificacionById(@PathVariable Long id) {
        return notificacionLogic.getNotificacionesById(id)
            .map(notificacion -> new ResponseEntity<>(notificacion, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> updateNotificacion(@PathVariable Long id, @RequestBody Notificacion notificacionDetails) {
        Notificacion updatedNotificacion = notificacionLogic.updateNotificacion(id, notificacionDetails);
        return new ResponseEntity<>(updatedNotificacion, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        notificacionLogic.deleteNotificacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Notificacion>> getAllNotificaciones() {
        List<Notificacion> notificaciones = notificacionLogic.getAllNotificaciones();
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    // Enviar una notificación genérica
    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestParam Long usuarioId, @RequestParam String mensaje) {
        notificacionLogic.sendNotification(usuarioId, mensaje, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Enviar solicitud de amistad
    @PostMapping("/sendFriendRequest")
    public ResponseEntity<Void> sendFriendRequestNotification(@RequestParam Long usuarioId, @RequestParam Long likedUsuarioId) {
        notificacionLogic.sendFriendRequestNotification(usuarioId, likedUsuarioId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
