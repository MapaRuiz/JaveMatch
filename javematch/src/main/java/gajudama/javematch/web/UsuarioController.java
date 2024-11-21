package gajudama.javematch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gajudama.javematch.logic.UsuarioLogic;
import gajudama.javematch.model.Usuario;
import gajudama.javematch.model.Interes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioLogic usuarioLogic;

    @GetMapping
    public List<Map<String, Object>> getUsuarios() {
        // Obtener los usuarios mediante la lógica de negocio
        List<Usuario> usuarios = usuarioLogic.getAllUsuarios(); 
        List<Map<String, Object>> usuariosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            Map<String, Object> usuarioData = new HashMap<>();
            usuarioData.put("userId", usuario.getUserId());
            usuarioData.put("nombre", usuario.getNombre());
            
            // Suponiendo que tienes una lista de intereses en el objeto Usuario
            List<String> intereses = usuario.getIntereses().stream()
                                            .map(Interes::getNombre) // Obtén el nombre del interés
                                            .collect(Collectors.toList());
            usuarioData.put("intereses", intereses);
            
            usuariosDTO.add(usuarioData);
        }

        return usuariosDTO;
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioLogic.createUsuario(usuario);
        return new ResponseEntity<>(newUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUsuarioById(@PathVariable Long id) {
    // Obtener el usuario por su ID
    Optional<Usuario> usuarioOptional = usuarioLogic.getUsuarioById(id);
    
    if (usuarioOptional.isPresent()) {
        Usuario usuario = usuarioOptional.get();
        
        // Crear el mapa para devolver los datos, incluyendo los intereses
        Map<String, Object> usuarioData = new HashMap<>();
        usuarioData.put("userId", usuario.getUserId());
        usuarioData.put("nombre", usuario.getNombre());
        
        // Obtener los intereses y agregarlos al mapa
        List<String> intereses = usuario.getIntereses().stream()
                                        .map(Interes::getNombre) // Obtener el nombre del interés
                                        .collect(Collectors.toList());
        usuarioData.put("intereses", intereses);

        // Agregar el plan del usuario (si existe)
        if (usuario.getPlan() != null) {
            usuarioData.put("plan", usuario.getPlan().getNombre()); // O ajusta según tu estructura de Plan
        }

        return new ResponseEntity<>(usuarioData, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si no se encuentra el usuario
    }
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




    // Endpoints para registrar y autenticar usuarios
    @CrossOrigin(origins = "https://8080-maparuiz-javematch-rzie4brrli7.ws-us116.gitpod.io")  // Permitir solicitudes de este origen

    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
        System.out.println("Recibiendo solicitud para registrar usuario: " + usuario);  // Ver los datos recibidos

        try {
            Usuario registeredUsuario = usuarioLogic.registerUsuario(usuario);
            System.out.println("Usuario registrado exitosamente: " + registeredUsuario);  // Ver el usuario registrado
            return new ResponseEntity<>(registeredUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());  // Ver el error
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String email) {
        Optional<Usuario> usuarioOptional = usuarioLogic.loginUsuario(email);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get()); // Retorna el usuario si se encuentra
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Usuario no encontrado
        }
    }

    @PostMapping("/actualizarPlan")
    public ResponseEntity<Usuario> upgradePlan(@RequestParam Long usuarioId, @RequestParam Long planId) {
        Usuario updatedUsuario = usuarioLogic.upgradePlan(usuarioId, planId);
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

    @GetMapping("/random")
    public ResponseEntity<Usuario> getRandomUsuario() {
    Usuario randomUsuario = usuarioLogic.getRandomUsuario();
    if (randomUsuario != null) {
        return new ResponseEntity<>(randomUsuario, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


}
