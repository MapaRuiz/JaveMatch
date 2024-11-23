package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.InteresLogic;
import gajudama.javematch.model.Interes;

@RestController
@RequestMapping("/api/interes")
public class InteresController {

    @Autowired
    private InteresLogic interesLogic;

    @GetMapping("/{id}")
    public ResponseEntity<Interes> getInteresById(@PathVariable Long id) {
        return interesLogic.getInteresById(id)
            .map(interes -> new ResponseEntity<>(interes, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}