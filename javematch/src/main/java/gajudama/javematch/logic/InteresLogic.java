package gajudama.javematch.logic;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.InteresRepository;
import gajudama.javematch.model.Interes;

@Service
public class InteresLogic {

    @Autowired
    private InteresRepository interesRepository;

    public Optional<Interes> getInteresById(Long id) {
        return interesRepository.findById(id);
    }
}