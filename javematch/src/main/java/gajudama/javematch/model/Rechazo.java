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
public class Rechazo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Rechazo_id;

    @ManyToOne
    private Usuario usuarioRechazo;  // User who gives the rejection

    @ManyToOne
    private Usuario rechazadoUsuario; // User who receives the rejection

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRechazo;
}
