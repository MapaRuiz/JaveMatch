package gajudama.javematch.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    @Column(unique = true)
    private String correo;
    
    @OneToMany(mappedBy = "user1")
    private List<Match> matchesAsUser1;

    @OneToMany(mappedBy = "user2")
    private List<Match> matchesAsUser2;

    @OneToMany(mappedBy = "autor")
    private List<Reporte> reportsAsAutor;

    @OneToMany(mappedBy = "reportado")
    private List<Reporte> reportsAsReported;

    @OneToMany(mappedBy = "usuarioNotificado")
    private List<Notificacion> notificationsAsUser;

    @OneToMany(mappedBy = "usuarioLike")
    private List<Like> likesAsUsuarioLike;

    @OneToMany(mappedBy = "likedUsuario")
    private List<Like> likesAslikedUsuario;

    @OneToMany(mappedBy = "usuarioRechazo")
    private List<Rechazo> rechazosAsUsuarioRechazo;

    @OneToMany(mappedBy = "rechazadoUsuario")
    private List<Rechazo> rechazosAsrechazadoUsuario;

    @OneToMany(mappedBy = "usuarioAmistad")
    private List<Amistad> amistadesAsUser1;

    @OneToMany(mappedBy = "amigoUsuario")
    private List<Amistad> amistadesAsamigoUsuario;

    @OneToMany(mappedBy = "usuarioIntereses")
    private List<UsuarioInteres> usuarioIntereses;
    
    @OneToOne
    private Plan plan;
}
