package gajudama.javematch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Amistad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Amistad_id;

    @ManyToOne
    private Usuario usuarioAmistad; // The user initiating the friendship

    @ManyToOne
    private Usuario amigoUsuario;   // The friend user
}
