package gajudama.javematch.logic;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.PlanRepository;
import gajudama.javematch.model.Plan;

//Clase que maneja la logica de la clase Plan
@Service
public class PlanLogic {
    @Autowired
    private PlanRepository planRepository;

    public Optional<Plan> getPlansById(Long id) {
        return planRepository.findById(id);
    }
}
