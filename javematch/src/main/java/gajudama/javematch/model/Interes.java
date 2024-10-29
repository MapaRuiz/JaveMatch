package gajudama.javematch.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Interes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Interes_id;

    private String nombre;

    @ManyToMany(mappedBy = "intereses")
    private List<Usuario> usuarios;
}
