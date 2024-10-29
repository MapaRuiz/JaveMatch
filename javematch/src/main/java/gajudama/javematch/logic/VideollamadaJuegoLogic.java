package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.VideollamadaJuegoRepository;
import gajudama.javematch.model.VideollamadaJuego;

@Service
public class VideollamadaJuegoLogic {
    private final VideollamadaJuegoRepository videollamadaJuegoRepository;

    public VideollamadaJuegoLogic(VideollamadaJuegoRepository videollamadaJuegoRepository) {
        this.videollamadaJuegoRepository = videollamadaJuegoRepository;
    }

    public List<VideollamadaJuego> getAllVideollamadaJuegos() {
        return videollamadaJuegoRepository.findAll();
    }

    public Optional<VideollamadaJuego> getVideollamadaJuegoById(Long id) {
        return videollamadaJuegoRepository.findById(id);
    }

    public VideollamadaJuego createVideollamadaJuego(VideollamadaJuego videollamadaJuego) {
        return videollamadaJuegoRepository.save(videollamadaJuego);
    }

    public VideollamadaJuego updateVideollamadaJuego(Long id, VideollamadaJuego updatedVideollamadaJuego) {
        return videollamadaJuegoRepository.findById(id).map(vj -> {
            vj.setVideollamadaJuego(updatedVideollamadaJuego.getVideollamadaJuego());
            vj.setJuegoVideollamada(updatedVideollamadaJuego.getJuegoVideollamada());
            return videollamadaJuegoRepository.save(vj);
        }).orElseGet(() -> {
            updatedVideollamadaJuego.setId(id);
            return videollamadaJuegoRepository.save(updatedVideollamadaJuego);
        });
    }

    public void deleteVideollamadaJuego(Long id) {
        videollamadaJuegoRepository.deleteById(id);
    }
}
