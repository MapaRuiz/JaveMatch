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
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Reporte_id;

    @ManyToOne
    private Usuario autor;  // The user who created the report

    @ManyToOne
    private Usuario reportado;  // The user being reported

    private String tipo;  // Type of report (e.g., spam, abuse)
    private String descripcion;  // Description of the report

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRep;  // Date and time of the report

    @ManyToOne
    private Videollamada videollamada;  // Optional videollamada related to the report
}
