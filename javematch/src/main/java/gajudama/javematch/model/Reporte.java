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
    private Long id;

    @ManyToOne
    private Usuario autor;

    @ManyToOne 
    private Usuario reportado;

    private String tipo;
    private String descripcion;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRep;

    @ManyToOne
    private Videollamada videollamada;
}
