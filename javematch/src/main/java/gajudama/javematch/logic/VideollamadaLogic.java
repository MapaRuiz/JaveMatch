package gajudama.javematch.logic;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.VideollamadaRepository;
import gajudama.javematch.model.Videollamada;

@Service
public class VideollamadaLogic {
    private final VideollamadaRepository videollamadaRepository;

    public VideollamadaLogic(VideollamadaRepository videollamadaRepository) {
        this.videollamadaRepository = videollamadaRepository;
    }

    public List<Videollamada> getAllVideollamadas() {
        return videollamadaRepository.findAll();
    }

    public Optional<Videollamada> getVideollamadaById(Long id) {
        return videollamadaRepository.findById(id);
    }

    public Videollamada createVideollamada(Videollamada videollamada) {
        return videollamadaRepository.save(videollamada);
    }

    public Videollamada updateVideollamada(Long id, Videollamada updatedVideollamada) {
        return videollamadaRepository.findById(id).map(v -> {
            v.setDuracion(updatedVideollamada.getDuracion());
            v.setFechaVideollamada(updatedVideollamada.getFechaVideollamada());
            v.setEstado(updatedVideollamada.getEstado());
            return videollamadaRepository.save(v);
        }).orElseGet(() -> {
            updatedVideollamada.setId(id);
            return videollamadaRepository.save(updatedVideollamada);
        });
    }

    public void deleteVideollamada(Long id) {
        videollamadaRepository.deleteById(id);
    }
    
}
