package gajudama.javematch.logic;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.VideollamadaRepository;
import gajudama.javematch.model.Videollamada;
import jakarta.transaction.Transactional;

@Service
public class VideollamadaLogic {
    @Autowired
    private VideollamadaRepository videollamadaRepository;

    @Transactional
    public Videollamada createVideollamada(Videollamada videollamada) {
        return videollamadaRepository.save(videollamada);
    }

    public Optional<Videollamada> getVideollamadaById(Long id) {
        return videollamadaRepository.findById(id);
    }

    @Transactional
    public Videollamada updateVideollamada(Long id, Videollamada videollamadaDetails) {
        return videollamadaRepository.findById(id).map(videollamada -> {
            videollamada.setFechaVideollamada(videollamadaDetails.getFechaVideollamada());
            videollamada.setEstado(videollamadaDetails.getEstado());
            videollamada.setDuracion(videollamadaDetails.getDuracion());
            videollamada.setJuegos(videollamadaDetails.getJuegos());
            return videollamadaRepository.save(videollamada);
        }).orElseThrow(() -> new RuntimeException("Videollamada not found"));
    }

    @Transactional
    public void deleteVideollamada(Long id) {
        videollamadaRepository.deleteById(id);
    }

    public List<Videollamada> getAllVideollamadas() {
        return videollamadaRepository.findAll();
    }
    
}
