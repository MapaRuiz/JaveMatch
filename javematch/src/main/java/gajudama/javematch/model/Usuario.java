package gajudama.javematch.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    private String nombre;
    
    @Column(unique = true)
    private String correo;
    
    // Matches as user1 and user2
    @OneToMany(mappedBy = "user1")
    private List<UserMatch> matchesAsUser1;

    @OneToMany(mappedBy = "user2")
    private List<UserMatch> matchesAsUser2;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_interes",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "interes_id")
    )   
    @JsonIgnore
    private List<Interes> intereses = new ArrayList<>();
    
    // Notifications received by the user
    @OneToMany(mappedBy = "usuarioNotificado")
    private List<Notificacion> notifications;

    // Many users can share the same plan
    @ManyToOne
    @JoinColumn(name = "plan_id") 
    private Plan plan;

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + userId +
            ", nombre='" + nombre + '\'' +
            '}';
        
    }
}
