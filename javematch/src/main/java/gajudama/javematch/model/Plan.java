package gajudama.javematch.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Plan_id;

    private String nombre;  // Plan name
    private Integer maxLikes;  // Max likes allowed for this plan

    // Optional: Users associated with this plan
    @OneToMany(mappedBy = "plan")
    private List<Usuario> usuarios;  // List of users assigned to this plan
}
