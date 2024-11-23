package gajudama.javematch.logic;

import java.util.Date;
import java.util.List;
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
    
    @Autowired
    private UsuarioLogic usuarioLogic;

    @Transactional
    public void sendNotification(Long usuarioId, String mensaje, boolean isRead) {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioNotificado(usuario);
        notificacion.setMensaje(mensaje);
        notificacion.setFechaEnvio(new Date());
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
    
    public List<Notificacion> getNotificacionesByUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioNotificado(usuarioId);
    }

}
