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
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserLike_id;

    @ManyToOne
    private Usuario usuarioLike;  // User who gives the like

    @ManyToOne
    private Usuario likedUsuario; // User who receives the like

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLike;
}
