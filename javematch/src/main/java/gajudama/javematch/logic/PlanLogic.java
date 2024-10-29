package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.PlanRepository;
import gajudama.javematch.model.Plan;

@Service
public class PlanLogic {
    private final PlanRepository planRepository;

    public PlanLogic(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> getAllPlanes() {
        return planRepository.findAll();
    }

    public Optional<Plan> getPlanById(Long id) {
        return planRepository.findById(id);
    }

    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Plan updatePlan(Long id, Plan updatedPlan) {
        return planRepository.findById(id).map(plan -> {
            plan.setNombre(updatedPlan.getNombre());
            plan.setMaxLikes(updatedPlan.getMaxLikes());
            return planRepository.save(plan);
        }).orElseGet(() -> {
            updatedPlan.setId(id);
            return planRepository.save(updatedPlan);
        });
    }

    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

}
