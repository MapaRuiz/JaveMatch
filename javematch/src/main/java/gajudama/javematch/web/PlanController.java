package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.PlanLogic;
import gajudama.javematch.model.Plan;

import java.util.List;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    private PlanLogic planLogic;

    @PostMapping
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        Plan newPlan = planLogic.createPlan(plan);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        return planLogic.getPlansById(id)
            .map(plan -> new ResponseEntity<>(plan, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody Plan planDetails) {
        Plan updatedPlan = planLogic.updatePlan(id, planDetails);
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planLogic.deletePlan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlanes() {
        List<Plan> planes = planLogic.getAllPlanes();
        return new ResponseEntity<>(planes, HttpStatus.OK);
    }
    
}
