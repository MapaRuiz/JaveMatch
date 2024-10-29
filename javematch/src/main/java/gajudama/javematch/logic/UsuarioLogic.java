package gajudama.javematch.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gajudama.javematch.accesoDatos.UsuarioRepository;
import gajudama.javematch.model.Usuario;

@Service
public class UsuarioLogic {
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Create a new Usuario
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Read a Usuario by ID
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Update a Usuario
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setCorreo(usuarioDetails.getCorreo());
            usuario.setPlan(usuarioDetails.getPlan());
            // Update other fields as necessary
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario not found"));
    }

    // Delete a Usuario
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // List all Usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    
}
