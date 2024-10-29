package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.NotificacionRepository;
import gajudama.javematch.model.Notificacion;

@Service
public class NotificacionLogic {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public Notificacion createNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public Optional<Notificacion> getNotificacionesById(Long id) {
        return notificacionRepository.findById(id);
    }

    public Notificacion updateNotificacion(Long id, Notificacion notificacionDetails) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacion.setEstadoLectura(notificacionDetails.getEstadoLectura());
            notificacion.setFechaEnvio(notificacionDetails.getFechaEnvio());
            notificacion.setMensaje(notificacionDetails.getMensaje());
            notificacion.setUsuarioNotificado(notificacionDetails.getUsuarioNotificado());
            return notificacionRepository.save(notificacion);
        }).orElseThrow(() -> new RuntimeException("Notificacion not found"));
    }

    public void deleteNotificacion(Long id) {
        notificacionRepository.deleteById(id);
    }

    public List<Notificacion> getAllNotificaciones() {
        return notificacionRepository.findAll();
    }
    
}
