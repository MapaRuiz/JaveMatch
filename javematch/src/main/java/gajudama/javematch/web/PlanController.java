package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.PlanLogic;
import gajudama.javematch.model.Plan;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    private PlanLogic planLogic;

    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        return planLogic.getPlansById(id)
            .map(plan -> new ResponseEntity<>(plan, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}