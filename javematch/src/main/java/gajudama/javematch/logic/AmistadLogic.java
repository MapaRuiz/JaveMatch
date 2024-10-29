package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.AmistadRepository;
import gajudama.javematch.model.Amistad;
import gajudama.javematch.model.UserMatch;
import jakarta.transaction.Transactional;

@Service
public class AmistadLogic {

    @Autowired
    private AmistadRepository amistadRepository;
    @Autowired
    private UserMatchLogic userMatchLogic;
    @Autowired
    private UsuarioLogic usuarioLogic;

    @Transactional
    public Amistad createAmistad(Amistad amistad) {
        return amistadRepository.save(amistad);
    }

    public Optional<Amistad> getAmistadById(Long id) {
        return amistadRepository.findById(id);
    }

    @Transactional
    public Amistad updateAmistad(Long id, Amistad amistadDetails) {
        return amistadRepository.findById(id).map(amistad -> {
            amistad.setAmigoUsuario(amistadDetails.getAmigoUsuario());
            amistad.setUsuarioAmistad(amistadDetails.getUsuarioAmistad());
            return amistadRepository.save(amistad);
        }).orElseThrow(() -> new RuntimeException("Amistad not found"));
    }

    @Transactional
    public void deleteAmistad(Long id) {
        if (!amistadRepository.existsById(id)) {
            throw new RuntimeException("Amistad not found");
        }
        amistadRepository.deleteById(id);
    }

    public List<Amistad> getAllAmistades() {
        return amistadRepository.findAll();
    }

    @Transactional
    public Amistad confirmFriendship(Long matchId, boolean isFriend) {
        UserMatch match = userMatchLogic.getMatchById(matchId)
            .orElseThrow(() -> new RuntimeException("Match not found"));

        if (isFriend) {
            match.setAmistad(true);
            Amistad amistad = new Amistad();
            amistad.setUsuarioAmistad(match.getUser1());
            amistad.setAmigoUsuario(match.getUser2());
            amistadRepository.save(amistad);

            // Update user's friendships
            match.getUser1().getFriendshipsAsUser().add(amistad);
            match.getUser2().getFriendshipsAsFriend().add(amistad);
            usuarioLogic.updateFriendshipsAsUser(match.getUser1().getUser_id(), match.getUser1().getFriendshipsAsUser());
            usuarioLogic.updateFriendshipsAsFriend(match.getUser2().getUser_id(), match.getUser2().getFriendshipsAsFriend());

            return amistad;
        }
        return null;
    }

}