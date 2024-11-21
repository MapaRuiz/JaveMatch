package gajudama.javematch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "interesId")
public class Interes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interesId;

    private String nombre;

    @ManyToMany(mappedBy = "intereses")
    @JsonIgnore
    private List<Usuario> usuarios;
    @Override
    public String toString() {
        return "Interes{" +
                "interesId=" + interesId +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
