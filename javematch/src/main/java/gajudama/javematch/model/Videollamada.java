package gajudama.javematch.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Data
@Entity
public class Videollamada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Videollamada_id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaVideollamada;

    private String estado;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "videollamada_juego",
        joinColumns = @JoinColumn(name = "videollamada_id"),
        inverseJoinColumns = @JoinColumn(name = "juego_id")
    )
    
    private List<Juego> juegos = new ArrayList<>();


    @OneToOne
     @JsonManagedReference 
    @JoinColumn(name = "user_match_id") 
    private UserMatch match;  // Match linked to this Videollamada

public String toString() {
    return "Videollamada{" +
            "Videollamada_id=" + Videollamada_id +
            ", fechaVideollamada=" + fechaVideollamada +
            ", estado='" + estado + '\'' +
            ", juegos=" + (juegos != null ? juegos.stream().map(Juego::getNombre).toList() : "null") +
            ", match=" + (match != null ? "UserMatch{id=" + match.getUserMatchId() + "}" : "null") +
            '}';
}

}



