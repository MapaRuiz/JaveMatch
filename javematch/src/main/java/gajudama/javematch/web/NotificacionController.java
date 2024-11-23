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

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestParam Long usuarioId, @RequestParam String mensaje) {
        notificacionLogic.sendNotification(usuarioId, mensaje, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> getNotificacionesByUsuario(@PathVariable Long usuarioId) {
        List<Notificacion> notificaciones = notificacionLogic.getNotificacionesByUsuario(usuarioId);
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }
}
