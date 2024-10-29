package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gajudama.javematch.accesoDatos.PlanRepository;
import gajudama.javematch.model.Plan;
import jakarta.transaction.Transactional;

@Service
public class PlanLogic {
    @Autowired
    private PlanRepository planRepository;

    @Transactional
    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Optional<Plan> getPlansById(Long id) {
        return planRepository.findById(id);
    }

    @Transactional
    public Plan updatePlan(Long id, Plan planDetails) {
        return planRepository.findById(id).map(plan -> {
            plan.setMaxLikes(planDetails.getMaxLikes());
            plan.setNombre(planDetails.getNombre());
            plan.setUsuarios(planDetails.getUsuarios());
            return planRepository.save(plan);
        }).orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    @Transactional
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

    public List<Plan> getAllPlanes() {
        return planRepository.findAll();
    }

}
