package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Usuario;
import gajudama.javematch.model.Interes;
import gajudama.javematch.model.Plan;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioLogic usuarioLogic;

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioLogic.createUsuario(usuario);
        return new ResponseEntity<>(newUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioLogic.getUsuarioById(id)
            .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Usuario updatedUsuario = usuarioLogic.updateUsuario(id, usuarioDetails);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioLogic.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioLogic.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Endpoints para registrar y autenticar usuarios
    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario registeredUsuario = usuarioLogic.registerUsuario(usuario);
            return new ResponseEntity<>(registeredUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> loginUsuario(@RequestParam String email, @RequestParam String password) {
        return usuarioLogic.loginUsuario(email, password)
            .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    // Endpoint para actualizar el plan de un usuario
    @PostMapping("/upgradePlan")
    public ResponseEntity<Usuario> upgradePlan(@RequestParam Long usuarioId, @RequestBody Plan newPlan) {
        Usuario updatedUsuario = usuarioLogic.upgradePlan(usuarioId, newPlan);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

    // Endpoint para añadir un interés a un usuario
    @PostMapping("/addInteres")
    public ResponseEntity<Usuario> addInteres(@RequestParam Long usuarioId, @RequestBody Interes interes) {
        Usuario updatedUsuario = usuarioLogic.addInteres(usuarioId, interes);
        return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
    }

    // Endpoint para encontrar usuarios con intereses coincidentes
    @GetMapping("/findUsersWithMatchingInterests")
    public ResponseEntity<List<Usuario>> findUsersWithMatchingInterests(@RequestParam Long usuarioId) {
        List<Usuario> usuarios = usuarioLogic.findUsersWithMatchingInterests(usuarioId);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
