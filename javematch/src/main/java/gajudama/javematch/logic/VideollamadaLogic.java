package gajudama.javematch.logic;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.VideollamadaRepository;
import gajudama.javematch.model.Videollamada;

@Service
public class VideollamadaLogic {
    @Autowired
    private VideollamadaRepository videollamadaRepository;

    public Videollamada createVideollamada(Videollamada videollamada) {
        return videollamadaRepository.save(videollamada);
    }

    public Optional<Videollamada> getVideollamadaById(Long id) {
        return videollamadaRepository.findById(id);
    }

    public Videollamada updateVideollamada(Long id, Videollamada videollamadaDetails) {
        return videollamadaRepository.findById(id).map(videollamada -> {
            videollamada.setFechaVideollamada(videollamadaDetails.getFechaVideollamada());
            videollamada.setEstado(videollamadaDetails.getEstado());
            videollamada.setDuracion(videollamadaDetails.getDuracion());
            videollamada.setJuegos(videollamadaDetails.getJuegos());
            return videollamadaRepository.save(videollamada);
        }).orElseThrow(() -> new RuntimeException("Videollamada not found"));
    }

    public void deleteVideollamada(Long id) {
        videollamadaRepository.deleteById(id);
    }

    public List<Videollamada> getAllVideollamadas() {
        return videollamadaRepository.findAll();
    }
    
}
