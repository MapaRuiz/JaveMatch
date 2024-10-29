package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.RechazoRepository;
import gajudama.javematch.model.Rechazo;
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

}
