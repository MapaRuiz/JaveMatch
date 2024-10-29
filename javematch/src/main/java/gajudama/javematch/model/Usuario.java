package gajudama.javematch.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long User_id;
    
    private String nombre;
    
    @Column(unique = true)
    private String correo;
    
    // Matches as user1 and user2
    @OneToMany(mappedBy = "user1")
    private List<UserMatch> matchesAsUser1;

    @OneToMany(mappedBy = "user2")
    private List<UserMatch> matchesAsUser2;

    // Reports as author and as reported user
    @OneToMany(mappedBy = "autor")
    private List<Reporte> reportsAuthored;

    @OneToMany(mappedBy = "reportado")
    private List<Reporte> reportsReceived;

    @OneToMany(mappedBy = "usuarioLike")
    private List<UserLike> likesGiven;

    @OneToMany(mappedBy = "likedUsuario")
    private List<UserLike> likesReceived;

    @OneToMany(mappedBy = "usuarioRechazo")
    private List<Rechazo> rejectionsGiven;

    @OneToMany(mappedBy = "rechazadoUsuario")
    private List<Rechazo> rejectionsReceived;

    @OneToMany(mappedBy = "usuarioAmistad")
    private List<Amistad> friendshipsAsUser;

    @OneToMany(mappedBy = "amigoUsuario")
    private List<Amistad> friendshipsAsFriend;

    @ManyToMany
    @JoinTable(
        name = "usuario_interes",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "interes_id")
    )
    private List<Interes> intereses;
    
    // Notifications received by the user
    @OneToMany(mappedBy = "usuarioNotificado")
    private List<Notificacion> notifications;

    // Many users can share the same plan
    @ManyToOne
    private Plan plan;
}
