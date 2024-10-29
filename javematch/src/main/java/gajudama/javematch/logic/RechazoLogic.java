package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.RechazoRepository;
import gajudama.javematch.model.Rechazo;

@Service
public class RechazoLogic {
    private final RechazoRepository rechazoRepository;

    public RechazoLogic(RechazoRepository rechazoRepository) {
        this.rechazoRepository = rechazoRepository;
    }

    public List<Rechazo> getAllRechazos() {
        return rechazoRepository.findAll();
    }

    public Optional<Rechazo> getRechazoById(Long id) {
        return rechazoRepository.findById(id);
    }

    public Rechazo createRechazo(Rechazo rechazo) {
        return rechazoRepository.save(rechazo);
    }

    public Rechazo updateRechazo(Long id, Rechazo updatedRechazo) {
        return rechazoRepository.findById(id).map(rechazo -> {
            rechazo.setUsuarioRechazo(updatedRechazo.getUsuarioRechazo());
            rechazo.setRechazadoUsuario(updatedRechazo.getRechazadoUsuario());
            rechazo.setFechaRechazo(updatedRechazo.getFechaRechazo());
            return rechazoRepository.save(rechazo);
        }).orElseGet(() -> {
            updatedRechazo.setId(id);
            return rechazoRepository.save(updatedRechazo);
        });
    }

    public void deleteRechazo(Long id) {
        rechazoRepository.deleteById(id);
    }

}
