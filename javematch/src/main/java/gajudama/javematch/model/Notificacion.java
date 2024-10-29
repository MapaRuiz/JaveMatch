package gajudama.javematch.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Notificacion_id;

    private String mensaje;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;  // Date the notification was sent

    private Boolean estadoLectura;  // Whether the notification has been read

    @ManyToOne
    private Usuario usuarioNotificado;  // The user who received the notification
}
