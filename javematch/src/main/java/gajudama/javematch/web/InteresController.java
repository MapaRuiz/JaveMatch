package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.InteresLogic;
import gajudama.javematch.model.Interes;

import java.util.List;

@RestController
@RequestMapping("/api/interes")
public class InteresController {

    @Autowired
    private InteresLogic interesLogic;

    @PostMapping
    public ResponseEntity<Interes> createInteres(@RequestBody Interes interes) {
        Interes newInteres = interesLogic.createInteres(interes);
        return new ResponseEntity<>(newInteres, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interes> getInteresById(@PathVariable Long id) {
        return interesLogic.getInteresById(id)
            .map(interes -> new ResponseEntity<>(interes, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interes> updateInteres(@PathVariable Long id, @RequestBody Interes interesDetails) {
        Interes updatedInteres = interesLogic.updateInteres(id, interesDetails);
        return new ResponseEntity<>(updatedInteres, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteres(@PathVariable Long id) {
        interesLogic.deleteInteres(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Interes>> getAllIntereses() {
        List<Interes> intereses = interesLogic.getAllIntereses();
        return new ResponseEntity<>(intereses, HttpStatus.OK);
    }
}
