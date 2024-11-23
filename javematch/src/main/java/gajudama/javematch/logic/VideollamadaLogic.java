package gajudama.javematch.logic;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.VideollamadaRepository;
import gajudama.javematch.model.Juego;
import gajudama.javematch.model.UserMatch;
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

    @Transactional
    public Videollamada createVideollamada(UserMatch match) {
        Videollamada videollamada = new Videollamada();
    videollamada.setFechaVideollamada(LocalDateTime.now());
        videollamada.setEstado("pending"); // Estado inicial      
        videollamada.setMatch(match);
        videollamada.setJuegos(new ArrayList<>());

        return videollamadaRepository.save(videollamada);
    }

    @Transactional
    public Videollamada addJuegoToVideollamada(Long videollamadaId, Juego juego) {
        Videollamada videollamada = videollamadaRepository.findById(videollamadaId)
            .orElseThrow(() -> new RuntimeException("Videollamada not found"));

        List<Juego> juegos = videollamada.getJuegos();
        if (juegos == null) {
            juegos = new ArrayList<>();
        }
        juegos.add(juego);
        videollamada.setJuegos(juegos);

        return videollamadaRepository.save(videollamada);
    }
    
}
