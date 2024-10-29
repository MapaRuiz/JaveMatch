package gajudama.javematch.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Usuario;

@RestController
@RequestMapping("/")
public class JaveMatchController {
    private UsuarioLogic usuarioLogic;

    public JaveMatchController(UsuarioLogic usuarioLogic) {
        this.usuarioLogic = usuarioLogic;
    } 
    
    //Agregar un usuario
    @PostMapping("/usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario agregarUsuario(@RequestBody Usuario todo) throws Exception{
        return usuarioLogic.createUsuario(todo);
    }

    //Mostrar todas los usuarios
    @GetMapping("/usuario")
    public Iterable<Usuario> mostrarUsuarios() {
        return usuarioLogic.getAllUsuarios();
    }

}
