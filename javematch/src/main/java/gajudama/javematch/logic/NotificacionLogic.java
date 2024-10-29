package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.NotificacionRepository;
import gajudama.javematch.model.Notificacion;
import gajudama.javematch.model.Usuario;
import jakarta.transaction.Transactional;

@Service
public class NotificacionLogic {
    @Autowired
    private NotificacionRepository notificacionRepository;

    @Transactional
    public Notificacion createNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public Optional<Notificacion> getNotificacionesById(Long id) {
        return notificacionRepository.findById(id);
    }

    @Transactional
    public Notificacion updateNotificacion(Long id, Notificacion notificacionDetails) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacion.setEstadoLectura(notificacionDetails.getEstadoLectura());
            notificacion.setFechaEnvio(notificacionDetails.getFechaEnvio());
            notificacion.setMensaje(notificacionDetails.getMensaje());
            notificacion.setUsuarioNotificado(notificacionDetails.getUsuarioNotificado());
            return notificacionRepository.save(notificacion);
        }).orElseThrow(() -> new RuntimeException("Notificacion not found"));
    }

    @Transactional
    public void deleteNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }

    public List<Notificacion> getAllNotificaciones() {
        return notificacionRepository.findAll();
    }
    
    @Autowired
    private UsuarioLogic usuarioLogic;

    @Transactional
    public void sendNotification(Long usuarioId, String mensaje, boolean isRead) {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioNotificado(usuario);
        notificacion.setMensaje(mensaje);
        notificacion.setEstadoLectura(isRead);
        notificacionRepository.save(notificacion);
    }

    @Transactional
    public void sendFriendRequestNotification(Long usuarioId, Long likedUsuarioId) {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Usuario likedUsuario = usuarioLogic.getUsuarioById(likedUsuarioId)
            .orElseThrow(() -> new RuntimeException("Liked user not found"));

        sendNotification(usuarioId, "Would you like to be friends with " + likedUsuario.getNombre() + "?", false);
        sendNotification(likedUsuarioId, "Would you like to be friends with " + usuario.getNombre() + "?", false);
    }
    
}
