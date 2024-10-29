package gajudama.javematch.model;

import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Entity
public class Videollamada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Videollamada_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVideollamada;

    private String estado;

    private Integer duracion; // Duración en minutos

    @ManyToMany
    @JoinTable(
        name = "videollamada_juego",
        joinColumns = @JoinColumn(name = "videollamada_id"),
        inverseJoinColumns = @JoinColumn(name = "juego_id")
    )
    private List<Juego> juegos;

    @OneToMany(mappedBy = "videollamada_Match")
    private List<UserMatch> matches;  // Matches linked to this Videollamada

    @OneToMany(mappedBy = "videollamada")
    private List<Reporte> reportes;  // Reports linked to this Videollamada
}
