package gajudama.javematch.logic;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.NotificacionRepository;
import gajudama.javematch.model.Notificacion;
import gajudama.javematch.model.Usuario;

@Service
public class NotificacionLogic {
    private final NotificacionRepository notificacionRepository;

    public NotificacionLogic(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> getAllNotificaciones() {
        return notificacionRepository.findAll();
    }

    public Notificacion createNotificacion(Usuario usuario, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioNotificado(usuario);
        notificacion.setMensaje(mensaje);
        notificacion.setFechaEnvio(new Date());
        notificacion.setEstadoLectura(false);
        return notificacionRepository.save(notificacion);
    }

    public void markAsRead(Long id) {
        notificacionRepository.findById(id).ifPresent(notificacion -> {
            notificacion.setEstadoLectura(true);
            notificacionRepository.save(notificacion);
        });
    }

    public void deleteNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }
    
}
