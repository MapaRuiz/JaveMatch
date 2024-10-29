package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Usuario;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioLogic usuarioLogic;

    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        try {
            Usuario registeredUser = usuarioLogic.registerUsuario(usuario);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> loginUser(@RequestBody Usuario usuario) {
        return usuarioLogic.loginUsuario(usuario.getCorreo(), usuario.getCorreo())
            .map(loggedInUser -> new ResponseEntity<>(loggedInUser, HttpStatus.OK))
            .orElse(new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED));
    }
}
