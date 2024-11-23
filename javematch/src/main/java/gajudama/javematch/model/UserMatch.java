package gajudama.javematch.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class UserMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserMatchId;

    @ManyToOne
    @JoinColumn(name = "user1_user_id") 
    private Usuario user1;  

    @ManyToOne
    @JoinColumn(name = "user2_user_id") 
    private Usuario user2;  // Second user in the match

    @Temporal(TemporalType.DATE)
    private Date fechaMatch;  // Match date

    @OneToOne
    @JoinColumn(name = "videollamada_id", referencedColumnName = "Videollamada_id")
    private Videollamada videollamada_Match;  // Videollamada associated with the match
   
}
