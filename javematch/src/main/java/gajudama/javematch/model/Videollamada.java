package gajudama.javematch.model;

import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Videollamada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVideollamada;

    private String estado;

    private Integer duracion; // Duración en minutos

    @OneToMany(mappedBy = "videollamada")
    private List<Reporte> reportes;

    @OneToMany(mappedBy = "videollamadaJuego")
    private List<VideollamadaJuego> videollamadaJuegos;
}
