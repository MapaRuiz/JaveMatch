package gajudama.javematch.logic;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.VideollamadaRepository;
import gajudama.javematch.accesoDatos.UserMatchRepository;
import gajudama.javematch.model.Juego;
import gajudama.javematch.model.UserMatch;
import gajudama.javematch.model.Videollamada;
import jakarta.transaction.Transactional;

@Service
public class VideollamadaLogic {
    @Autowired
    private VideollamadaRepository videollamadaRepository;
     @Autowired
    private UserMatchRepository userMatchRepository;

    @Transactional
    public Videollamada createVideollamada(Videollamada videollamada) {
        return videollamadaRepository.save(videollamada);
    }

    public Optional<Videollamada> getVideollamadaById(Long id) {
        return videollamadaRepository.findById(id);
    }

    // Método que obtiene la videollamada asociada a un match
    public Videollamada  getVideollamadaByMatchId(Long matchId) {
        Optional <Videollamada> videoOptional = videollamadaRepository.findByMatch_UserMatchId(matchId);
        return videoOptional.get();
        
    }

    @Transactional
    public Videollamada updateVideollamada(Long id, Videollamada updatedDetails) {
    return videollamadaRepository.findById(id).map(existingVideollamada -> {
        // Update the relevant fields from `updatedDetails` to `existingVideollamada`
        if (updatedDetails.getFechaVideollamada() != null) {
            existingVideollamada.setFechaVideollamada(updatedDetails.getFechaVideollamada());
        }
        if (updatedDetails.getEstado() != null) {
            existingVideollamada.setEstado(updatedDetails.getEstado());
        }
        if (updatedDetails.getJuegos() != null && !updatedDetails.getJuegos().isEmpty()) {
            existingVideollamada.setJuegos(updatedDetails.getJuegos());
        }
        // Persist the changes
        return videollamadaRepository.save(existingVideollamada);
    }).orElseThrow(() -> new RuntimeException("Videollamada not found with id: " + id));
}

    @Transactional
    public void deleteVideollamada(Long id) {
        videollamadaRepository.deleteById(id);
    }

    public List<Videollamada> getAllVideollamadas() {
        return videollamadaRepository.findAll();
    }

   @Transactional
public Videollamada createVideollamada(Long matchId) {
      UserMatch userMatch = userMatchRepository.findById(matchId)
            .orElseThrow(() -> new RuntimeException("Match no encontrado"));

      Optional<Videollamada> existingVideollamada = videollamadaRepository.findByMatch(userMatch);
    if (existingVideollamada.isPresent()) {
        userMatch.setVideollamada_Match(existingVideollamada.get());
       userMatchRepository.save(userMatch);
        return existingVideollamada.get(); // Devuelve la existente si ya hay una

    }

  

    // Crear la videollamada y guardar su referencia
    Videollamada videollamada = new Videollamada();
    videollamada.setMatch(userMatch);
    videollamada.setEstado("Iniciada");
    videollamada.setFechaVideollamada(LocalDateTime.now());
     // Guarda la nueva videollamada
    Videollamada nuevaVideollamada = videollamadaRepository.save(videollamada);

    // Actualizar el campo videollamada_id en UserMatch
    userMatch.setVideollamada_Match(videollamada);
    userMatchRepository.save(userMatch);

    return nuevaVideollamada;
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
