package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.RechazoRepository;
import gajudama.javematch.model.Rechazo;
import gajudama.javematch.model.Usuario;
import jakarta.transaction.Transactional;

@Service
public class RechazoLogic {
    @Autowired
    private RechazoRepository rechazoRepository;

    @Transactional
    public Rechazo createRechazo(Rechazo rechazo) {
        return rechazoRepository.save(rechazo);
    }

    public Optional<Rechazo> getRechazoById(Long id) {
        return rechazoRepository.findById(id);
    }

    @Transactional
    public Rechazo updateRechazo(Long id, Rechazo rechazoDetails) {
        return rechazoRepository.findById(id).map(rechazo -> {
            rechazo.setFechaRechazo(rechazoDetails.getFechaRechazo());
            rechazo.setRechazadoUsuario(rechazoDetails.getRechazadoUsuario());
            rechazo.setUsuarioRechazo(rechazoDetails.getUsuarioRechazo());
            return rechazoRepository.save(rechazo);
        }).orElseThrow(() -> new RuntimeException("Rechazo not found"));
    }

    @Transactional
    public void deleteRechazo(Long id) {
        rechazoRepository.deleteById(id);
    }

    public List<Rechazo> getAllRechazos() {
        return rechazoRepository.findAll();
    }

    @Autowired
    private UsuarioLogic usuarioLogic;

    @Transactional
    public Rechazo rechazarUsuario(Long usuarioId, Long rechazadoUsuarioId) {
        Usuario usuario = usuarioLogic.getUsuarioById(usuarioId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Usuario rechazadoUsuario = usuarioLogic.getUsuarioById(rechazadoUsuarioId)
            .orElseThrow(() -> new RuntimeException("User to reject not found"));

        Rechazo rechazo = new Rechazo();
        rechazo.setUsuarioRechazo(usuario);
        rechazo.setRechazadoUsuario(rechazadoUsuario);
        rechazoRepository.save(rechazo);

        // Update user's rejectionsGiven and rejectionsReceived
        usuario.getRejectionsGiven().add(rechazo);
        rechazadoUsuario.getRejectionsReceived().add(rechazo);
        usuarioLogic.updateRejectionsGiven(usuarioId, usuario.getRejectionsGiven());
        usuarioLogic.updateRejectionsReceived(rechazadoUsuarioId, rechazadoUsuario.getRejectionsReceived());

        return rechazo;
    }

}
