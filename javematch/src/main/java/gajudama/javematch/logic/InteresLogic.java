package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.InteresRepository;
import gajudama.javematch.model.Interes;

@Service
public class InteresLogic {

     @Autowired
    private InteresRepository interesRepository;

    public Interes createInteres(Interes interes) {
        return interesRepository.save(interes);
    }

    public Optional<Interes> getInteresById(Long id) {
        return interesRepository.findById(id);
    }

    public Interes updateInteres(Long id, Interes interesDetails) {
        return interesRepository.findById(id).map(interes -> {
            interes.setNombre(interesDetails.getNombre());
            interes.setUsuarios(interesDetails.getUsuarios());
            return interesRepository.save(interes);
        }).orElseThrow(() -> new RuntimeException("Interes not found"));
    }

    public void deleteInteres(Long id) {
        interesRepository.deleteById(id);
    }

    public List<Interes> getAllIntereses() {
        return interesRepository.findAll();
    }

}
