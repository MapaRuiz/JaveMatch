package gajudama.javematch.logic;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.JuegoRepository;
import gajudama.javematch.model.Juego;
import jakarta.transaction.Transactional;

@Service
public class JuegoLogic {

    @Autowired
    private JuegoRepository juegoRepository;

    @Transactional
    public Juego createJuego(Juego juego) {
        return juegoRepository.save(juego);
    }

    public Optional<Juego> getJuegosById(Long id) {
        return juegoRepository.findById(id);
    }
    
    public Optional<Juego> findByNombre(String nombre) {
        return juegoRepository.findByNombre(nombre);
    }

}
