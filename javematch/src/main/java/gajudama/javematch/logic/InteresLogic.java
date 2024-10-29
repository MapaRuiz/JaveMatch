package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.InteresRepository;
import gajudama.javematch.model.Interes;

@Service
public class InteresLogic {

    private final InteresRepository interesRepository;

    public InteresLogic(InteresRepository interesRepository) {
        this.interesRepository = interesRepository;
    }

    public List<Interes> getAllIntereses() {
        return interesRepository.findAll();
    }

    public Optional<Interes> getInteresById(Long id) {
        return interesRepository.findById(id);
    }

    public Interes createInteres(Interes interes) {
        return interesRepository.save(interes);
    }

    public Interes updateInteres(Long id, Interes updatedInteres) {
        return interesRepository.findById(id).map(interes -> {
            interes.setNombre(updatedInteres.getNombre());
            return interesRepository.save(interes);
        }).orElseGet(() -> {
            updatedInteres.setId(id);
            return interesRepository.save(updatedInteres);
        });
    }

    public void deleteInteres(Long id) {
        interesRepository.deleteById(id);
    }

}
