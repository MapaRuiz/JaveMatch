package gajudama.javematch.logic;

import java.util.List;
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

    @Transactional
    public Juego updateJuegos(Long id, Juego juegoDetails) {
        return juegoRepository.findById(id).map(juego -> {
            juego.setNombre(juegoDetails.getNombre());
            return juegoRepository.save(juego);
        }).orElseThrow(() -> new RuntimeException("Juego not found"));
    }

    @Transactional
    public void deleteJuego(Long id) {
        juegoRepository.deleteById(id);
    }

    public List<Juego> getAllJuegos() {
        return juegoRepository.findAll();
    }

}
